package pl.akolata.trainingtracker.exercises.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.exercises.dto.ExerciseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ExerciseResponse {
    private ExerciseDto exercise;
}
