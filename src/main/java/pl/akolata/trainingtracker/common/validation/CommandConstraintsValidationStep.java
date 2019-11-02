package pl.akolata.trainingtracker.common.validation;

import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.dto.ValidationStep;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CommandConstraintsValidationStep<T> extends ValidationStep<T> {

    @Override
    public ValidationResult verify(T command) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<T>> constraintsViolations = validator.validate(command);

            if (!constraintsViolations.isEmpty()) {
                return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
            }
        }
        return checkNext(command);
    }
}