package stroom.startup;

import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.resource.PathResource;
import stroom.security.spring.SecurityConfiguration;
import stroom.util.spring.StroomSpringProfiles;
import stroom.util.thread.ThreadScopeContextHolder;

import java.nio.file.Paths;

/**
 * Configures  the environment, including the Dropwizard Environment as well as system properties and misc.
 */
public class Environment {
    public static void configure(io.dropwizard.setup.Environment environment) {
        // Set up a session manager for Jetty
        HashSessionManager manager = new HashSessionManager();
        SessionHandler sessions = new SessionHandler(manager);
        environment.servlets().setSessionHandler(sessions);

        // If we don't set the baseResource then servlets might not be able to find files.
        environment.servlets().setBaseResource(new PathResource(Paths.get("src/main/resources/webapp/")));

        // We want Stroom to use the root path so we need to move Dropwizard's path.
        environment.jersey().setUrlPattern("/api/*");

        // We need to set this otherwise we won't have all the beans we need.
        System.setProperty("spring.profiles.active", String.format("%s,%s", StroomSpringProfiles.PROD, SecurityConfiguration.PROD_SECURITY));

        // We need to prime this otherwise we won't have a thread scope context and bean initialisation will fail
        ThreadScopeContextHolder.createContext();
    }
}
