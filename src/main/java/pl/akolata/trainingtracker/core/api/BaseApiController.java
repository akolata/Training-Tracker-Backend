package pl.akolata.trainingtracker.core.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(value = BaseApiController.API_URL)
public class BaseApiController {

    static final String API_URL = "/api";

    protected BaseApiController() {
    }

    protected URI getResourceLocation(String pathWithoutApiPrefix, Object... uriVariables) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(API_URL + pathWithoutApiPrefix)
                .buildAndExpand(uriVariables)
                .toUri();
    }
}
