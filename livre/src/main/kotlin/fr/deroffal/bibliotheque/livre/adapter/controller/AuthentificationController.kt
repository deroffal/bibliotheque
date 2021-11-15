package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.securite.auth.AuthentificationService
import fr.deroffal.bibliotheque.securite.auth.JwtRequest
import fr.deroffal.bibliotheque.securite.auth.JwtResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequiredArgsConstructor
class AuthentificationController(private val authentificationService: AuthentificationService) {

    @PostMapping("/authenticate")
    @ResponseStatus(OK)
    fun authenticate(@RequestBody authenticationRequest: JwtRequest): JwtResponse {
        return authentificationService.authenticate(authenticationRequest)
    }
}
