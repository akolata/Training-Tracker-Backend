package pl.akolata.trainingtracker.auth;

import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import pl.akolata.trainingtracker.user.command.SignUpCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class ComplexPasswordValidator implements ConstraintValidator<ComplexPassword, String> {

    private PasswordValidator passwordValidator;

    @Override
    public void initialize(ComplexPassword constraintAnnotation) {
        passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(SignUpCommand.FIELD_MIN_LENGTH, SignUpCommand.FIELD_MAX_LENGTH),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            return false;
        }

        RuleResult result = passwordValidator.validate(new PasswordData(password));
        return result.isValid();
    }
}
