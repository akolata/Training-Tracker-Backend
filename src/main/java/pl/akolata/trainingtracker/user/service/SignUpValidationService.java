package pl.akolata.trainingtracker.user.service;

import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.user.command.SignUpCommand;

public interface SignUpValidationService {
    ValidationResult validate(SignUpCommand command);
}
