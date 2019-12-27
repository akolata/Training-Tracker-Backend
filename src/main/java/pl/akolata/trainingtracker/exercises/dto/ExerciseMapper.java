package pl.akolata.trainingtracker.exercises.dto;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

@Mapper
public interface ExerciseMapper {
    ExerciseDto toExerciseDto(Exercise exercise);
}
