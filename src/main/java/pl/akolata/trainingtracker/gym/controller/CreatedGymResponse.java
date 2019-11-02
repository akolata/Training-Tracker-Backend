package pl.akolata.trainingtracker.gym.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.akolata.trainingtracker.gym.dto.GymDto;

@Data
@AllArgsConstructor
class CreatedGymResponse {
    private GymDto gym;
}
