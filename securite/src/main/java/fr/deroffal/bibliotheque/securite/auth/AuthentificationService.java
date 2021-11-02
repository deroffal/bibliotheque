package fr.deroffal.bibliotheque.securite.auth;

import fr.deroffal.bibliotheque.securite.JwtTokenService;
import fr.deroffal.bibliotheque.securite.details.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthentificationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public JwtResponse authenticate(final JwtRequest request) {
        final Authentication auth = getAuthentication(request.username(), request.password());

        final UserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        final String token = jwtTokenService.generateToken(userDetails);
        return new JwtResponse(token);
    }

    private Authentication getAuthentication(final String username, final String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final DisabledException | BadCredentialsException e) {
            throw new AuthentificationException(e);
        }
    }
}
