package pl.akolata.trainingtracker.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.user.service.UserSignUpService;

@RestController
@RequiredArgsConstructor
class UserController extends BaseApiController {

    private static final String USERS_URL = "/users";
    private static final String USERS_EMAILS_URL = "/emails/{email}";
    private static final String USERS_USERNAMES_URL = "/usernames/{username}";

    private final UserSignUpService signUpService;


    @RequestMapping(
            method = RequestMethod.HEAD,
            value = USERS_URL + USERS_EMAILS_URL
    )
    ResponseEntity<Void> userEmails(@PathVariable(value = "email") String email) {
        if (signUpService.isEmailTaken(email)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(
            method = RequestMethod.HEAD,
            value = USERS_URL + USERS_USERNAMES_URL
    )
    ResponseEntity<Void> usersUsernames(@PathVariable(name = "username") String username) {
        if (signUpService.isUsernameTaken(username)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
