package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.LivreNotFoundException
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
import org.springframework.http.HttpStatus.NOT_FOUND
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

    @GetMapping("/genre/")
    fun getByGenre(genre: String) = livreRetriever.findAllByGenre(genre)

    @GetMapping("/")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "asc") direction: String,
    ) = livreRetriever.findAllPaginated(page, size, sortBy, direction)

}

@RestControllerAdvice
class LivreControllerAdvice {

    @ExceptionHandler(value = [LivreNotFoundException::class])
    @ResponseStatus(value = NOT_FOUND)
    fun onLivreNonTrouveException(e: LivreNotFoundException, webRequest: WebRequest): String {
        return "livre ${e.id} non trouvé!"
    }
}
