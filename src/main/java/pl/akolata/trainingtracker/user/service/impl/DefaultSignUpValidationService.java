package pl.akolata.trainingtracker.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.dto.ValidationStep;
import pl.akolata.trainingtracker.user.command.SignUpCommand;
import pl.akolata.trainingtracker.user.service.SignUpValidationService;
import pl.akolata.trainingtracker.user.service.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
@AllArgsConstructor
public class DefaultSignUpValidationService implements SignUpValidationService {

    private final UserRepository userRepository;

    @Override
    public ValidationResult validate(SignUpCommand command) {
        CommandConstraintsValidationStep validation = new CommandConstraintsValidationStep();
        validation.linkWith(new UsernameDuplicationValidationStep(userRepository))
                .linkWith(new EmailDuplicationValidationStep(userRepository));
        return validation.verify(command);
    }

    private static class CommandConstraintsValidationStep extends ValidationStep<SignUpCommand> {

        @Override
        public ValidationResult verify(SignUpCommand command) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<SignUpCommand>> constraintsViolations = validator.validate(command);

                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(command);
        }
    }

    @AllArgsConstructor
    private static class UsernameDuplicationValidationStep extends ValidationStep<SignUpCommand> {

        private final UserRepository userRepository;

        @Override
        public ValidationResult verify(SignUpCommand command) {
            if (userRepository.findByUsername(command.getUsername()).isPresent()) {
                return ValidationResult.invalid(String.format("Username [%s] is already taken", command.getUsername()));
            }
            return checkNext(command);
        }
    }

    @AllArgsConstructor
    private static class EmailDuplicationValidationStep extends ValidationStep<SignUpCommand> {

        private final UserRepository userRepository;

        @Override
        public ValidationResult verify(SignUpCommand command) {
            if (userRepository.findByEmail(command.getEmail()).isPresent()) {
                return ValidationResult.invalid(String.format("Email [%s] is already taken", command.getEmail()));
            }
            return checkNext(command);
        }
    }
}
