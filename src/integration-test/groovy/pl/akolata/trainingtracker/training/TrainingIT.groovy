package pl.akolata.trainingtracker.training

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.akolata.trainingtracker.auth.AuthTestHelper
import pl.akolata.trainingtracker.exercise.controller.ExerciseTestHelper
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponse
import pl.akolata.trainingtracker.exercise.repository.ExercisesRepository
import pl.akolata.trainingtracker.gym.controller.GymTestHelper
import pl.akolata.trainingtracker.gym.model.api.GymResponse
import pl.akolata.trainingtracker.gym.repository.GymRepository
import pl.akolata.trainingtracker.test.annotation.EmbeddedDatabase
import pl.akolata.trainingtracker.test.annotation.IntegrationTest
import pl.akolata.trainingtracker.training.controller.CreateTrainingRequest
import pl.akolata.trainingtracker.training.controller.CreateTrainingSetRequest
import pl.akolata.trainingtracker.training.entity.Training
import pl.akolata.trainingtracker.training.entity.TrainingSet
import pl.akolata.trainingtracker.training.model.api.TrainingResponse
import pl.akolata.trainingtracker.training.model.api.TrainingSetResponse
import pl.akolata.trainingtracker.training.repository.TrainingsRepository
import pl.akolata.trainingtracker.user.service.RoleRepository
import pl.akolata.trainingtracker.user.service.UserRepository
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@IntegrationTest
@AutoConfigureMockMvc
@EmbeddedDatabase
class TrainingIT extends Specification {

    static final TRAININGS_URL = "/api/trainings"

    @Autowired
    AuthTestHelper authTestHelper

    @Autowired
    GymTestHelper gymTestHelper

    @Autowired
    ExerciseTestHelper exerciseTestHelper

    @Autowired
    UserRepository userRepository

    @Autowired
    RoleRepository roleRepository

    @Autowired
    GymRepository gymRepository

    @Autowired
    TrainingsRepository trainingsRepository

    @Autowired
    ExercisesRepository exercisesRepository

    @Autowired
    ObjectMapper om

    @Autowired
    MockMvc mvc

    def setup() {
        trainingsRepository.deleteAll()
        exercisesRepository.deleteAll()
        gymRepository.deleteAll()
        userRepository.deleteAll()
    }

    def cleanup() {
        trainingsRepository.deleteAll()
        exercisesRepository.deleteAll()
        gymRepository.deleteAll()
        userRepository.deleteAll()
        roleRepository.deleteAll()
    }

    def "should create training"() {
        given: "user created in the database"
        def user = authTestHelper.signUpAndReturnUser()

        and: "valid create training request"
        def request = validCreateTrainingRequestWithoutGymAssigned(user.id)
        def requestJSON = om.writeValueAsString(request)

        and: "generated JWT token"
        def jwt = authTestHelper.signInAndReturnJWToken()

        when: "it will be executed"
        def resultActions = mvc.perform(post(TRAININGS_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON)
                .headers(authTestHelper.buildHttpHeadersWithJWTToken(jwt)))
                .andDo(print())
        def result = resultActions.andReturn()
        def responseJSON = result.response.contentAsString
        def response = om.readValue(responseJSON, TrainingResponse)

        then: "status will be 201 created"
        resultActions.andExpect(status().isCreated())

        and: "response contains same training properties as request"
        responseTrainingDataMatchRequest(request, response)

        and: "response should contain location header, with created training id"
        result.response.containsHeader(HttpHeaders.LOCATION)
        result.response.getHeader(HttpHeaders.LOCATION).endsWith("/api/trainings/${response.id}")

        when: "training will be queried in the database"
        def trainingOpt = trainingsRepository.findById(response.id)

        then: "it will be found"
        trainingOpt.isPresent()
        def training = trainingOpt.get()

        and: "it will contains same data as in request"
        trainingDataMatchRequest(request, training)
    }

    def "should create training with gym"() {
        given: "user created in the database"
        def user = authTestHelper.signUpAndReturnUser()

        and: "gym created in the database"
        def gymRequest = gymTestHelper.validCreateGymRequest()
        def gym = gymTestHelper.createGym(gymRequest)

        and: "valid create training request"
        def request = validCreateTrainingRequest(user.id, gym.id)
        def requestJSON = om.writeValueAsString(request)

        and: "generated JWT token"
        def jwt = authTestHelper.signInAndReturnJWToken()

        when: "it will be executed"
        def resultActions = mvc.perform(post(TRAININGS_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON)
                .headers(authTestHelper.buildHttpHeadersWithJWTToken(jwt)))
                .andDo(print())
        def result = resultActions.andReturn()
        def responseJSON = result.response.contentAsString
        def response = om.readValue(responseJSON, TrainingResponse)

        then: "status will be 201 created"
        resultActions.andExpect(status().isCreated())

        and: "response contains same training properties as request"
        responseTrainingDataMatchRequest(request, response, gym.id, gym.name)

        and: "response should contain location header, with created training id"
        result.response.containsHeader(HttpHeaders.LOCATION)
        result.response.getHeader(HttpHeaders.LOCATION).endsWith("/api/trainings/${response.id}")

        when: "training will be queried in the database"
        def trainingOpt = trainingsRepository.findById(response.id)

        then: "it will be found"
        trainingOpt.isPresent()
        def training = trainingOpt.get()

        and: "it will contains same data as in request"
        trainingDataMatchRequest(request, training)

        and: "gym will be assigned to training"
        trainingHasGymAssigned(training, gym)
    }

