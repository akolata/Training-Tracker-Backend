package pl.akolata.trainingtracker.exercise.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.akolata.trainingtracker.exercise.entity.Exercise
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponse

@Component
class ExerciseTestHelper {

    @Autowired
    ExercisesController exercisesController

    def sampleExerciseRequest() {
        new CreateExerciseRequest(name: "TEST_EXERCISE", type: Exercise.ExerciseType.CARDIO)
    }

    def createExercise(CreateExerciseRequest request) {
        def response = exercisesController.createExercise(request)
        (ExerciseResponse) response.body
    }

}
