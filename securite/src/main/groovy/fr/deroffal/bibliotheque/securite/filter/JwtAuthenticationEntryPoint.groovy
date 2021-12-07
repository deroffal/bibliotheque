package fr.deroffal.bibliotheque.securite.filter

import groovy.util.logging.Slf4j
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

@Slf4j
@Component
class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
        log.debug("Erreur lors de l'authentification", authException)
        response.sendError(SC_UNAUTHORIZED, "Unauthorized : $authException.message")
    }
}
