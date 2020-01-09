package pl.akolata.trainingtracker.auth.policy;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.akolata.trainingtracker.auth.AuthenticationConverter;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;

import java.util.Objects;

@Component
public class OwnershipPolicy {

    public boolean isOwner(Authentication authentication, Long userId) {
        UserPrincipal principal = AuthenticationConverter.toDomainPrincipal(authentication);
        Long principalId = principal.getId();
        return Objects.nonNull(principalId) && Objects.nonNull(userId) && principalId.equals(userId);
    }
}
