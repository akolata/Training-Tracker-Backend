package pl.akolata.trainingtracker.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import spock.lang.Specification

class JwtTokenProviderTest extends Specification {

    static final String secret = "TEST_SECRET"

    def "should generate JWT token"() {
        given: "expiration date"
        long hourInMs = 3_600_000L
        long yearInMs = hourInMs * 24 * 365
        Date expirationDate = new Date(new Date().getTime() + yearInMs)

        when: "JWT token will be created"
        String jwt = Jwts
                .builder()
                .setSubject("1")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()

        then: "it will not be null"
        jwt
    }
}
