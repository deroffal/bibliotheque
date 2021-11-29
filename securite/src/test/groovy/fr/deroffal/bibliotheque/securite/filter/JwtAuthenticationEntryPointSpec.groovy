package fr.deroffal.bibliotheque.securite.filter

import org.springframework.security.core.AuthenticationException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationEntryPointSpec extends Specification {

    def "JwtAuthenticationEntryPoint retourne un code 401 en cas d'erreur"(){
        given:
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint()
        and:
        HttpServletRequest request = Stub()
        AuthenticationException authException = Stub()
        HttpServletResponse response = Mock()

        when:
        entryPoint.commence(request, response, authException)

        then:
        1 * response.sendError(401, 'Unauthorized')

    }
}
