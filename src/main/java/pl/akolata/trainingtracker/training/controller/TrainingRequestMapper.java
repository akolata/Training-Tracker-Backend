package pl.akolata.trainingtracker.training.controller;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;

@Mapper
interface TrainingRequestMapper {
    TrainingRequestMapper INSTANCE = Mappers.getMapper(TrainingRequestMapper.class);

    CreateTrainingCommand toCreateTrainingCommand(CreateTrainingRequest request);

    CreateTrainingSetCommand toCreateTrainingSetCommand(CreateTrainingSetRequest request);
}
