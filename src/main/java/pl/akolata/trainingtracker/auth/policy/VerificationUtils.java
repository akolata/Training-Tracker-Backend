package pl.akolata.trainingtracker.auth.policy;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import pl.akolata.trainingtracker.auth.AuthenticationConverter;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;
import pl.akolata.trainingtracker.core.entity.RoleName;

@UtilityClass
public final class VerificationUtils {

    public static boolean isAdmin(Authentication authentication) {
        UserPrincipal userPrincipal = AuthenticationConverter.toDomainPrincipal(authentication);
        return userPrincipal.getAuthorities().stream().anyMatch(authority -> RoleName.ROLE_ADMIN.getRoleName().equals(authority.getAuthority()));
    }
}
