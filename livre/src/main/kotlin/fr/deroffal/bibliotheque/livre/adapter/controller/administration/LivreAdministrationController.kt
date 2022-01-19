package fr.deroffal.bibliotheque.livre.adapter.controller.administration

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreAdministrationService
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin/livre")
class LivreAdministrationController(
    private val livreControllerMapper: LivreControllerMapper,
    private val livreAdministrationService: LivreAdministrationService,
    private val livreExporters: Collection<LivreExporter>
) {

    @PostMapping("/")
    @ResponseStatus(CREATED)
    fun creer(@RequestBody livre: CreationLivreCommand) = livreAdministrationService.create(livre.toLivre())

    @GetMapping(path = ["/export"], produces = ["text/csv", "application/json"])
    fun export(@RequestHeader("accept") accept: String, response: HttpServletResponse): String {
        response.setHeader("Content-Disposition", "attachment; filename=${LocalDateTime.now().format(ISO_DATE_TIME)}")
        return livreExporters.firstOrNull { it.getContentTypeValue() == accept }
            ?.export(livreAdministrationService.findAll())
            ?: throw UnsupportedMediaTypeException(accept)
    }

    private fun CreationLivreCommand.toLivre() = livreControllerMapper.toLivre(this)
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

data class CreationLivreCommand(val titre: String, val genre: String)

@Mapper(config = MapperConfiguration::class)
interface LivreControllerMapper {

    @Mapping(target = "id", ignore = true)
    fun toLivre(command: CreationLivreCommand): Livre
}
