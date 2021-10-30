package fr.deroffal.bibliotheque.authentification.securite;

import static org.springframework.http.HttpStatus.OK;

import fr.deroffal.bibliotheque.authentification.securite.details.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/authenticate")
    @ResponseStatus(OK)
    public JwtResponse authenticate(@RequestBody final JwtRequest authenticationRequest) {
        final Authentication auth = authenticate(authenticationRequest.username(), authenticationRequest.password());

        final UserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        final String token = jwtTokenService.generateToken(userDetails);
        return new JwtResponse(token);
    }

    private Authentication authenticate(final String username, final String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final DisabledException e) {
            throw new AuthentificationException("USER_DISABLED", e);
        } catch (final BadCredentialsException e) {
            throw new AuthentificationException("INVALID_CREDENTIALS", e);
        }
    }
}
