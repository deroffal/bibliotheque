package fr.deroffal.bibliotheque.securite.auth


import fr.deroffal.bibliotheque.securite.filter.JwtTokenService
import groovy.util.logging.Slf4j
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Slf4j
@Service
class AuthentificationService {

    private final AuthenticationManager authenticationManager
    private final JwtTokenService jwtTokenService

    AuthentificationService(final AuthenticationManager authenticationManager, final JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager
        this.jwtTokenService = jwtTokenService
    }

    String authenticate(final String username, final String password) {
        final Authentication auth = getAuthentication(username, password)

        final UserDetails userDetails = (UserDetails) auth.principal
        return jwtTokenService.generateToken(userDetails)
    }

    private Authentication getAuthentication(final String username, final String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
        } catch (final AuthenticationException e) {
            log.debug("Erreur lors de l'authentification", e)
            throw new AuthentificationException(e)
        }
    }
}
