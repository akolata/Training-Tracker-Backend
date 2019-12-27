package pl.akolata.trainingtracker.auth.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminOrOwnerPolicy {
    private final OwnershipVerifier ownershipVerifier;

    public boolean isAdminOrOwner(Authentication authentication, Long ownerId) {
        return VerificationUtils.isAdmin(authentication) || ownershipVerifier.isOwner(authentication, ownerId);
    }
}
