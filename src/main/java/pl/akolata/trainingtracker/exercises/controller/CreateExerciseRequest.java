package pl.akolata.trainingtracker.exercises.controller;

import lombok.Data;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
class CreateExerciseRequest {

    @NotEmpty
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private Exercise.ExerciseType type;
}
