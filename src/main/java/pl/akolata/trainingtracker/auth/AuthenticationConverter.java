package pl.akolata.trainingtracker.auth;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;

@UtilityClass
public final class AuthenticationConverter {

    public static UserPrincipal toDomainPrincipal(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }
}
