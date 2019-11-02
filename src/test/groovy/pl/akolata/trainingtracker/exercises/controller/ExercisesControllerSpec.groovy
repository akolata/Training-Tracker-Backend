package pl.akolata.trainingtracker.exercises.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.akolata.trainingtracker.exercises.entity.Exercise
import pl.akolata.trainingtracker.exercises.repository.ExercisesRepository
import spock.lang.Specification

import static pl.akolata.utils.SpecificationHelper.asJsonString

@SpringBootTest
@AutoConfigureMockMvc
class ExercisesControllerSpec extends Specification {

    private static final String EXERCISES_URL = "/api/exercises"

    @Autowired
    MockMvc mvc

    @Autowired
    ExercisesRepository exercisesRepository

    def setup() {
        exercisesRepository.deleteAll()
    }

    def cleanup() {
        exercisesRepository.deleteAll()
    }

    @WithMockUser
    def "should create exercise"() {
        given: "valid exercise creation data"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        def request = createExerciseRequest(name, type)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))


        then: "status will be 201 created"
        result.andExpect(MockMvcResultMatchers.status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(MockMvcResultMatchers.jsonPath('$.exercise').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.exercise.id').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.exercise.name').value(name))
        result.andExpect(MockMvcResultMatchers.jsonPath('$.exercise.type').value(type.name()))
    }

    def createExerciseRequest(String name, Exercise.ExerciseType type) {
        return new CreateExerciseRequest(name: name, type: type)
    }
}
