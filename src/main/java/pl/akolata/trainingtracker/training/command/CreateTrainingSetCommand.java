package pl.akolata.trainingtracker.training.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTrainingSetCommand {
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