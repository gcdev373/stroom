/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.security.server;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.apiclients.AuthenticationServiceClients;
import stroom.auth.service.ApiException;
import stroom.security.server.UserSession.State;
import stroom.security.server.exception.AuthenticationException;
import stroom.security.shared.UserRef;
import stroom.util.io.StreamUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * <p>
 * Filter to avoid posts to the wrong place (e.g. the root of the app)
 * </p>
 */
public class SecurityFilter implements Filter {
    private static final String IGNORE_URI_REGEX = "ignoreUri";

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    private final String authenticationServiceUrl;
    private final String advertisedStroomUrl;
    private final JWTService jwtService;
    private final AuthenticationServiceClients authenticationServiceClients;
    private final AuthenticationService authenticationService;

    private Pattern pattern = null;

    public SecurityFilter(
            final String authenticationServiceUrl,
            final String advertisedStroomUrl,
            final JWTService jwtService,
            final AuthenticationServiceClients authenticationServiceClients,
            final AuthenticationService authenticationService) {
        this.authenticationServiceUrl = authenticationServiceUrl;
        this.advertisedStroomUrl = advertisedStroomUrl;
        this.jwtService = jwtService;
        this.authenticationServiceClients = authenticationServiceClients;
        this.authenticationService = authenticationService;
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        final String regex = filterConfig.getInitParameter(IGNORE_URI_REGEX);
        if (regex != null) {
            pattern = Pattern.compile(regex);
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (!(response instanceof HttpServletResponse)) {
            final String message = "Unexpected response type: " + response.getClass().getName();
            LOGGER.error(message);
            return;
        }
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!(request instanceof HttpServletRequest)) {
            final String message = "Unexpected request type: " + request.getClass().getName();
            LOGGER.error(message);
            httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, message);
            return;
        }
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        filter(httpServletRequest, httpServletResponse, chain);
    }

    private void filter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        if (request.getMethod().toUpperCase().equals(HttpMethod.OPTIONS)) {
            // We need to allow CORS preflight requests
            chain.doFilter(request, response);

        } else if (ignoreUri(request.getRequestURI())) {
            // Allow some URIs to bypass authentication checks
            chain.doFilter(request, response);

        } else {
            // We need to distinguish between requests from an API client and from the UI.
            // If a request is from the UI and fails authentication then we need to redirect to the login page.
            // If a request is from an API client and fails authentication then we need to return HTTP 403 UNAUTHORIZED.
            final String servletPath = request.getServletPath();
            final boolean isApiRequest = servletPath.contains("/api");

            UserRef userRef;
            if (isApiRequest) {
                // Authenticate requests to the API.
                userRef = loginAPI(request, response);

            } else {
                // Try and get an existing user ref from the session.
                final UserSession userSession = getUserSession(request);
                userRef = userSession.getUserRef();

                // If the session doesn't have a user ref then attempt login.
                if (userRef == null) {
                    // Authenticate requests from the UI.
                    loginUI(request, response, userSession);
                }
            }

            if (userRef != null) {
                // If the session already has a reference to a user then continue the chain as that user.
                try {
                    CurrentUserState.pushUserRef(userRef);

                    chain.doFilter(request, response);
                } finally {
                    CurrentUserState.popUserRef();
                }
            }
        }
    }

    private boolean ignoreUri(final String uri) {
        return pattern != null && pattern.matcher(uri).matches();
    }

    private UserSession getUserSession(HttpServletRequest request) {
        final HttpSession session = request.getSession(true);
        if (session == null) {
            throw new AuthenticationException("NULL SESSION");
        }

        return UserSession.getOrCreate(session);
    }

    private void loginUI(final HttpServletRequest request, final HttpServletResponse response, final UserSession userSession) throws IOException {
        boolean loggedIn = false;

        // If we have a state id then this should be a return from the auth service.
        final String stateId = request.getParameter("state");
        if (stateId != null) {
            LOGGER.debug("We have the following state: {{}}", stateId);

            // Check the state is one we requested.
            final UserSession.State state = userSession.getState(stateId);
            if (state == null) {
                LOGGER.warn("Unexpected state: " + stateId);

            } else {
                // If we have an access code we can try and log in.
                final String accessCode = request.getParameter("accessCode");
                if (accessCode != null) {
                    LOGGER.debug("We have the following access code: {{}}", accessCode);
                    final AuthenticationToken token = createUIToken(request, state, accessCode);
                    final UserRef userRef = authenticationService.getUserRef(token);
                    loggedIn = userRef != null;

                    // If we manage to login then redirect to the original URL held in the state.
                    if (loggedIn) {
                        LOGGER.info("Redirecting to initiating URL: {}", state.getUrl());
                        response.sendRedirect(state.getUrl());
                    }
                }
            }
        }

        // If we're not logged in we need to start an AuthenticationRequest flow.
        if (!loggedIn) {
            // We have a a new request so we're going to redirect with an AuthenticationRequest.
            redirectToAuthService(request, response, userSession);
        }
    }

    private void redirectToAuthService(final HttpServletRequest request, final HttpServletResponse response, final UserSession userSession) throws IOException {
        // We have a a new request so we're going to redirect with an AuthenticationRequest.
        final String authenticationRequestBaseUrl = authenticationServiceUrl + "/authentication/v1/authenticate";

        // Get the redirect URL for the auth service from the current request.
        String url = request.getRequestURL().toString();

        // Create a state for this user session.
        final State state = userSession.createState(url);

        // In some cases we might need to use an external URL as the current incoming one might have been proxied.
        if (advertisedStroomUrl != null && advertisedStroomUrl.trim().length() > 0) {
            url = advertisedStroomUrl;
        }

        // Trim off any trailing params or paths.
        final int index = url.lastIndexOf('/');
        if (index != -1) {
            url = url.substring(0, index);
        }

        // Encode the URL.
        url = URLEncoder.encode(url, StreamUtil.DEFAULT_CHARSET_NAME);

        final String authenticationRequestParams = "" +
                "?scope=openid" +
                "&response_type=code" +
                "&client_id=stroom" +
                "&redirect_url=" +
                url +
                "&state=" +
                state.getId() +
                "&nonce=" +
                state.getNonce();

        final String authenticationRequestUrl = authenticationRequestBaseUrl + authenticationRequestParams;
        LOGGER.info("Redirecting with an AuthenticationRequest to: {}", authenticationRequestUrl);
        // We want to make sure that the client has the cookie.
        response.sendRedirect(authenticationRequestUrl);
    }

    /**
     * This method must create the token.
     * It does this by enacting the OpenId exchange of accessCode for idToken.
     */
    private AuthenticationToken createUIToken(final HttpServletRequest request, final UserSession.State state, final String accessCode) {
        AuthenticationToken token = null;

        try {
            String sessionId = request.getSession().getId();
            final String idToken = authenticationServiceClients.newAuthenticationApi().getIdToken(accessCode);
            final Optional<JwtClaims> jwtClaimsOptional = jwtService.verifyToken(idToken);
            if (jwtClaimsOptional.isPresent()) {
                final String nonce = (String) jwtClaimsOptional.get().getClaimsMap().get("nonce");
                final boolean match = nonce.equals(state.getNonce());
                if (match) {
                    LOGGER.info("User is authenticated for sessionId " + sessionId);
                    token = new AuthenticationToken(jwtClaimsOptional.get().getSubject(), idToken);

                } else {
                    // If the nonces don't match we need to redirect to log in again.
                    // Maybe the request uses an out-of-date stroomSessionId?
                    LOGGER.info("Received a bad nonce!");
                }
            }
        } catch (ApiException e) {
            if (e.getCode() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                // If we can't exchange the accessCode for an idToken then this probably means the
                // accessCode doesn't exist any more, or has already been used. so we can't proceed.
                LOGGER.error("The accessCode used to obtain an idToken was rejected. Has it already been used?", e);
            }
        } catch (final MalformedClaimException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return token;
    }

    private UserRef loginAPI(final HttpServletRequest request, final HttpServletResponse response) {
        UserRef userRef = null;

        // Authenticate requests from an API client
        boolean isAuthenticatedApiRequest = jwtService.containsValidJws(request);
        if (isAuthenticatedApiRequest) {
            final AuthenticationToken token = createAPIToken(request);
            userRef = authenticationService.getUserRef(token);
        }

        if (userRef == null) {
            LOGGER.debug("API request is unauthorised.");
            response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
        }

        return userRef;
    }

    /**
     * This method creates a token for the API auth flow.
     */
    private AuthenticationToken createAPIToken(final HttpServletRequest request) {
        AuthenticationToken token = null;

        try {
            if (jwtService.containsValidJws(request)) {
                final Optional<String> optionalJws = jwtService.getJws(request);
                final String jws = optionalJws.orElseThrow(() -> new AuthenticationException("Unable to get JWS"));
                final Optional<JwtClaims> jwtClaimsOptional = jwtService.verifyToken(jws);
                final JwtClaims jwtClaims = jwtClaimsOptional.orElseThrow(() -> new AuthenticationException("Unable to get JWT claims"));
                token = new AuthenticationToken(jwtClaims.getSubject(), optionalJws.get());
            } else {
                LOGGER.error("Cannot get a valid JWS for API request!");
            }
        } catch (final MalformedClaimException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AuthenticationException(e.getMessage(), e);
        }

        return token;
    }

    @Override
    public void destroy() {
    }
}
