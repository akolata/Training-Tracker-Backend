package pl.akolata.trainingtracker.gym.model.api;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.gym.model.dto.GymDto;

@Mapper
public interface GymResponseMapper {
    GymResponse toGymResponse(GymDto gymDto);
}
