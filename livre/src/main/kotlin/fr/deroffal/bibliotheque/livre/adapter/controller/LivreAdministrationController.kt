package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreAdministrationService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin")
class LivreAdministrationController(
    private val livreAdministrationService: LivreAdministrationService,
    private val livreExporters: Collection<LivreExporter>
) {

    @PostMapping("/livre")
    @ResponseStatus(CREATED)
    fun creer(livre: Livre) = livreAdministrationService.create(livre)

    @GetMapping(path = ["/export"], produces = ["text/csv"])
    fun export(@RequestHeader("accept") accept: String, response: HttpServletResponse): String {
        response.setHeader("Content-Disposition", "attachment; filename=${LocalDateTime.now().format(ISO_DATE_TIME)}")
        return livreExporters.firstOrNull { it.getContentTypeValue() == accept }
            ?.export(livreAdministrationService.findAll())
            ?: throw UnsupportedMediaTypeException(accept)
    }
}

class UnsupportedMediaTypeException(val mediaType: String) : RuntimeException()

@RestControllerAdvice
class LivreAdministrationControllerAdvice {

    @ExceptionHandler(value = [UnsupportedMediaTypeException::class])
    @ResponseStatus(value = UNSUPPORTED_MEDIA_TYPE)
    fun onLivreNonTrouveException(e: UnsupportedMediaTypeException, webRequest: WebRequest): String {
        return "Unsupported Media Type : ${e.mediaType}"
    }
}

