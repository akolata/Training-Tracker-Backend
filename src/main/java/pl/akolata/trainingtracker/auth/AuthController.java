package pl.akolata.trainingtracker.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.trainingtracker.core.api.ApiResponse;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.user.command.SignUpCommand;
import pl.akolata.trainingtracker.user.dto.UserDto;
import pl.akolata.trainingtracker.user.dto.UserMapper;
import pl.akolata.trainingtracker.user.service.UserSignUpService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
class AuthController extends BaseApiController {

    private static final String AUTH_URL = "/auth";
    private static final String SIGN_IN_URL = "/sign-in";
    private static final String SIGN_UP_URL = "/sign-up";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserSignUpService signUpService;
    private final UserMapper userMapper;

    @PostMapping(
            path = AUTH_URL + SIGN_IN_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity
                .ok(ApiResponse.success(new JwtAuthenticationResponse(jwt)));
    }

    @PostMapping(
            path = AUTH_URL + SIGN_UP_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody SignUpRequest request) {
        OperationResult<User> signUpResult = signUpService.signUp(signUpRequestToCommand(request));

        if (signUpResult.isFailure()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(signUpResult.getValidationResult().getErrorMsg()));
        }
        User signedUpUser = signUpResult.getResult();
        UserDto userDto = userMapper.toUserDto(signedUpUser);
        URI location = getResourceLocation("/users/{id}", signedUpUser.getId()); // TODO move this URL path somewhere else
        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(userDto));
    }

    private SignUpCommand signUpRequestToCommand(SignUpRequest request) {
        return new SignUpCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
    }
}
