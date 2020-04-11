package pl.akolata.trainingtracker.gym.service;

import org.springframework.data.domain.Page;
import pl.akolata.trainingtracker.gym.model.dto.GymDto;
import pl.akolata.trainingtracker.gym.query.GymQuery;

public interface GymQueryService {
    Page<GymDto> findGymsBy(GymQuery query);
}
