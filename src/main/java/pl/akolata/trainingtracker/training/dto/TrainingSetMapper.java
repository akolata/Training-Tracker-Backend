package pl.akolata.trainingtracker.training.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.akolata.trainingtracker.exercises.dto.ExerciseMapper;
import pl.akolata.trainingtracker.training.entity.TrainingSet;

@Mapper(uses = {ExerciseMapper.class})
public interface TrainingSetMapper {
    TrainingSetMapper INSTANCE = Mappers.getMapper(TrainingSetMapper.class);

    TrainingSetDto toTrainingSetDto(TrainingSet trainingSet);
}
