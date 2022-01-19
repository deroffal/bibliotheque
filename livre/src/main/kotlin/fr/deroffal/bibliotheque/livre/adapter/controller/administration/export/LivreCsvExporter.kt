package fr.deroffal.bibliotheque.livre.adapter.controller.administration.export

import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import fr.deroffal.bibliotheque.livre.adapter.controller.administration.ExportLivre
import fr.deroffal.bibliotheque.livre.adapter.controller.administration.ExportLivreMapper
import fr.deroffal.bibliotheque.livre.adapter.controller.administration.LivreExporter
import fr.deroffal.bibliotheque.livre.domain.Livre
import org.springframework.stereotype.Component
import java.io.StringWriter

@Component
class LivreCsvExporter(
    private val exportLivreMapper: ExportLivreMapper
) : LivreExporter {

    private val livreCSVWriter = intialiserLivreCsvWriter()

    override fun export(livres: Collection<Livre>) = StringWriter().use { return@use ecrireCsv(it, livres.map(exportLivreMapper::toExport)) }

    private fun ecrireCsv(stringWriter: StringWriter, livres: Collection<ExportLivre>) =
        livreCSVWriter.writeValues(stringWriter).use { sequenceWriter ->
            livres.forEach { sequenceWriter.write(it) }
            return@use stringWriter.toString()

        }

    override fun getContentTypeValue() = "text/csv"

    private fun intialiserLivreCsvWriter(): ObjectWriter {
        val csvMapper = CsvMapper().apply { registerModule(JavaTimeModule()) }
        return csvMapper.schemaFor(ExportLivre::class.java)
            .withoutQuoteChar()//
            .withColumnSeparator(';')//
            .withArrayElementSeparator(", ")//
            .withHeader()
            .run { csvMapper.writer(this) }
    }

}

