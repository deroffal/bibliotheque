package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.LivreNotFoundException
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestController
@RequestMapping("/livre")
class LivreController(
    private val livreRetriever: LivreRetriever
) {

    @GetMapping("/{uuid}")
    fun getById(@PathVariable uuid: UUID) = livreRetriever.findById(uuid)

    @GetMapping("/genre")
    fun getByGenre(genre: String) = livreRetriever.findAllByGenre(genre)

}

@RestControllerAdvice
class LivreControllerAdvice {

    @ExceptionHandler(value = [LivreNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun onLivreNonTrouveException(exception: LivreNotFoundException, webRequest: WebRequest): String {
        return "livre non trouvé!"
    }
}
