package dev.flavio.api_est_gateway.repository;

import dev.flavio.api_est_gateway.model.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByLoginAndSenha(String login, String senha);
}
