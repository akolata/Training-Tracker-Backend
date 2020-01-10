package pl.akolata.trainingtracker.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.akolata.trainingtracker.core.entity.RoleName
import pl.akolata.trainingtracker.core.entity.User
import pl.akolata.trainingtracker.test.annotation.EmbeddedTruncatedAndInitialisedDatabase
import pl.akolata.trainingtracker.user.dto.UserDto
import pl.akolata.trainingtracker.user.service.RoleRepository
import pl.akolata.trainingtracker.user.service.UserRepository
import spock.lang.Specification
import spock.lang.Title

import static org.hamcrest.Matchers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@EmbeddedTruncatedAndInitialisedDatabase
@AutoConfigureMockMvc
@Title("Authorization spec for sign up and sign in features")
class AuthorizationIT extends Specification {

    static final String SIGN_UP_URL = '/api/auth/sign-up'
    static final String SIGN_IN_URL = '/api/auth/sign-in'

    @Autowired
    UserRepository userRepository

    @Autowired
    RoleRepository roleRepository

    @Autowired
    ObjectMapper om

    @Autowired
    MockMvc mvc

    def setup() {
        userRepository.deleteAll()
    }

    def cleanup() {
        userRepository.deleteAll()
        roleRepository.deleteAll()
    }

    def "should sign up a new user"() {
        given: "valid sign up request"
        def request = validSignUpRequest()
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
        def responseJSON = mvcResult.response.contentAsString

        then: "response will contain registered user data"
        def response = om.readValue(responseJSON, UserDto)
        responseUserDataMatchesRequest(request, response)

        and: "user will be saved in the database"
        userRepository.count() == 1

        when: "database will be queried for user by username from the request"
        def userOpt = userRepository.findByUsername(request.username)

        then: "username will be found"
        userOpt.isPresent()
        def user = userOpt.get()

        and: "user's data saved in the database will match request"
        databaseUserDataMatchesRequest(request, user)

        and: "user's password should not be saved in a plain form"
        user.password != request.password

        and: "user should be active by default"
        userHasActiveAccount(user)

        and: "user should has ROLE_USER assigned"
        user.roles.find { it -> it.name == RoleName.ROLE_USER }

        and: "user should has only one role, and should not be an admin"
        user.roles.size() == 1
        !user.roles.find { it -> it.name == RoleName.ROLE_ADMIN }

        and: "response should contain location header, with registered user's id"
        mvcResult.response.containsHeader(HttpHeaders.LOCATION)
        mvcResult.response.getHeader(HttpHeaders.LOCATION).endsWith("/api/users/${user.id}")
    }

    def "should NOT sign up a user if username is already taken"() {
        given: "valid sign up request"
        def request = validSignUpRequest()
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
        def responseJSON = mvcResult.response.contentAsString

        then: "response will contain registered user data"
        def response = om.readValue(responseJSON, UserDto)
        responseUserDataMatchesRequest(request, response)

        and: "user will be saved in the database"
        userRepository.count() == 1

        when: "second request will be executed, with duplicated username"
        request = duplicatedUsernameSignUpRequest()
        requestJSON = om.writeValueAsString(request)

        mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())

        then: "status will be bad request"
        mvcResult.andExpect(status().isBadRequest())

        and: "response contains error message"
        mvcResult.andExpect(jsonPath('$.errorMsg', is("Username [username] is already taken")))

