package pl.akolata.trainingtracker.exercise.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

import java.util.EnumSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExercisesTypesResponse {
    private EnumSet<Exercise.ExerciseType> types;
}
