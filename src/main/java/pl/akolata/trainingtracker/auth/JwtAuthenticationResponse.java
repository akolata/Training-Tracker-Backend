package pl.akolata.trainingtracker.auth;

import lombok.Data;
import pl.akolata.trainingtracker.configuration.SecurityConfiguration;

@Data
class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = SecurityConfiguration.AUTH_TOKEN_TYPE;

    JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
