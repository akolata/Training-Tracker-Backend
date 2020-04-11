package pl.akolata.trainingtracker.gym.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.akolata.trainingtracker.gym.entity.Gym;
import pl.akolata.trainingtracker.gym.model.dto.GymDto;
import pl.akolata.trainingtracker.gym.model.dto.GymMapper;
import pl.akolata.trainingtracker.gym.query.GymQuery;
import pl.akolata.trainingtracker.gym.repository.GymRepository;
import pl.akolata.trainingtracker.gym.service.GymQueryService;

@Service
@RequiredArgsConstructor
class DatabaseGymQueryService implements GymQueryService {
    private final GymRepository gymRepository;
    private final GymMapper gymMapper;

    @Override
    public Page<GymDto> findGymsBy(GymQuery query) {
        Page<Gym> page = gymRepository.findAll(query.toSpecification(), query.getPageable());
        return page.map(gymMapper::toGymDto);
    }
}
