package pl.akolata.trainingtracker.gym.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
class CreateGymRequest {

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;
}
