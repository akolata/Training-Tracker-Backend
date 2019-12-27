package pl.akolata.trainingtracker.training.controller;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
class CreateTrainingSetRequest {
    @NotNull
    private Long exerciseId;
    @NotNull
    private Long trainingId;
    private Integer reps;
    private Integer weight;
    private Integer calories;
    private Integer durationInMinutes;
    private Double distanceInKm;
    private String additionalInfo;
}
