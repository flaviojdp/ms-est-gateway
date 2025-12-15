package dev.flavio.api_est_gateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record UserEntity(
        @Id
        Long id,
        String login,
        String senha
) {
}
