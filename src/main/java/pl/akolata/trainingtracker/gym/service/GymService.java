package pl.akolata.trainingtracker.gym.service;

import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;
import pl.akolata.trainingtracker.gym.entity.Gym;

public interface GymService {
    OperationResult<Gym> createGym(CreateGymCommand command);
}
