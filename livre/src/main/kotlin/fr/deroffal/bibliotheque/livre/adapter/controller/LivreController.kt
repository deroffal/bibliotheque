package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.Livre
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LivreController {

    @GetMapping
    fun get(uuid: String): Livre {
        return Livre(uuid)
    }
}
