package pl.akolata.trainingtracker.training.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.akolata.trainingtracker.gym.dto.GymsMapper;
import pl.akolata.trainingtracker.training.entity.Training;

@Mapper(uses = {GymsMapper.class, TrainingSetMapper.class})
public interface TrainingsMapper {
    TrainingsMapper INSTANCE = Mappers.getMapper(TrainingsMapper.class);

    @Mappings({
            @Mapping(source = "sets", target = "trainingSets")
    })
    TrainingDto toTrainingDto(Training training);
}
