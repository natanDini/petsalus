package br.com.petsalus.security;

import br.com.petsalus.properties.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateToken(String username, String userRole) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", userRole)
                .withExpiresAt(Date.from(Instant.now().plus(99999, ChronoUnit.HOURS)))
                .sign(getAlgorithm());
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(getAlgorithm()).build().verify(token);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }
}