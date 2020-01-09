package pl.akolata.trainingtracker.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.api.ValidationErrorResponse;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.exercise.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercise.model.dto.ExerciseDto;
import pl.akolata.trainingtracker.exercise.model.dto.ExerciseMapper;
import pl.akolata.trainingtracker.exercise.entity.Exercise;
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponseMapper;
import pl.akolata.trainingtracker.exercise.service.ExercisesService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
class ExercisesController extends BaseApiController {

    private static final String EXERCISES_URL = "/exercises";
    private static final String EXERCISE_RESOURCE_URL = EXERCISES_URL + "/{id}";

    private final ExercisesService exercisesService;
    private final ExercisesRequestMapper requestMapper;
    private final ExerciseResponseMapper responseMapper;
    private final ExerciseMapper exerciseMapper;

    @PostMapping(
            value = EXERCISES_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createExercise(@Valid @RequestBody CreateExerciseRequest request) {
        CreateExerciseCommand command = requestMapper.toCreateExerciseCommand(request);
        OperationResult<Exercise> operationResult = exercisesService.createExercise(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(operationResult.getErrorMSg()));
        }

        ExerciseDto exerciseDto = exerciseMapper.toExerciseDto(operationResult.getResult());
        URI location = getResourceLocation(EXERCISE_RESOURCE_URL, exerciseDto.getId());
        return ResponseEntity
                .created(location)
                .body(responseMapper.toExerciseResponse(exerciseDto));
    }
}