    def "should add training set to an existing training"() {
        given: "user created in the database"
        def user = authTestHelper.signUpAndReturnUser()
        def jwt = authTestHelper.signInAndReturnJWToken()

        and: "gym created in the database"
        def gymRequest = gymTestHelper.validCreateGymRequest()
        def gym = gymTestHelper.createGym(gymRequest)

        and: "exercise created in the database"
        def exerciseRequest = exerciseTestHelper.sampleExerciseRequest()
        def exercise = exerciseTestHelper.createExercise(exerciseRequest)

        and: "training created in the database"
        def trainingRequest = validCreateTrainingRequest(user.id, gym.id)
        def trainingRequestJSON = om.writeValueAsString(trainingRequest)
        def resultActions = mvc.perform(post(TRAININGS_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(trainingRequestJSON)
                .headers(authTestHelper.buildHttpHeadersWithJWTToken(jwt)))
                .andDo(print())
        def result = resultActions.andReturn()
        def responseJSON = result.response.contentAsString
        def trainingResponse = om.readValue(responseJSON, TrainingResponse)

        and: "create training set request"
        def trainingSetRequest = createTrainingSetRequest(exercise.id)
        def trainingSetRequestJSON = om.writeValueAsString(trainingSetRequest)

        when: "training set will be created"
        resultActions = mvc.perform(post("/api/trainings/${trainingResponse.id}/sets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(trainingSetRequestJSON)
                .headers(authTestHelper.buildHttpHeadersWithJWTToken(jwt)))
                .andDo(print())
        result = resultActions.andReturn()
        responseJSON = result.response.contentAsString
        def trainingSetResponse = om.readValue(responseJSON, TrainingSetResponse)

        then: "training set will be created, and response will contain same training set data as request"
        responseTrainingSetDataMatchRequest(trainingSetRequest, trainingSetResponse, trainingResponse.id, exercise)

        and: "response should contain location header, with created training set id"
        result.response.containsHeader(HttpHeaders.LOCATION)
        result.response.getHeader(HttpHeaders.LOCATION).endsWith("/api/trainings/${trainingResponse.id}/sets/${trainingSetResponse.id}")

        when: "training will be queried in the database"
        def trainingOpt = trainingsRepository.findById(trainingResponse.id)

        then: "it will be found"
        trainingOpt.isPresent()
        def training = trainingOpt.get()

        and: "it will have sets assigned"
        training.sets.size() == 1

        and: "created training set will have the same properties as in the request"
        def trainingSet = training.sets.find { it -> it.id == trainingSetResponse.id }
        trainingSetDataMatchRequest(trainingSetRequest, trainingSet, exercise)
    }


    def validCreateTrainingRequestWithoutGymAssigned(Long userId) {
        new CreateTrainingRequest(date: LocalDate.now(), userId: userId, gymId: null, additionalInfo: "Easy", name: "my workout")
    }

    def validCreateTrainingRequest(Long userId, Long gymId) {
        new CreateTrainingRequest(date: LocalDate.now(), userId: userId, gymId: gymId, additionalInfo: "Easy", name: "my workout")
    }

    def createTrainingSetRequest(Long exerciseId) {
        new CreateTrainingSetRequest(exerciseId: exerciseId, reps: 1, weight: 1, calories: 100,
                durationInMinutes: 10, distanceInKm: 1, additionalInfo: "too easy")
    }

    void responseTrainingDataMatchRequest(CreateTrainingRequest request, TrainingResponse response) {
        assert request.name == response.name
        assert request.date == response.date
        assert request.additionalInfo == response.additionalInfo
    }

    void responseTrainingDataMatchRequest(CreateTrainingRequest request, TrainingResponse response, Long gymId, String gymName) {
        assert request.name == response.name
        assert request.date == response.date
        assert request.additionalInfo == response.additionalInfo
        assert gymId == response.gym.id
        assert gymName == gymName
    }

    void trainingDataMatchRequest(CreateTrainingRequest request, Training training) {
        assert request.name == training.name
        assert request.date == training.date
        assert request.additionalInfo == training.additionalInfo
    }

    void trainingHasGymAssigned(Training training, GymResponse gymResponse) {
        assert training.gym != null
        assert training.gym.id == gymResponse.id
        assert training.gym.name == gymResponse.name
    }

    void responseTrainingSetDataMatchRequest(CreateTrainingSetRequest request, TrainingSetResponse response,
                                             Long trainingId, ExerciseResponse exercise) {
        assert response.id != null
        assert response.trainingId == trainingId
        assert response.exercise != null
        assert response.exercise.id == exercise.id
        assert response.exercise.name == exercise.name
        assert response.exercise.type == exercise.type
        assert response.reps == request.reps
        assert response.weight == request.weight
        assert response.calories == request.calories
        assert response.durationInMinutes == request.durationInMinutes
        assert response.distanceInKm == request.distanceInKm
        assert response.additionalInfo == request.additionalInfo
    }

    void trainingSetDataMatchRequest(CreateTrainingSetRequest request, TrainingSet trainingSet, ExerciseResponse exerciseResponse) {
        assert trainingSet.exercise != null
        assert trainingSet.exercise.id == exerciseResponse.id
        assert trainingSet.exercise.name == exerciseResponse.name
        assert trainingSet.exercise.type == exerciseResponse.type
        assert trainingSet.reps == request.reps
        assert trainingSet.weight == request.weight
        assert trainingSet.calories == request.calories
        assert trainingSet.durationInMinutes == request.durationInMinutes
        assert trainingSet.distanceInKm == request.distanceInKm
        assert trainingSet.additionalInfo == request.additionalInfo
    }
}
