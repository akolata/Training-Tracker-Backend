package pl.akolata.trainingtracker.exercise.service.impl;

import lombok.RequiredArgsConstructor;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.dto.ValidationStep;
import pl.akolata.trainingtracker.exercise.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercise.entity.Exercise;
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository;

@RequiredArgsConstructor
class ExercisesValidationService {
    private final ExercisesRepository exercisesRepository;

    ValidationResult validateCreateExerciseCommand(CreateExerciseCommand command) {
        return new UniqueExerciseByNameAndTypeValidationStep(exercisesRepository).verify(command);
    }

    @RequiredArgsConstructor
    private static class UniqueExerciseByNameAndTypeValidationStep extends ValidationStep<CreateExerciseCommand> {

        private final ExercisesRepository exercisesRepository;

        @Override
        public ValidationResult verify(CreateExerciseCommand command) {
            String name = command.getName();
            Exercise.ExerciseType type = command.getType();
            if (exercisesRepository.existsByNameAndType(name, type)) {
                return ValidationResult.invalid(String.format("Exercise with name [%s] and type [%s] already exists", name, type));
            }

            return checkNext(command);
        }
    }
}
