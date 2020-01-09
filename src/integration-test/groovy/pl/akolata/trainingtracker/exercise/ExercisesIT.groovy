package pl.akolata.trainingtracker.exercise

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.akolata.trainingtracker.exercise.controller.CreateExerciseRequest
import pl.akolata.trainingtracker.exercise.entity.Exercise
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponse
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository
import pl.akolata.trainingtracker.test.annotation.IntegrationTest
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@IntegrationTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ExercisesIT extends Specification {

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

    def "should create exercise"() {
        given: "valid exercise creation data"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        def request = createExerciseRequest(name, type)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        def responseJSON = result.andReturn().response.contentAsString
        def response = om.readValue(responseJSON, ExerciseResponse)

        then: "status will be 201 created"
        result.andExpect(status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(jsonPath('$.id').exists())
        result.andExpect(jsonPath('$.name').value(name))
        result.andExpect(jsonPath('$.type').value(type.name()))

        responseExerciseDataMatchRequest(request, response)

        and: "response should contain location header, with registered exercise id"
        result.andReturn().response.containsHeader(HttpHeaders.LOCATION)
        result.andReturn().response.getHeader(HttpHeaders.LOCATION).endsWith("/api/exercises/${response.id}")

        when: "exercise will be queried in the database"
        def exerciseOpt = exercisesRepository.findById(response.id)

        then: "it will be found"
        exerciseOpt.isPresent()
        def exercise = exerciseOpt.get()

        and: "it will contains same data as in request"
        exerciseDataMatchRequest(request, exercise)
    }

    def "should create an exercise, if exercise with the same name but different type already exists"() {
        given: "one exercise saved in the database"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        exercisesRepository.saveAndFlush(new Exercise(name: name, type: type))

        and: "create exercise request, with the same name but different type"
        def otherType = Exercise.ExerciseType.GROUP_WORKOUT
        def request = createExerciseRequest(name, otherType)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        def responseJSON = result.andReturn().response.contentAsString
        def response = om.readValue(responseJSON, ExerciseResponse)

        then: "status will be 201 created"
        result.andExpect(status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(jsonPath('$.id').exists())
        result.andExpect(jsonPath('$.name').value(name))
        result.andExpect(jsonPath('$.type').value(otherType.name()))

        responseExerciseDataMatchRequest(request, response)

        and: "response should contain location header, with registered exercise id"
        result.andReturn().response.containsHeader(HttpHeaders.LOCATION)
        result.andReturn().response.getHeader(HttpHeaders.LOCATION).endsWith("/api/exercises/${response.id}")

        when: "exercise will be queried in the database"
        def exerciseOpt = exercisesRepository.findById(response.id)

        then: "it will be found"
        exerciseOpt.isPresent()
        def exercise = exerciseOpt.get()

        and: "it will contains same data as in request"
        exerciseDataMatchRequest(request, exercise)

        and: "there will be 2 exercises in the database"
        exercisesRepository.count() == 2
    }

    def "should create an exercise, if exercise with the same type but different name already exists"() {
        given: "one exercise saved in the database"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        exercisesRepository.saveAndFlush(new Exercise(name: name, type: type))

        and: "create exercise request, with the same name but different type"
        def otherName = "MMA"
        def request = createExerciseRequest(otherName, type)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        def responseJSON = result.andReturn().response.contentAsString
        def response = om.readValue(responseJSON, ExerciseResponse)

        then: "status will be 201 created"
        result.andExpect(status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(jsonPath('$.id').exists())
        result.andExpect(jsonPath('$.name').value(otherName))
        result.andExpect(jsonPath('$.type').value(type.name()))

        responseExerciseDataMatchRequest(request, response)

        and: "response should contain location header, with registered exercise id"
        result.andReturn().response.containsHeader(HttpHeaders.LOCATION)
        result.andReturn().response.getHeader(HttpHeaders.LOCATION).endsWith("/api/exercises/${response.id}")

        when: "exercise will be queried in the database"
        def exerciseOpt = exercisesRepository.findById(response.id)

        then: "it will be found"
        exerciseOpt.isPresent()
        def exercise = exerciseOpt.get()

        and: "it will contains same data as in request"
        exerciseDataMatchRequest(request, exercise)

        and: "there will be 2 exercises in the database"
        exercisesRepository.count() == 2
    }

    def "should NOT create an exercise, if exercise with the same name and type already exists"() {
        given: "one exercise saved in the database"
        def name = "Running"
        def type = Exercise.ExerciseType.CARDIO
        exercisesRepository.saveAndFlush(new Exercise(name: name, type: type))

        and: "create exercise request, with the same name and type"
        def request = createExerciseRequest(name, type)

        when: "request will be performed"
        def resultAction = mvc.perform(MockMvcRequestBuilders
                .post(EXERCISES_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then: "response status will be 400"
        resultAction.andExpect(status().isBadRequest())

        and: "response will contain error message"
        resultAction.andExpect(jsonPath('$.errorMsg', is("Exercise with name [Running] and type [CARDIO] already exists")))

        and: "the number of exercises in the database is still the same"
        exercisesRepository.count() == 1
    }


    def createExerciseRequest(String name, Exercise.ExerciseType type) {
        new CreateExerciseRequest(name: name, type: type)
    }

    void responseExerciseDataMatchRequest(CreateExerciseRequest request, ExerciseResponse exercise) {
        assert request.name == exercise.name
        assert request.type == exercise.type
    }

    void exerciseDataMatchRequest(CreateExerciseRequest request, Exercise exercise) {
        assert request.name == exercise.name
        assert request.type == exercise.type
    }
}
