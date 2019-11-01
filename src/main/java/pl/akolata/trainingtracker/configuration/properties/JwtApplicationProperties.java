package pl.akolata.trainingtracker.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.jwt")
public class JwtApplicationProperties {

    @NotEmpty
    private String secret;

    @NotNull
    private Integer tokenExpirationInMs;
}
