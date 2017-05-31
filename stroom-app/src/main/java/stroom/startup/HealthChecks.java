/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.startup;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import stroom.resources.HasHealthCheck;
import stroom.servicediscovery.ServiceDiscoveryRegistrar;

import java.util.function.Consumer;

public class HealthChecks {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthChecks.class);

    public static final String HEALTH_CHECK_SUFFIX = "HealthCheck";

    private HealthChecks() {
    }

    public static void registerHealthChecks(final HealthCheckRegistry healthCheckRegistry,
                                            final Resources resources,
                                            final ServletMonitor servletMonitor){

        resources.getResources().stream()
            .filter(resource -> resource instanceof HasHealthCheck)
            .forEach(resource -> {
                String name = resource.getName() + HEALTH_CHECK_SUFFIX;
                LOGGER.debug("Registering heath check {}", name);
                healthCheckRegistry.register(name, new HealthCheck() {
                    @Override
                    protected Result check() throws Exception {
                        return ((HasHealthCheck) resource).getHealth();
                    }
                });
            });

        servletMonitor.registerApplicationContextListener(createApplicationContextListener(healthCheckRegistry));
    }

    private static Consumer<ApplicationContext> createApplicationContextListener(final HealthCheckRegistry healthCheckRegistry) {

        return (applicationContext) -> registerServiceDiscoveryRegistrar(applicationContext, healthCheckRegistry);
    }

    private static void registerServiceDiscoveryRegistrar(final ApplicationContext applicationContext,
                                                   final HealthCheckRegistry healthCheckRegistry) {
        ServiceDiscoveryRegistrar serviceDiscoveryRegistrar = applicationContext.getBean(ServiceDiscoveryRegistrar.class);
        String name = serviceDiscoveryRegistrar.getClass().getName() + HEALTH_CHECK_SUFFIX;
        LOGGER.debug("Registering heath check {}", name);
        healthCheckRegistry.register(name, new HealthCheck() {
                    @Override
                    protected Result check() throws Exception {
                        return serviceDiscoveryRegistrar.getHealth();
                    }
                });
    }
}
