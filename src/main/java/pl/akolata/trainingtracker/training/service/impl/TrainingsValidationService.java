package pl.akolata.trainingtracker.training.service.impl;

import lombok.RequiredArgsConstructor;
import pl.akolata.trainingtracker.common.validation.CommandConstraintsValidationStep;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.dto.ValidationStep;
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository;
import pl.akolata.trainingtracker.gym.repository.GymRepository;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;
import pl.akolata.trainingtracker.training.repository.TrainingsRepository;
import pl.akolata.trainingtracker.user.service.UserRepository;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
class TrainingsValidationService {

    private final GymRepository gymRepository;
    private final TrainingsRepository trainingsRepository;
    private final ExercisesRepository exercisesRepository;
    private final UserRepository userRepository;

    ValidationResult validateCreateTrainingCommand(CreateTrainingCommand command) {
        return new CommandConstraintsValidationStep<CreateTrainingCommand>()
                .linkWith(new GymWithGivenIdExistsValidationStep(gymRepository))
                .linkWith(new UserIdMustBeValidValidationStep(userRepository))
                .verify(command);
    }

    ValidationResult validateCreateTrainingSetCommand(CreateTrainingSetCommand command) {
        return new CommandConstraintsValidationStep<CreateTrainingSetCommand>()
                .linkWith(new AtLeastOnePropertyIsNotNullValidationStep())
                .linkWith(new TrainingWithGivenIdMustExistValidationStep(trainingsRepository))
                .linkWith(new ExerciseWithGivenIdMustExistValidationStep(exercisesRepository))
                .verify(command);
    }

    @RequiredArgsConstructor
    private static class GymWithGivenIdExistsValidationStep extends ValidationStep<CreateTrainingCommand> {
        private final GymRepository gymRepository;

        @Override
        public ValidationResult verify(CreateTrainingCommand command) {
            Long gymId = command.getGymId();
            if (gymId != null && gymRepository.findById(gymId).isEmpty()) {
                return ValidationResult.invalid(String.format("Gym with id [%s] doesn't exist", gymId));
            }

            return checkNext(command);
        }
    }

    @RequiredArgsConstructor
    private static class UserIdMustBeValidValidationStep extends ValidationStep<CreateTrainingCommand> {
        private final UserRepository userRepository;

        @Override
        public ValidationResult verify(CreateTrainingCommand command) {
            Long userId = command.getUserId();

            if (userId == null) {
                return ValidationResult.invalid("User id must not be null");
            }

            if (userRepository.findById(userId).isEmpty()) {
                return ValidationResult.invalid(String.format("User with id [%s] doesn't exist", userId));
            }

            return checkNext(command);
        }
    }

    @RequiredArgsConstructor
    private static class ExerciseWithGivenIdMustExistValidationStep extends ValidationStep<CreateTrainingSetCommand> {
        private final ExercisesRepository exercisesRepository;

        @Override
        public ValidationResult verify(CreateTrainingSetCommand command) {
            Long exerciseId = command.getExerciseId();

            if (exerciseId == null) {
                return ValidationResult.invalid("Exercise id must not be null");
            }

            if (exercisesRepository.findById(exerciseId).isEmpty()) {
                return ValidationResult.invalid(String.format("Exercise with id [%s] doesn't exist", exerciseId));
            }

            return checkNext(command);
        }
    }

    @RequiredArgsConstructor
    private static class TrainingWithGivenIdMustExistValidationStep extends ValidationStep<CreateTrainingSetCommand> {
        private final TrainingsRepository trainingsRepository;

        @Override
        public ValidationResult verify(CreateTrainingSetCommand command) {
            Long trainingId = command.getTrainingId();

            if (trainingId == null) {
                return ValidationResult.invalid("Training id must not be null");
            }

            if (trainingsRepository.findById(trainingId).isEmpty()) {
                return ValidationResult.invalid(String.format("Training with id [%s] doesn't exist", trainingId));
            }
            return checkNext(command);
        }
    }

    @RequiredArgsConstructor
    private static class AtLeastOnePropertyIsNotNullValidationStep extends ValidationStep<CreateTrainingSetCommand> {
        @Override
        public ValidationResult verify(CreateTrainingSetCommand command) {

            if (allArgumentsAreNull(
                    command.getReps(),
                    command.getWeight(),
                    command.getCalories(),
                    command.getDurationInMinutes(),
                    command.getDistanceInKm(),
                    command.getAdditionalInfo())) {
                return ValidationResult.invalid("At least one property describing training set has to be provided");
            }

            return checkNext(command);
        }

        private boolean allArgumentsAreNull(Object... args) {
            return Arrays
                    .stream(args)
                    .allMatch(Objects::isNull);
        }
    }
}
