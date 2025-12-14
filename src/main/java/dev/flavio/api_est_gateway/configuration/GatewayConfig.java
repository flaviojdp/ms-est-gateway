package dev.flavio.api_est_gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("api-est-negocio", r ->
                        r.path("/negocios/**")
                                .filters(f -> f.stripPrefix(0))
                                .uri("lb://api-est-negocio"))
                .build();
    }
}
