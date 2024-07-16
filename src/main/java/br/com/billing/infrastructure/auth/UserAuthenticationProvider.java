package br.com.billing.infrastructure.auth;

import br.com.billing.domain.dto.User;
import br.com.billing.domain.dto.UserAuthenticatedJwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider {

    private static final int ONE_HOUR = 3600000;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ONE_HOUR);
        Algorithm authenticationCode = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", user.getId().toString())
                .withClaim("fullName", getFullName(user))
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .sign(authenticationCode);
    }

    private Authentication getAuthentication(String token) {
        Algorithm authenticationCode = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(authenticationCode).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        UserAuthenticatedJwt user = UserAuthenticatedJwt.builder()
                .id(UUID.fromString(decodedJWT.getClaim("id").asString()))
                .email(decodedJWT.getSubject())
                .fullName(decodedJWT.getClaim("fullName").asString())
                .firstName(decodedJWT.getClaim("firstName").asString())
                .lastName(decodedJWT.getClaim("lastName").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        return getAuthentication(token);
    }

    public Authentication validateToken(String token) {
        return getAuthentication(token);
    }

    private static String getFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
