package pl.akolata.trainingtracker.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtTokenProviderTest {

    @Value("${app.jwt.secret}")
    private String secret;

    @Test
    void generateToken() {
        long hourInMs = 3_600_000L;
        long yearInMs = hourInMs * 24 * 365;
        Date expirationDate = new Date(new Date().getTime() + yearInMs);

        String jwt = Jwts
                .builder()
                .setSubject("1")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        Assertions.assertNotNull(jwt);
    }
}