package pl.akolata.trainingtracker.exercises.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.exercises.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercises.entity.Exercise;
import pl.akolata.trainingtracker.exercises.repository.ExercisesRepository;
import pl.akolata.trainingtracker.exercises.service.ExercisesService;

@Service
class DatabaseExercisesService implements ExercisesService {

    private final ExercisesRepository exercisesRepository;
    private final ExercisesValidationService validationService;

    @Autowired
    public DatabaseExercisesService(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
        this.validationService = new ExercisesValidationService(exercisesRepository);
    }

    @Transactional
    @Override
    public OperationResult<Exercise> createExercise(CreateExerciseCommand command) {
        ValidationResult validationResult = validationService.validateCreateExerciseCommand(command);
        if (validationResult.notValid()) {
            return OperationResult.failure(validationResult);
        }

        Exercise exercise = exercisesRepository.saveAndFlush(command.toExercise());
        return OperationResult.success(exercise);
    }
}
