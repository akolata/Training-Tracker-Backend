package pl.akolata.trainingtracker.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import pl.akolata.trainingtracker.auth.SignUpRequest
import pl.akolata.trainingtracker.user.dto.UserDto

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Component
class AuthHelper {
    static final String SIGN_UP_URL = '/api/auth/sign-up'

    @Autowired
    ObjectMapper om

    def static validSignUpRequest() {
        new SignUpRequest("John", "Doe", "username", "johndoe@gmail.com", "Complex!1", "Complex!1")
    }

    UserDto signUp(MockMvc mvc) {
        def request = validSignUpRequest()
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
        om.readValue(mvcResult.response.contentAsString, UserDto)
    }
}
