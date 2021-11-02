package fr.deroffal.bibliotheque.livre.adapter.controller;

import static org.springframework.http.HttpStatus.OK;

import fr.deroffal.bibliotheque.securite.auth.AuthentificationService;
import fr.deroffal.bibliotheque.securite.auth.JwtRequest;
import fr.deroffal.bibliotheque.securite.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthentificationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/authenticate")
    @ResponseStatus(OK)
    public JwtResponse authenticate(@RequestBody final JwtRequest authenticationRequest) {
        return authentificationService.authenticate(authenticationRequest);
    }
}
