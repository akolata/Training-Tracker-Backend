package pl.akolata.trainingtracker.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class SignInRequest {

    @NotBlank(message = "messages.sign-in.username.not-blank")
    private String username;

    @NotBlank(message = "messages.sign-in.password.not-blank")
    private String password;
}
