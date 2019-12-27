package pl.akolata.trainingtracker.gym.dto;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.gym.entity.Gym;

@Mapper
public interface GymsMapper {
    GymDto toGymDto(Gym gym);
}
