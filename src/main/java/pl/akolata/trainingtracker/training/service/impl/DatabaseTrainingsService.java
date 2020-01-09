package pl.akolata.trainingtracker.training.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository;
import pl.akolata.trainingtracker.gym.repository.GymRepository;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;
import pl.akolata.trainingtracker.training.command.TrainingsCommandsMapper;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.entity.TrainingSet;
import pl.akolata.trainingtracker.training.repository.TrainingsRepository;
import pl.akolata.trainingtracker.training.service.TrainingsService;
import pl.akolata.trainingtracker.user.service.UserRepository;

import java.util.Optional;

@Slf4j
@Service
class DatabaseTrainingsService implements TrainingsService {

    private final TrainingsRepository trainingsRepository;
    private final TrainingsValidationService validationService;
    private final TrainingsCommandsMapper commandsMapper;

    @Autowired
    public DatabaseTrainingsService(
            GymRepository gymRepository,
            TrainingsRepository trainingsRepository,
            ExercisesRepository exercisesRepository,
            UserRepository userRepository) {
        this.trainingsRepository = trainingsRepository;
        this.validationService = new TrainingsValidationService(gymRepository, trainingsRepository, exercisesRepository,
                userRepository);
        this.commandsMapper = new TrainingsCommandsMapper(gymRepository, userRepository, exercisesRepository);
    }

    @Transactional
    @Override
    public OperationResult<Training> createTraining(CreateTrainingCommand command) {
        ValidationResult validationResult = validationService.validateCreateTrainingCommand(command);
        if (validationResult.notValid()) {
            return OperationResult.failure(validationResult);
        }

        Training training = trainingsRepository.save(commandsMapper.toTraining(command));
        return OperationResult.success(training);
    }

    @Transactional
    @Override
    public OperationResult<TrainingSet> addTrainingSetToTraining(CreateTrainingSetCommand command) {
        ValidationResult validationResult = validationService.validateCreateTrainingSetCommand(command);
        if (validationResult.notValid()) {
            return OperationResult.failure(validationResult);
        }

        Optional<Training> trainingOpt = trainingsRepository.findById(command.getTrainingId());
        if (trainingOpt.isEmpty()) {
            log.error("At this point training id should be validated.");
            return OperationResult.failure(
                    ValidationResult.invalid(
                            String.format("Training with id [%s] doesn't exist", command.getTrainingId())));
        }

        Training training = trainingOpt.get();
        TrainingSet trainingSet = commandsMapper.toTrainingSet(command);
        training.addTrainingSet(trainingSet);
        return OperationResult.success(trainingSet);
    }
}
