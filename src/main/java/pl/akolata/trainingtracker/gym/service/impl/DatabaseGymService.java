package pl.akolata.trainingtracker.gym.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;
import pl.akolata.trainingtracker.gym.entity.Gym;
import pl.akolata.trainingtracker.gym.repository.GymRepository;
import pl.akolata.trainingtracker.gym.service.GymService;

@Service
@RequiredArgsConstructor
class DatabaseGymService implements GymService {

    private final GymRepository gymRepository;
    private final GymValidationService gymValidationService;

    @Autowired
    public DatabaseGymService(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
        this.gymValidationService = new GymValidationService(gymRepository);
    }

    @Transactional
    @Override
    public OperationResult<Gym> createGym(CreateGymCommand command) {
        ValidationResult validationResult = gymValidationService.validateCreateGymCommand(command);
        if (validationResult.notValid()) {
            return OperationResult.failure(validationResult);
        }

        Gym gym = command.toGym();
        gym = gymRepository.save(gym);
        return OperationResult.success(gym);
    }
}
