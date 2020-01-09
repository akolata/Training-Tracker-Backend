package pl.akolata.trainingtracker.exercise.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExerciseResponse {
    private Long id;
    private String name;
    private Exercise.ExerciseType type;
}
