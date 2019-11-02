package pl.akolata.trainingtracker.exercises.dto;

import lombok.Value;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

@Value
public class ExerciseDto {
    private final Long id;
    private final String name;
    private final Exercise.ExerciseType type;
}
