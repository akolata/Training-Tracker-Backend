package pl.akolata.trainingtracker.auth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {

    @Override
    public boolean isValid(SignUpRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (request.getPassword() == null) {
            return request.getRepeatedPassword() == null;
        }
        return request.getPassword().equals(request.getRepeatedPassword());
    }
}