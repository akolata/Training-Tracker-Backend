package pl.akolata.trainingtracker.exercise.service;

import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.exercise.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

public interface ExercisesService {
    OperationResult<Exercise> createExercise(CreateExerciseCommand command);
}
