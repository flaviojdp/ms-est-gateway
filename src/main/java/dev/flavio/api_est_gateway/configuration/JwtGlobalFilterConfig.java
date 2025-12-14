package dev.flavio.api_est_gateway.configuration;

import dev.flavio.api_est_gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-100)
public class JwtGlobalFilterConfig implements GlobalFilter {
    private final JwtService jwtService;

    public JwtGlobalFilterConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if(!jwtService.validToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Se a validação for bem-sucedida, continua a cadeia
        System.out.println("Token validado com sucesso para a requisição: " + exchange.getRequest().getURI());

        // Continua para o próximo filtro e roteamento
        return chain.filter(exchange);
    }
}
