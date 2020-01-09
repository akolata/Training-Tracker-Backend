package pl.akolata.trainingtracker.training.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.akolata.trainingtracker.training.command.CreateTrainingCommand;
import pl.akolata.trainingtracker.training.command.CreateTrainingSetCommand;

@Mapper
interface TrainingRequestMapper {
    CreateTrainingCommand toCreateTrainingCommand(CreateTrainingRequest request);

    @Mappings({
            @Mapping(target = "trainingId", source = "trainingId")
    })
    CreateTrainingSetCommand toCreateTrainingSetCommand(CreateTrainingSetRequest request, Long trainingId);
}
