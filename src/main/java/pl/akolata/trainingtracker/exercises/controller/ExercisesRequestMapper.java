package pl.akolata.trainingtracker.exercises.controller;

import lombok.NonNull;
import pl.akolata.trainingtracker.exercises.command.CreateExerciseCommand;
import pl.akolata.trainingtracker.exercises.dto.ExerciseDto;

class ExercisesRequestMapper {

    CreateExerciseCommand toCreateExerciseCommand(@NonNull CreateExerciseRequest request) {
        return new CreateExerciseCommand(
                request.getName(),
                request.getType()
        );
    }

    ExerciseResponse toExerciseResponse(@NonNull ExerciseDto exerciseDto) {
        return new ExerciseResponse(exerciseDto);
    }
}
