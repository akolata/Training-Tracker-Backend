package pl.akolata.trainingtracker.exercises.dto;

import lombok.NonNull;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

public class ExerciseMapper {

    public ExerciseDto toExerciseDto(@NonNull Exercise exercise) {
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getType()
        );
    }
}
