package pl.akolata.trainingtracker.gym.service.impl;

import lombok.AllArgsConstructor;
import pl.akolata.trainingtracker.common.validation.CommandConstraintsValidationStep;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.dto.ValidationStep;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;
import pl.akolata.trainingtracker.gym.repository.GymRepository;

@AllArgsConstructor
class GymValidationService {
    private final GymRepository gymRepository;

    ValidationResult validateCreateGymCommand(CreateGymCommand command) {
        return new CommandConstraintsValidationStep<CreateGymCommand>()
                .linkWith(new GymNameDuplicationValidationStep(gymRepository))
                .verify(command);
    }

    @AllArgsConstructor
    private static class GymNameDuplicationValidationStep extends ValidationStep<CreateGymCommand> {
        private final GymRepository gymRepository;

        @Override
        public ValidationResult verify(CreateGymCommand command) {
            if (gymRepository.existsByName(command.getName())) {
                return ValidationResult.invalid(String.format("Gym with name [%s] already exists", command.getName()));
            }

            return checkNext(command);
        }
    }

}
