package pl.akolata.trainingtracker.auth


import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class SignUpRequestTest extends Specification {

    static final String VALID_FIRST_NAME = "John"
    static final String VALID_LAST_NAME = "Doe"
    static final String VALID_USERNAME = "johndoe"
    static final String VALID_PASSWORD = "Complex!1"
    static final String VALID_EMAIL = "johndoe@gmail.com"

    ValidatorFactory validatorFactory
    Validator validator

    def setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.validator
    }

    @Unroll
    def "should be invalid because firstName [#_firstName] is not valid"() {
        given:
        SignUpRequest request = new SignUpRequest(
                _firstName,
                VALID_LAST_NAME,
                VALID_USERNAME,
                VALID_EMAIL,
                VALID_PASSWORD,
                VALID_PASSWORD
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _firstName << [
                null,
                "",
                " ",
                "   ",
                "a",
                "A",
                "a" * 61
        ]
    }

    @Unroll
    def "should be invalid because lastName [#_lastName] is not valid"() {
        given:
        SignUpRequest request = new SignUpRequest(
                VALID_FIRST_NAME,
                _lastName,
                VALID_USERNAME,
                VALID_EMAIL,
                VALID_PASSWORD,
                VALID_PASSWORD
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _lastName << [
                null,
                "",
                " ",
                "   ",
                "a",
                "A",
                "a" * 61
        ]
    }

    @Unroll
    def "should be invalid because username [#_username] is not valid"() {
        given:
        SignUpRequest request = new SignUpRequest(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                _username,
                VALID_EMAIL,
                VALID_PASSWORD,
                VALID_PASSWORD
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _username << [
                null,
                "",
                " ",
                "   ",
                "a",
                "A",
                "a" * 61
        ]
    }

    @Unroll
    def "should be invalid because email [#_email] is not valid"() {
        given:
        SignUpRequest request = new SignUpRequest(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_USERNAME,
                _email,
                VALID_PASSWORD,
                VALID_PASSWORD
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _email << [
                null,
                "",
                " ",
                "   ",
                "a",
                "a@",
                "@com",
                "@.com" * 61
        ]
    }

    @Unroll
    def "should be invalid because password [#_password] is not valid"() {
        given:
        SignUpRequest request = new SignUpRequest(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_USERNAME,
                VALID_EMAIL,
                _password,
                _password
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _password << [
                null,
                "",
                " ",
                "   ",
                "a",
                "A",
                "Copex!1", // too short
                "compex!1", // no uppercase
                "COMPEX!1", // no lowercase
                "COMPEX11", // no special character
                "COMPEX!!", // no digit
                "Complex!1Complex!1Complex!1Complex!1Complex!1Complex!11111111" // too long
        ]
    }

    @Unroll
    def "should be invalid because passwords [#_password][#_repeatedPassword] do not match"() {
        given:
        SignUpRequest request = new SignUpRequest(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_USERNAME,
                VALID_EMAIL,
                _password,
                _repeatedPassword
        )

        when:
        Set<ConstraintViolation> violations = validator.validate(request)

        then:
        !violations.isEmpty()

        where:
        _password            | _repeatedPassword
        VALID_PASSWORD       | VALID_PASSWORD + "1"
        VALID_PASSWORD + "1" | VALID_PASSWORD
    }
}
