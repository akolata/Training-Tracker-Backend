package pl.akolata.trainingtracker.training.model.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.akolata.trainingtracker.exercise.model.dto.ExerciseMapper;
import pl.akolata.trainingtracker.training.entity.TrainingSet;

@Mapper(uses = {ExerciseMapper.class}, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TrainingSetMapper {
    TrainingSetDto toTrainingSetDto(TrainingSet trainingSet);
}
