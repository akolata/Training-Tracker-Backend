package pl.akolata.trainingtracker.exercises.command;

import lombok.Value;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

@Value
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
