package pl.akolata.trainingtracker.training.service;

import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.entity.TrainingSet;

public interface TrainingsService {
    OperationResult<Training> createTraining(CreateTrainingCommand command);

    OperationResult<TrainingSet> addTrainingSetToTraining(CreateTrainingSetCommand command);
}
