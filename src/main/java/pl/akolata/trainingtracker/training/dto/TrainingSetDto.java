package pl.akolata.trainingtracker.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.akolata.trainingtracker.exercises.dto.ExerciseDto;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
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
