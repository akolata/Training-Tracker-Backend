package pl.akolata.trainingtracker.exercises.service;

import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.exercises.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

public interface ExercisesService {
    OperationResult<Exercise> createExercise(CreateExerciseCommand command);
}
