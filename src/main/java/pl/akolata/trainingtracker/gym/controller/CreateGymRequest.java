package pl.akolata.trainingtracker.gym.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateGymRequest {

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;
}
