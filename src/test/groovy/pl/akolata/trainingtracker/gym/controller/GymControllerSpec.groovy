package pl.akolata.trainingtracker.gym.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.akolata.trainingtracker.gym.repository.GymRepository
import spock.lang.Specification
import spock.lang.Unroll

import static pl.akolata.utils.SpecificationHelper.asJsonString

@SpringBootTest
@AutoConfigureMockMvc
class GymControllerSpec extends Specification {

    private static final String GYMS_URL = "/api/gyms"

    @Autowired
    MockMvc mvc

    @Autowired
    GymRepository gymRepository

    def setup() {
        gymRepository.deleteAll()
    }

    def cleanup() {
        gymRepository.deleteAll()
    }

    @WithMockUser
    def "should create gym"() {
        given: "valid gym creation data"
        def gymName = "City Fit"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))

        then: "status will be 201 created"
        result.andExpect(MockMvcResultMatchers.status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym.id').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym.name').value(gymName))
    }

    @Unroll
    @WithMockUser
    def "should return 400 and not create gym because [#_reason]"() {
        given: "valid gym creation data"
        def request = createGymRequest(_gymName)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))


        then: "status will be 201 created"
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())

        where:
        _gymName  | _reason
        "c"       | "gym's name is too short"
        "c" * 256 | "gym's name is too long"
    }

    @WithMockUser
    def "should return 400 and not create gym if gym's name is already taken"() {
        given: "valid gym creation data"
        def gymName = "City Fit"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))


        then: "status will be 201 created"
        result.andExpect(MockMvcResultMatchers.status().isCreated())

        and: "response will contain data about the created gym"
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym.id').exists())
        result.andExpect(MockMvcResultMatchers.jsonPath('$.response.gym.name').value(gymName))

        when: "the same request will be executed again"
        result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))

        then: "status will be 400 bad request"
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())

    }

    def "should not allow to create gym if user is not authenticated"() {
        given: "valid gym creation data"
        def gymName = "City Fit"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))


        then: "status will be 401 created"
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized())
    }

    private static createGymRequest(String gymName) {
        return new CreateGymRequest(name: gymName)
    }
}
