package dev.flavio.api_est_gateway.web;

import dev.flavio.api_est_gateway.service.JwtService;
import dev.flavio.api_est_gateway.service.UserService;
import dev.flavio.api_est_gateway.web.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest loginRequest) {

        return userService.authenticate(
                loginRequest.login(),
                loginRequest.senha()
        ).flatMap(user -> {
            return Mono.just(ResponseEntity.ok(jwtService.generateToken()));
        }).switchIfEmpty(Mono.just(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciais inválidas")
        ));
    }
}
