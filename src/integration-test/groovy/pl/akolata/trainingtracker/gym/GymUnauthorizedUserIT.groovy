package pl.akolata.trainingtracker.gym

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.akolata.trainingtracker.gym.controller.CreateGymRequest
import pl.akolata.trainingtracker.gym.repository.GymRepository
import pl.akolata.trainingtracker.test.annotation.EmbeddedTruncatedAndInitialisedDatabase
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedTruncatedAndInitialisedDatabase
class GymUnauthorizedUserIT extends Specification {

    private static final String GYMS_URL = "/api/gyms"

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper om

    @Autowired
    GymRepository gymRepository

    def "should not allow to create gym if user is not authenticated"() {
        given: "valid gym creation data"
        def gymName = "City Fit"
        def request = createGymRequest(gymName)

        when: "POST request will be executed"
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(GYMS_URL)
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))


        then: "status will be 401 created"
        result.andExpect(status().isUnauthorized())

        and: "no gym will be saved"
        gymRepository.count() == 0
    }

    def createGymRequest(String gymName) {
        new CreateGymRequest(name: gymName)
    }
}
