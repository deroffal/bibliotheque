package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreCreator
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class LivreAdministrationController(
    private val livreCreator: LivreCreator
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun creer(livre: Livre) = livreCreator.create(livre)
}
