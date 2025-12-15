package dev.flavio.api_est_gateway.service;

import dev.flavio.api_est_gateway.model.UserEntity;
import dev.flavio.api_est_gateway.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserEntity> authenticate(String login, String senha) {
        return userRepository.findByLoginAndSenha(login, senha);
    }
}