        and: "the number of users in the database is still the same"
        userRepository.count() == 1
    }

    def "should NOT sign up a user if email is already taken"() {
        given: "valid sign up request"
        def request = validSignUpRequest()
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
        def responseJSON = mvcResult.response.contentAsString

        then: "response will contain registered user data"
        def response = om.readValue(responseJSON, UserDto)
        responseUserDataMatchesRequest(request, response)

        and: "user will be saved in the database"
        userRepository.count() == 1

        when: "second request will be executed, with duplicated username"
        request = duplicatedEmailSignUpRequest()
        requestJSON = om.writeValueAsString(request)

        mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())

        then: "status will be bad request"
        mvcResult.andExpect(status().isBadRequest())

        and: "response contains error message"
        mvcResult.andExpect(jsonPath('$.errorMsg', is("Email [johndoe@gmail.com] is already taken")))

        and: "the number of users in the database is still the same"
        userRepository.count() == 1
    }

    def "should NOT sign up a new user, if request properties won't pass basic validation"() {
        def request = new SignUpRequest("f", "l", "u", "e.com", "p", "p")
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())

        then: "status will be bad request"
        mvcResult.andExpect(status().isBadRequest())

        and: "response contains error message"
        mvcResult.andExpect(jsonPath('$.errors.password', hasSize(2)))
        mvcResult.andExpect(jsonPath('$.errors.password', hasItem("Password length must be between 8 and 60")))
        mvcResult.andExpect(jsonPath('$.errors.password', hasItem("Password must have at least 1 capital letter, 1 digit, 1 special character")))
        mvcResult.andExpect(jsonPath('$.errors.firstname', hasItem("First name length must be between 2 and 60")))
        mvcResult.andExpect(jsonPath('$.errors.email', hasItem("Email is not valid")))
        mvcResult.andExpect(jsonPath('$.errors.username', hasItem("Username length must be between 2 and 60")))
        mvcResult.andExpect(jsonPath('$.errors.lastname', hasItem("Last name length must be between 2 and 60")))

        and: "user repository will be empty"
        userRepository.count() == 0
    }

    def "should sign in a user, if username and password matches"() {
        given: "valid sign up request"
        def request = validSignUpRequest()
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
        def responseJSON = mvcResult.response.contentAsString

        then: "response will contain registered user data"
        def response = om.readValue(responseJSON, UserDto)
        responseUserDataMatchesRequest(request, response)

        and: "user will be saved in the database"
        userRepository.count() == 1

        when: "the same user will try to sign in"
        request = validSignInRequest()
        requestJSON = om.writeValueAsString(request)

        mvcResult = mvc.perform(post(SIGN_IN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())

        then: "status will be OK"
        mvcResult.andExpect(status().isOk())

        and: "response will contain token type and the actual token"
        mvcResult.andExpect(jsonPath('$.tokenType', is("Bearer")))
        mvcResult.andExpect(jsonPath('$.accessToken').exists())
    }

    def "should NOT sign in a user, if username or password doesn't match"() {
        given: "valid sign up request"
        def request = validSignUpRequest()
        def requestJSON = om.writeValueAsString(request)

        when: "it will be executed"
        def mvcResult = mvc.perform(post(SIGN_UP_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
        def responseJSON = mvcResult.response.contentAsString

        then: "response will contain registered user data"
        def response = om.readValue(responseJSON, UserDto)
        responseUserDataMatchesRequest(request, response)

        and: "user will be saved in the database"
        userRepository.count() == 1

        when: "user will try to sign in, but username or password will not match"
        requestJSON = om.writeValueAsString(signInRequest)

        mvcResult = mvc.perform(post(SIGN_IN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andDo(print())

        then: "status will be OK"
        mvcResult.andExpect(status().isUnauthorized())

        where:
        signInRequest << [invalidUsernameSignInRequest(), invalidPasswordSignInRequest()]
    }

    def validSignUpRequest() {
        new SignUpRequest("John", "Doe", "username", "johndoe@gmail.com", "Complex!1", "Complex!1")
    }

    def duplicatedUsernameSignUpRequest() {
        def request = validSignUpRequest()
        new SignUpRequest(request.firstName, request.lastName, request.username, "other@gmail.com",
                request.password, request.repeatedPassword)
    }

    def duplicatedEmailSignUpRequest() {
        def request = validSignUpRequest()
        new SignUpRequest(request.firstName, request.lastName, "other_username", request.email,
                request.password, request.repeatedPassword)
    }

    def validSignInRequest() {
        new SignInRequest("username", "Complex!1")
    }

    def invalidUsernameSignInRequest(SignUpRequest request) {
        new SignInRequest("username" + "!", "Complex!1")
    }

    def invalidPasswordSignInRequest() {
        new SignInRequest("username", "Complex!1" + "!")
    }

    void responseUserDataMatchesRequest(SignUpRequest request, UserDto user) {
        assert request.username == user.username
        assert request.firstName == user.firstName
        assert request.lastName == user.lastName
        assert request.email == user.email
    }

    void databaseUserDataMatchesRequest(SignUpRequest request, User user) {
        assert request.username == user.username
        assert request.firstName == user.firstName
        assert request.lastName == user.lastName
        assert request.email == user.email
    }

    void userHasActiveAccount(User user) {
        assert user.userAccountDetails.enabled
        assert !user.userAccountDetails.accountExpired
        assert !user.userAccountDetails.accountLocked
        assert !user.userAccountDetails.credentialsExpired
    }
}
