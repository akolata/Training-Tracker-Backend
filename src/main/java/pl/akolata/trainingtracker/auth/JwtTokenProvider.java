package pl.akolata.trainingtracker.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.akolata.trainingtracker.configuration.properties.JwtApplicationProperties;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;

import java.util.Date;

@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenProvider {

    private final JwtApplicationProperties jwtApplicationProperties;

    String generateToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + jwtApplicationProperties.getTokenExpirationInMs());

        return Jwts
                .builder()
                .setSubject(principal.getId() + "")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtApplicationProperties.getSecret())
                .compact();
    }

    Long getUserIdFromJWT(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtApplicationProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtApplicationProperties.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token. Reason:", e);
        }

        return false;
    }
}
