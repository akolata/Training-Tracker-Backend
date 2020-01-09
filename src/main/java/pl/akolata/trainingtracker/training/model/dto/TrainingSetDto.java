package pl.akolata.trainingtracker.training.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.akolata.trainingtracker.exercise.model.dto.ExerciseDto;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingSetDto {
    private final Long id;
    private final ExerciseDto exercise;
    private final TrainingDto training;
    private final Integer reps;
    private final Integer weight;
    private final Integer calories;
    private final Integer durationInMinutes;
    private final Double distanceInKm;
    private final String additionalInfo;
}
