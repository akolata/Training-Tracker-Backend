package pl.akolata.trainingtracker.gym

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.akolata.trainingtracker.gym.controller.CreateGymRequest
import pl.akolata.trainingtracker.gym.entity.Gym
import pl.akolata.trainingtracker.gym.model.api.GymResponse
import pl.akolata.trainingtracker.gym.repository.GymRepository
import pl.akolata.trainingtracker.test.annotation.EmbeddedTruncatedAndInitialisedDatabase
import pl.akolata.trainingtracker.test.annotation.IntegrationTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.hasItem
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@IntegrationTest
@AutoConfigureMockMvc
@EmbeddedTruncatedAndInitialisedDatabase
class GymIT extends Specification {

    private static final String GYMS_URL = "/api/gyms"

    @Autowired
    MockMvc mvc

    @Autowired
    GymRepository gymRepository

    @Autowired
    ObjectMapper om

    def "should create gym"() {
        given: "valid gym creation data"
        def gymName = "City Fit"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        def resultActions = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        def result = resultActions.andReturn()
        def responseJSON = result.response.contentAsString
        def response = om.readValue(responseJSON, GymResponse)

        then: "status will be 201 created"
        resultActions.andExpect(status().isCreated())

        and: "response will contain data about the created gym"
        resultActions.andExpect(jsonPath('$.id').exists())
        resultActions.andExpect(jsonPath('$.name').value(gymName))

        responseGymDataMatchRequest(request, response)

        and: "response should contain location header, with created gym id"
        result.response.containsHeader(HttpHeaders.LOCATION)
        result.response.getHeader(HttpHeaders.LOCATION).endsWith("/api/gyms/${response.id}")

        when: "gym will be queried in the database"
        def gymOpt = gymRepository.findById(response.id)

        then: "it will be found"
        gymOpt.isPresent()
        def gym = gymOpt.get()

        and: "it will contains same data as in request"
        gymDataMatchRequest(request, gym)
    }

    @Unroll
    def "should NOT create gym if request is invalid because [#_reason]"() {
        given: "invalid gym creation data"
        def request = createGymRequest(_gymName)

        when: "POST request will be executed"
        def resultActions = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())


        then: "status will be 400"
        resultActions.andExpect(status().isBadRequest())

        and: "response will contain error message"
        resultActions.andExpect(jsonPath('$.errors.name', hasItem("size must be between 2 and 255")))

        and: "gym won't be saved in the database"
        gymRepository.count() == 0

        where:
        _gymName  | _reason
        "c"       | "gym's name is too short"
        "c" * 256 | "gym's name is too long"
    }

    def "should NOT create gym if gym's name is already taken"() {
        given: "gym with specified name already saved in the database"
        def gymName = "City Fit"
        gymRepository.saveAndFlush(new Gym(name: gymName))

        and: "create gym request with the same gym's name"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        def resultActions = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then: "status will be 400"
        resultActions.andExpect(status().isBadRequest())

        and: "response will contain error details"
        resultActions.andExpect(jsonPath('$.errorMsg').value("Gym with name [City Fit] already exists"))

        and: "gym won't be saved in the database"
        gymRepository.count() == 1
    }

    def createGymRequest(String gymName) {
        new CreateGymRequest(name: gymName)
    }

    void responseGymDataMatchRequest(CreateGymRequest request, GymResponse response) {
        assert request.name == response.name
    }

    void gymDataMatchRequest(CreateGymRequest request, Gym gym) {
        assert request.name == gym.name
    }
}
