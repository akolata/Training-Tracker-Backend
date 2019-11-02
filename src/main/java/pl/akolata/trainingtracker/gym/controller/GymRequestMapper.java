package pl.akolata.trainingtracker.gym.controller;

import pl.akolata.trainingtracker.gym.command.CreateGymCommand;

class GymRequestMapper {
    CreateGymCommand toCreateGymCommand(CreateGymRequest request) {
        return new CreateGymCommand(request.getName());
    }
}
