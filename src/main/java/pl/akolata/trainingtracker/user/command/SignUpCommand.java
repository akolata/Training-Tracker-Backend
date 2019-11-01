package pl.akolata.trainingtracker.user.command;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class SignUpCommand {

    public static final int FIELD_MIN_LENGTH = 2;
    public static final int FIELD_MAX_LENGTH = 60;
    public static final int EMAIL_MAX_LENGTH = 200;
    public static final int PASSWORD_MIN_LENGTH = 8;

    @NotBlank
    @Size(min = FIELD_MIN_LENGTH, max = FIELD_MAX_LENGTH)
    private final String firstName;

    @NotBlank
    @Size(min = FIELD_MIN_LENGTH, max = FIELD_MAX_LENGTH)
    private final String lastName;

    @NotBlank
    @Size(min = FIELD_MIN_LENGTH, max = FIELD_MAX_LENGTH)
    private final String username;

    @Email
    @NotBlank
    @Size(max = EMAIL_MAX_LENGTH)
    private final String email;

    @NotBlank
    @Size(min = PASSWORD_MIN_LENGTH, max = FIELD_MAX_LENGTH)
    private final String rawPassword;
}
