package dev.flavio.api_est_gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtService {
    private static final String SECRET_KEY = "bataTinhaFrita123***";
    private static final String ISSUER = "api-gateway-seg";
    private static final String USER_NAME_BATATINHA_FRITA = "batatinhaFrita";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            // Opcional: .withSubject(USER_NAME_BATATINHA_FRITA)
            .build();

    public String generateToken() {
        return JWT.create()
                .withIssuer(ISSUER) // Define o emissor do token
                .withIssuedAt(creationDate()) // Define a data de emissão do token
                .withExpiresAt(expirationDate()) // Define a data de expiração do token
                .withSubject(USER_NAME_BATATINHA_FRITA) // Define o assunto do token (neste caso, o nome de usuário)
                .sign(algorithm);
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusMinutes(30).toInstant();
    }

    public boolean validToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            DecodedJWT verify = verifier.verify(token);
            String payload = verify.getPayload();
            return true;
        } catch (JWTVerificationException e) {
            // Logar a exceção é uma boa prática para debug, mas retornamos false
            // System.err.println("Token inválido: " + e.getMessage());
            return false;
        }
    }
}
