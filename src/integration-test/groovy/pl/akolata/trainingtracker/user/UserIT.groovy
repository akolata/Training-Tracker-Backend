package pl.akolata.trainingtracker.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import pl.akolata.trainingtracker.auth.AuthHelper
import pl.akolata.trainingtracker.test.annotation.EmbeddedTruncatedAndInitialisedDatabase
import pl.akolata.trainingtracker.test.annotation.IntegrationTest
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@IntegrationTest
@AutoConfigureMockMvc
@EmbeddedTruncatedAndInitialisedDatabase
class UserIT extends Specification {

    static final USERS_EMAIL_URL = "/api/users/emails/{email}"
    static final USERS_USERNAMES_URL = "/api/users/usernames/{username}"

    @Autowired
    AuthHelper authHelper

    @Autowired
    MockMvc mvc

    def "should return HTTP 204 if email is taken"() {
        given: "username signed up"
        def user = authHelper.signUp(mvc)

        when: "signed up user's email will be verified"
        def resultActions = mvc.perform(head(USERS_EMAIL_URL, user.email))
                .andDo(print())

        then: "server should return 204, because this email has been found"
        resultActions.andExpect(status().isNoContent())
    }

    def "should return HTTP 404 NOT FOUND if email is available"() {
        when: "unique email will be verified"
        def resultActions = mvc.perform(head(USERS_EMAIL_URL, "unique@gmail.com"))
                .andDo(print())

        then: "server should return 404, because this email is available"
        resultActions.andExpect(status().isNotFound())
    }

    def "should return HTTP 204 if username is taken"() {
        given: "username signed up"
        def user = authHelper.signUp(mvc)

        when: "signed up user's username will be verified"
        def resultActions = mvc.perform(head(USERS_USERNAMES_URL, user.username))
                .andDo(print())

        then: "server should return 204, because this email has been found"
        resultActions.andExpect(status().isNoContent())
    }

    def "should return HTTP 404 NOT FOUND if username is available"() {
        when: "unique email will be verified"
        def resultActions = mvc.perform(head(USERS_USERNAMES_URL, "username"))
                .andDo(print())

        then: "server should return 404, because this email is available"
        resultActions.andExpect(status().isNotFound())
    }
}
