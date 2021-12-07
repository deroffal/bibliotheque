package fr.deroffal.bibliotheque.securite.filter

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.core.AuthenticationException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationEntryPointSpec extends Specification {

    def "commence retourne un code 401 en cas d'erreur"() {
        given:
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint()
        and:
        HttpServletRequest request = Stub(HttpServletRequest)
        AuthenticationException authException = new AccountExpiredException("account expired !")
        HttpServletResponse response = Mock(HttpServletResponse)

        when:
        entryPoint.commence(request, response, authException)

        then:
        1 * response.sendError(401, 'Unauthorized : account expired !')
    }
}
