package fr.deroffal.bibliotheque.livre.adapter.controller.administration.export

import com.fasterxml.jackson.databind.ObjectMapper
import fr.deroffal.bibliotheque.livre.adapter.controller.administration.ExportLivreMapper
import fr.deroffal.bibliotheque.livre.adapter.controller.administration.LivreExporter
import fr.deroffal.bibliotheque.livre.domain.Livre
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component

@Component
class LivreJsonExporter(
    private val exportLivreMapper: ExportLivreMapper,
    private val objectMapper: ObjectMapper
) : LivreExporter {
    override fun export(livres: Collection<Livre>): String = livres.map { exportLivreMapper.toExport(it) }
        .run { objectMapper.writeValueAsString(this) }

    override fun getContentTypeValue() = APPLICATION_JSON_VALUE
}
