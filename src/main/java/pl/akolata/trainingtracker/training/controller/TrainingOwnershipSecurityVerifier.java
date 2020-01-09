package pl.akolata.trainingtracker.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.akolata.trainingtracker.auth.AuthenticationConverter;
import pl.akolata.trainingtracker.auth.policy.AdminOrOwnerPolicy;
import pl.akolata.trainingtracker.auth.policy.VerificationUtils;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;
import pl.akolata.trainingtracker.training.repository.TrainingsRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
class TrainingOwnershipSecurityVerifier {

    private final AdminOrOwnerPolicy adminOrOwnerPolicy;
    private final TrainingsRepository trainingsRepository;

    public boolean adminOrTrainingOwner(Authentication authentication, Long trainingId) {
        if (Objects.isNull(trainingId)) {
            return true; // nothing to validate
        }

        if (VerificationUtils.isAdmin(authentication)) {
            return true;
        }

        UserPrincipal principal = AuthenticationConverter.toDomainPrincipal(authentication);
        return trainingsRepository.existsById(trainingId) && trainingsRepository.existsByIdAndUserId(trainingId, principal.getId());
    }
}
