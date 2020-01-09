package pl.akolata.trainingtracker.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
class SignInRequest {

    @NotBlank(message = "messages.sign-in.username.not-blank")
    private String username;

    @NotBlank(message = "messages.sign-in.password.not-blank")
    private String password;
}
