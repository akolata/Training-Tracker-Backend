package pl.akolata.trainingtracker.exercise.controller;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.exercise.command.CreateExerciseCommand;

@Mapper
interface ExercisesRequestMapper {
    CreateExerciseCommand toCreateExerciseCommand(CreateExerciseRequest request);
}
