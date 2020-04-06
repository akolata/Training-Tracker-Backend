package pl.akolata.trainingtracker.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akolata.trainingtracker.common.query.PaginationHeadersUtils;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.model.dto.TrainingDto;
import pl.akolata.trainingtracker.training.model.dto.TrainingsMapper;
import pl.akolata.trainingtracker.training.query.TrainingQuery;
import pl.akolata.trainingtracker.training.service.TrainingQueryService;
import pl.akolata.trainingtracker.user.dto.UserDto;
import pl.akolata.trainingtracker.user.dto.UserMapper;
import pl.akolata.trainingtracker.user.service.UserSignUpService;
import pl.akolata.trainingtracker.user.service.UsersProfilesService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
class UserController extends BaseApiController {

    private static final String USERS_URL = "/users";
    private static final String USER_URL = "/users/{id}";
    private static final String USERS_USERNAMES_URL = "/usernames/{username}";
    private static final String USER_TRAININGS_URL = USER_URL + "/trainings";
    private static final String USERS_EMAILS_URL = "/emails/{email}";

    private final UserSignUpService signUpService;
    private final UsersProfilesService usersProfilesService;
    private final TrainingQueryService trainingQueryService;
    private final TrainingsMapper trainingsMapper;
    private final UserMapper userMapper;

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

    @GetMapping(
            value = USER_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UserResponse> getUserProfile(@PathVariable(name = "id") Long id) {
        Optional<User> user = usersProfilesService.findUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = userMapper.toUserDto(user.get());
        return ResponseEntity.ok(new UserResponse(userDto));
    }

    @GetMapping(
            value = USER_TRAININGS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> getUserTrainings(
            @PathVariable(name = "id") Long userId,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String additionalInfo,
            Pageable pageable) {
        TrainingQuery query = new TrainingQuery(id, name, additionalInfo, userId, pageable);
        Page<Training> trainingsPage = trainingQueryService.findTrainingsBy(query);
        List<TrainingDto> trainings = new LinkedList<>();
        trainingsPage.get().forEach(t -> trainings.add(trainingsMapper.toTrainingDto(t)));
        return ResponseEntity
                .ok()
                .headers(PaginationHeadersUtils.buildPaginationHeaders(trainingsPage))
                .body(new TrainingsResponse(trainings));
    }
}
