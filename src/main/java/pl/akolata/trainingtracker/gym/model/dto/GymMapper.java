package pl.akolata.trainingtracker.gym.model.dto;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.gym.entity.Gym;

@Mapper
public interface GymMapper {
    GymDto toGymDto(Gym gym);
}
