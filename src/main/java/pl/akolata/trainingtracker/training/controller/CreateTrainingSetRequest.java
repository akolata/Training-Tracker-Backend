package pl.akolata.trainingtracker.training.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
class CreateTrainingSetRequest {
    @NotNull
    private Long exerciseId;
    private Integer reps;
    private Integer weight;
    private Integer calories;
    private Integer durationInMinutes;
    private Double distanceInKm;
    private String additionalInfo;
}
