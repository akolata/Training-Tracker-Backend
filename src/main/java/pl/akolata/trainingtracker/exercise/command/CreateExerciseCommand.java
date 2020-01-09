package pl.akolata.trainingtracker.exercise.command;

import lombok.Builder;
import lombok.Value;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

@Value
@Builder
public class CreateExerciseCommand {
    private final String name;
    private final Exercise.ExerciseType type;

    public Exercise toExercise() {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setType(type);
        return exercise;
    }
}
