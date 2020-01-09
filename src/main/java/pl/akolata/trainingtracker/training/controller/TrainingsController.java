package pl.akolata.trainingtracker.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.akolata.trainingtracker.common.query.PaginationHeadersUtils;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.api.ValidationErrorResponse;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.entity.RoleName;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.entity.TrainingSet;
import pl.akolata.trainingtracker.training.model.api.TrainingResponseMapper;
import pl.akolata.trainingtracker.training.model.dto.TrainingDto;
import pl.akolata.trainingtracker.training.model.dto.TrainingsMapper;
import pl.akolata.trainingtracker.training.query.TrainingQuery;
import pl.akolata.trainingtracker.training.service.TrainingQueryService;
import pl.akolata.trainingtracker.training.service.TrainingsService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class TrainingsController extends BaseApiController {

    private static final String TRAININGS_URL = "/trainings";
    private static final String TRAINING_RESOURCE_URL = TRAININGS_URL + "/{trainingId}";
    private static final String TRAINING_SETS_URL = TRAINING_RESOURCE_URL + "/sets";
    private static final String TRAINING_SET_RESOURCE_URL = TRAINING_SETS_URL + "/{trainingSetId}";

    private final TrainingsService trainingsService;
    private final TrainingQueryService trainingQueryService;
    private final TrainingRequestMapper requestMapper;
    private final TrainingsMapper trainingsMapper;
    private final TrainingResponseMapper responseMapper;

    @Secured(RoleName.Annotation.ROLE_ADMIN)
    @GetMapping(
            path = TRAININGS_URL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<TrainingQueryResponse> getTrainings(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String additionalInfo,
            Pageable pageable) {
        TrainingQuery query = new TrainingQuery(id, name, additionalInfo, pageable);
        Page<Training> trainingsPage = trainingQueryService.findTrainingsBy(query);
        Set<TrainingDto> trainings = trainingsPage.get().map(trainingsMapper::toTrainingDto).collect(Collectors.toSet());
        return ResponseEntity
                .ok()
                .headers(PaginationHeadersUtils.buildPaginationHeaders(trainingsPage))
                .body(new TrainingQueryResponse(trainings));
    }

    @PreAuthorize("@adminOrOwnerPolicy.isAdminOrOwner(authentication, #request.userId)")
    @PostMapping(
            path = TRAININGS_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createTraining(@RequestBody @Valid CreateTrainingRequest request) {
        CreateTrainingCommand command = requestMapper.toCreateTrainingCommand(request);
        OperationResult<Training> operationResult = trainingsService.createTraining(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(operationResult.getErrorMSg()));
        }

        Training training = operationResult.getResult();
        URI location = getResourceLocation(TRAINING_RESOURCE_URL, training.getId());
        return ResponseEntity.created(location).body(responseMapper.toTrainingResponse(training));
    }

    @PreAuthorize("@trainingOwnershipSecurityVerifier.adminOrTrainingOwner(authentication, #trainingId)")
    @PostMapping(
            path = TRAINING_SETS_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createTrainingSet(
            @PathVariable(name = "trainingId") Long trainingId,
            @RequestBody @Valid CreateTrainingSetRequest request) {
        CreateTrainingSetCommand command = requestMapper.toCreateTrainingSetCommand(request, trainingId);
        OperationResult<TrainingSet> operationResult = trainingsService.addTrainingSetToTraining(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(operationResult.getErrorMSg()));
        }

        TrainingSet trainingSet = operationResult.getResult();
        URI location = getResourceLocation(TRAINING_SET_RESOURCE_URL, trainingSet.getTraining().getId(), trainingSet.getId());
        return ResponseEntity.created(location).body(responseMapper.toTrainingSetResponse(trainingSet));
    }
}
