package pl.akolata.trainingtracker.gym.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.trainingtracker.core.api.ApiResponse;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;
import pl.akolata.trainingtracker.gym.dto.GymDto;
import pl.akolata.trainingtracker.gym.dto.GymMapper;
import pl.akolata.trainingtracker.gym.entity.Gym;
import pl.akolata.trainingtracker.gym.service.GymService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
public class GymController extends BaseApiController {

    private static final String GYMS_URL = "/gyms";
    private static final String GYM_RESOURCE_URL = GYMS_URL + "/{id}";

    private final GymService gymService;
    private final GymMapper gymMapper = new GymMapper();
    private final GymRequestMapper gymRequestMapper = new GymRequestMapper();

    @PostMapping(path = GYMS_URL)
    ResponseEntity<ApiResponse<?>> createGym(@Valid @RequestBody CreateGymRequest request) {
        CreateGymCommand command = gymRequestMapper.toCreateGymCommand(request);
        OperationResult<Gym> operationResult = gymService.createGym(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(operationResult.getValidationResult().getErrorMsg()));
        }

        Gym createdGym = operationResult.getResult();
        GymDto gymDto = gymMapper.toGymDto(createdGym);
        URI location = getResourceLocation(GYM_RESOURCE_URL, gymDto.getId());
        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(new CreatedGymResponse(gymDto)));
    }

}
