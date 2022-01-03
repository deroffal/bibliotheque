package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LivreController(
    private val livreRetriever: LivreRetriever
) {

    @GetMapping
    fun get(uuid: String) = livreRetriever.findById(uuid)

    @GetMapping("/genre")
    fun getByGenre(genre: String) = livreRetriever.findAllByGenre(genre)
}
