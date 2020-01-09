package pl.akolata.trainingtracker.gym.controller;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.gym.command.CreateGymCommand;

@Mapper
interface GymRequestMapper {
    CreateGymCommand toCreateGymCommand(CreateGymRequest request);
}
