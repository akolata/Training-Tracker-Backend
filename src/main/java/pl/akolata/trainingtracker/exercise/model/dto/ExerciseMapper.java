package pl.akolata.trainingtracker.exercise.model.dto;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

@Mapper
public interface ExerciseMapper {
    ExerciseDto toExerciseDto(Exercise exercise);
}
