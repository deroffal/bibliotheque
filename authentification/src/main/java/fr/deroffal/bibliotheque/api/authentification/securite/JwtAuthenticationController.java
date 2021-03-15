package fr.deroffal.bibliotheque.api.authentification.securite;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import fr.deroffal.bibliotheque.api.authentification.securite.details.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @RequestMapping(value = "/authenticate", method = POST)
    public ResponseEntity<JwtResponse> authenticate(@RequestBody final JwtRequest authenticationRequest) throws Exception {
        final Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(final String username, final String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (final BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
