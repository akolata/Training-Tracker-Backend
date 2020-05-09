package pl.akolata.trainingtracker.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akolata.trainingtracker.common.query.PaginationHeadersUtils;
import pl.akolata.trainingtracker.core.api.BaseApiController;
import pl.akolata.trainingtracker.core.api.ValidationErrorResponse;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;
import pl.akolata.trainingtracker.gym.entity.Gym;
import pl.akolata.trainingtracker.gym.model.api.GymResponse;
import pl.akolata.trainingtracker.gym.model.api.GymResponseMapper;
import pl.akolata.trainingtracker.gym.model.dto.GymDto;
import pl.akolata.trainingtracker.gym.model.dto.GymMapper;
import pl.akolata.trainingtracker.gym.query.GymQuery;
import pl.akolata.trainingtracker.gym.service.GymQueryService;
import pl.akolata.trainingtracker.gym.service.GymService;

import javax.validation.Valid;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
class GymController extends BaseApiController {

    private static final String GYMS_URL = "/gyms";
    private static final String GYM_RESOURCE_URL = GYMS_URL + "/{id}";

    private final GymService gymService;
    private final GymMapper gymMapper;
    private final GymRequestMapper gymRequestMapper;
    private final GymResponseMapper gymResponseMapper;
    private final GymQueryService gymQueryService;

    @PostMapping(path = GYMS_URL)
    ResponseEntity<?> createGym(@Valid @RequestBody CreateGymRequest request) {
        CreateGymCommand command = gymRequestMapper.toCreateGymCommand(request);
        OperationResult<Gym> operationResult = gymService.createGym(command);
        if (operationResult.isFailure()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(operationResult.getErrorMSg()));
        }

        Gym createdGym = operationResult.getResult();
        GymDto gymDto = gymMapper.toGymDto(createdGym);
        URI location = getResourceLocation(GYM_RESOURCE_URL, gymDto.getId());
        return ResponseEntity
                .created(location)
                .body(gymResponseMapper.toGymResponse(gymDto));
    }

    @GetMapping(path = GYMS_URL)
    ResponseEntity<FindGymsResponse> browseGyms(
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {
        GymQuery query = new GymQuery(name, pageable);
        Page<GymDto> gyms = gymQueryService.findGymsBy(query);
        List<GymResponse> gymsAPIModel = new LinkedList<>();
        gyms.forEach(gym -> gymsAPIModel.add(gymResponseMapper.toGymResponse(gym)));
        return ResponseEntity
                .ok()
                .headers(PaginationHeadersUtils.buildPaginationHeaders(gyms))
                .body(new FindGymsResponse(gymsAPIModel));
    }

}
