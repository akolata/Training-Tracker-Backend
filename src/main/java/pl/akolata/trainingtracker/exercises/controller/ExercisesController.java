package pl.akolata.trainingtracker.exercises.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.trainingtracker.core.api.ApiResponse;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.exercises.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercises.dto.ExerciseDto;
import pl.akolata.trainingtracker.exercises.dto.ExerciseMapper;
import pl.akolata.trainingtracker.exercises.entity.Exercise;
import pl.akolata.trainingtracker.exercises.service.ExercisesService;

import javax.validation.Valid;
import java.net.URI;

@RestController
class ExercisesController extends BaseApiController {

    private static final String EXERCISES_URL = "/exercises";
    private static final String EXERCISE_RESOURCE_URL = EXERCISES_URL + "/{id}";

    private final ExercisesService exercisesService;
    private final ExercisesRequestMapper requestMapper;
    private final ExerciseMapper exerciseMapper;

    public ExercisesController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
        requestMapper = new ExercisesRequestMapper();
        exerciseMapper = new ExerciseMapper();

    }

    @PostMapping(
            value = EXERCISES_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createExercise(@Valid @RequestBody CreateExerciseRequest request) {
        CreateExerciseCommand command = requestMapper.toCreateExerciseCommand(request);
        OperationResult<Exercise> operationResult = exercisesService.createExercise(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(operationResult.getValidationResult().getErrorMsg()));
        }

        ExerciseDto exerciseDto = exerciseMapper.toExerciseDto(operationResult.getResult());
        URI location = getResourceLocation(EXERCISE_RESOURCE_URL, exerciseDto.getId());
        return ResponseEntity
                .created(location)
                .body(requestMapper.toExerciseResponse(exerciseDto));
    }
}
