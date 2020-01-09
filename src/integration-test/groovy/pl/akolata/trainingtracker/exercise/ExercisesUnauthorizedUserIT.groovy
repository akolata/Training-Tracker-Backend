package pl.akolata.trainingtracker.exercise

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.akolata.trainingtracker.exercise.controller.CreateExerciseRequest
import pl.akolata.trainingtracker.exercise.entity.Exercise
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ExercisesUnauthxrorizedUserIT extends Specification {

    private static final String EXERCISES_URL = "/api/exercises"

    @Autowired
    MockMvc mvc

    @Autowired
    ExercisesRepository exercisesRepository

    @Autowired
    ObjectMapper om

    def setup() {
        exercisesRepository.deleteAll()
    }

    def cleanup() {
        exercisesRepository.deleteAll()
    }

    def "should NOT allow to create exercise if user is not authenticated"() {
        given: "valid exercise creation data"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        def request = createExerciseRequest(name, type)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))

        then: "status will be 401"
        result.andExpect(status().isUnauthorized())
    }

    def createExerciseRequest(String name, Exercise.ExerciseType type) {
        new CreateExerciseRequest(name: name, type: type)
    }
}
