package pl.akolata.trainingtracker.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.akolata.trainingtracker.configuration.properties.JwtApplicationProperties;

@Configuration
@EnableConfigurationProperties({JwtApplicationProperties.class})
class ApplicationPropertiesConfiguration {
}
