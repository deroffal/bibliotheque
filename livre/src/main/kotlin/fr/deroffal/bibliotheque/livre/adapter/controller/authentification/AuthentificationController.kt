package fr.deroffal.bibliotheque.livre.adapter.controller.authentification

import fr.deroffal.bibliotheque.securite.auth.AuthentificationService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequiredArgsConstructor
class AuthentificationController(private val authentificationService: AuthentificationService) {

    @PostMapping("/authenticate")
    @ResponseStatus(OK)
    fun authenticate(@RequestBody request: JwtRequest): JwtResponse {
        val token = authentificationService.authenticate(request.username, request.password)
        return JwtResponse(token)
    }
}

data class JwtRequest(val username: String, val password: String)
data class JwtResponse(val jwtToken: String)
