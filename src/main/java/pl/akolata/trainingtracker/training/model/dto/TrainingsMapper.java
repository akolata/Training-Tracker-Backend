package pl.akolata.trainingtracker.training.model.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.akolata.trainingtracker.gym.model.dto.GymMapper;
import pl.akolata.trainingtracker.training.entity.Training;

@Mapper(uses = {GymMapper.class, TrainingSetMapper.class})
public interface TrainingsMapper {
    @Mappings({
            @Mapping(source = "sets", target = "trainingSets")
    })
    TrainingDto toTrainingDto(Training training);
}
