package pl.akolata.trainingtracker.exercises.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExerciseDto {
    private final Long id;
    private final String name;
    private final Exercise.ExerciseType type;
}
