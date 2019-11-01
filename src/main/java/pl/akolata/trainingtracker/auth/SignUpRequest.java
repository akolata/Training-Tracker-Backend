package pl.akolata.trainingtracker.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.akolata.trainingtracker.user.command.SignUpCommand;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@PasswordMatches(message = "password.unmatched")
class SignUpRequest {

    @NotBlank(message = "{first-name.not-blank}")
    @Size(min = SignUpCommand.FIELD_MIN_LENGTH, max = SignUpCommand.FIELD_MAX_LENGTH, message = "{first-name.size}")
    private final String firstName;

    @NotBlank(message = "{last-name.not-blank}")
    @Size(min = SignUpCommand.FIELD_MIN_LENGTH, max = SignUpCommand.FIELD_MAX_LENGTH, message = "{last-name.size}")
    private final String lastName;

    @NotBlank(message = "{username.not-blank}")
    @Size(min = SignUpCommand.FIELD_MIN_LENGTH, max = SignUpCommand.FIELD_MAX_LENGTH, message = "{username.size}")
    private final String username;

    @Email(message = "{email.invalid}")
    @NotBlank(message = "{email.not-blank}")
    @Size(max = SignUpCommand.EMAIL_MAX_LENGTH, message = "{email.size}")
    private final String email;

    @NotBlank(message = "{password.not-blank}")
    @ComplexPassword(message = "{password.complexity}")
    @Size(min = SignUpCommand.PASSWORD_MIN_LENGTH, max = SignUpCommand.FIELD_MAX_LENGTH, message = "{password.size}")
    private String password;

    @NotBlank(message = "{repeated-password.not-blank}")
    private String repeatedPassword;
}
