package pl.akolata.trainingtracker.auth.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminOrOwnerPolicy {
    private final OwnershipPolicy ownershipPolicy;

    public boolean isAdminOrOwner(Authentication authentication, Long ownerId) {
        return VerificationUtils.isAdmin(authentication) || ownershipPolicy.isOwner(authentication, ownerId);
    }
}
