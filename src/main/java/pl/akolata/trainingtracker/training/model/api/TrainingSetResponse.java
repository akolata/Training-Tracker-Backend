package pl.akolata.trainingtracker.training.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TrainingSetResponse {
    private Long id;
    private ExerciseResponse exercise;
    private Long trainingId;
    private Integer reps;
    private Integer weight;
    private Integer calories;
    private Integer durationInMinutes;
    private Double distanceInKm;
    private String additionalInfo;
}