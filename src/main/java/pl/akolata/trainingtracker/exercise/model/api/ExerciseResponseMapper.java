package pl.akolata.trainingtracker.exercise.model.api;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.exercise.model.dto.ExerciseDto;

@Mapper
public interface ExerciseResponseMapper {
    ExerciseResponse toExerciseResponse(ExerciseDto exerciseDto);
}
