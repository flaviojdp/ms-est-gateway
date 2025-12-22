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
                .route("api-est-negocio", r ->r.path("/negocios/**")
                                .filters(f -> f.stripPrefix(0))
                                .uri("lb://api-est-negocio"))
                //api-est-mscliente
                .route("api-est-mscliente", r ->r.path("/clientes/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://api-est-mscliente"))
                //api-est-mscartao
                .route("api-est-mscartao", r ->r.path("/cartoes/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://api-est-mscartao"))
                //api-est-msavaliadorcredito
                .route("api-est-msavaliadorcredito", r ->r.path("/avaliacoes-credito/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://api-est-msavaliadorcredito"))
                .build();
    }
}
