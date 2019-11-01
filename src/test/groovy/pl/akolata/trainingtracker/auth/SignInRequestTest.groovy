package pl.akolata.trainingtracker.auth

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class SignInRequestTest extends Specification {

    static final VALID_USERNAME = "johndoe"
    static final VALID_PASSWORD = "complex!1"

    ValidatorFactory validatorFactory
    Validator validator

    def setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.validator
    }

    @Unroll
    def "username [#_username] should be invalid"() {
        given:
        SignInRequest request = new SignInRequest(username: _username, password: VALID_PASSWORD)

        when:
        Set<ConstraintViolation> violations = validator.validate(request)
        then:
        !violations.isEmpty()

        where:
        _username << [
                null,
                "",
                " ",
                "   "
        ]
    }

    @Unroll
    def "password [#_password] should be invalid"() {
        given:
        SignInRequest request = new SignInRequest(username: VALID_USERNAME, password: _password)

        when:
        Set<ConstraintViolation> violations = validator.validate(request)
        then:
        !violations.isEmpty()

        where:
        _password << [
                null,
                "",
                " ",
                "   "
        ]
    }

    def tearDown() {
        validatorFactory.close()
    }
}
