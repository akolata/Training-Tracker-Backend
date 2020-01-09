package pl.akolata.trainingtracker.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Component
import pl.akolata.trainingtracker.core.entity.User
import pl.akolata.trainingtracker.user.dto.UserDto
import pl.akolata.trainingtracker.user.service.RoleRepository
import pl.akolata.trainingtracker.user.service.UserRepository

@Component
class AuthTestHelper {

    static final String TEST_USERNAME = "johndoe"
    static final String TEST_PASSWORD = "Complex!1"

    @Autowired
    UserRepository userRepository

    @Autowired
    RoleRepository roleRepository

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    JwtTokenProvider jwtTokenProvider

    @Autowired
    AuthController authController

    User signUpAndReturnUser() {
        def request = new SignUpRequest("John", "Doe", TEST_USERNAME, "johndoe@gmail.com", TEST_PASSWORD, TEST_PASSWORD)
        def response = authController.signUp(request)
        def user = (UserDto) response.body
        userRepository.findByUsername(user.username).get()
    }

    String signInAndReturnJWToken() {
        def request = new SignInRequest(TEST_USERNAME, TEST_PASSWORD)
        def response = authController.signIn(request)
        def tokenResponse = (JwtAuthenticationResponse) response.body
        tokenResponse.accessToken
    }

    HttpHeaders buildHttpHeadersWithJWTToken(String jwt) {
        def headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${jwt}")
        headers
    }
}
