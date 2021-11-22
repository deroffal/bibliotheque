package fr.deroffal.bibliotheque.securite.auth

import fr.deroffal.bibliotheque.securite.details.JwtUserDetails
import fr.deroffal.bibliotheque.securite.filter.JwtTokenService
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import spock.lang.Specification

class AuthentificationServiceSpec extends Specification {

    AuthentificationService authentificationService


    AuthenticationManager authenticationManager = Stub(AuthenticationManager)
    JwtTokenService jwtTokenService = Stub(JwtTokenService, constructorArgs: [null, null])

    def setup() {
        authentificationService = new AuthentificationService(authenticationManager, jwtTokenService)
    }

    def "authenticate retourne un token"() {
        given:
        String username = 'username'
        String password = 'password'
        String tokenAttendu = 'token !'

        and:
        def auth = Stub(Authentication)
        def userDetails = new JwtUserDetails(username, password)
        auth.getPrincipal() >> userDetails

        authenticationManager.authenticate(*_) >> auth
        jwtTokenService.generateToken(userDetails) >> tokenAttendu
        when:
        String authentication = authentificationService.authenticate(username, password)

        then:
        authentication == tokenAttendu
    }

    def "authenticate lance une exception en cas d'erreur d'authentification"() {
        given:
        String username = 'username'
        String password = 'password'

        and:
        AccountExpiredException exceptionCause = new AccountExpiredException("Account Expired !")
        authenticationManager.authenticate(*_) >> { throw exceptionCause }

        when:
        authentificationService.authenticate(username, password)

        then:
        AuthentificationException e = thrown(AuthentificationException)
        e.cause == exceptionCause
    }
}
