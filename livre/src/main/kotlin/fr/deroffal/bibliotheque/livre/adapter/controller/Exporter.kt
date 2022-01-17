package fr.deroffal.bibliotheque.livre.adapter.controller

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import org.mapstruct.Mapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.io.StringWriter

@Configuration
class LivreExporterConfiguration {

    @Bean
    @Primary
    fun objetMapper(): ObjectMapper = jacksonObjectMapper().apply { registerModule(JavaTimeModule()) }

    @Bean
    fun csvMapper(): CsvMapper = CsvMapper().apply { registerModule(JavaTimeModule()) }
}

interface LivreExporter {

    fun export(livres: Collection<Livre>): String
    fun getContentTypeValue(): String
}

@JsonPropertyOrder(value = ["titre", "genre"])
data class ExportLivre(val titre: String, val genre: String)

@Mapper(config = MapperConfiguration::class)
interface ExportLivreMapper {
    fun toExport(livre: Livre): ExportLivre
}

@Component
class LivreCsvExporter(
    private val exportLivreMapper: ExportLivreMapper,
    private val csvMapper: CsvMapper
) : LivreExporter {

    private val livreCSVWriter: ObjectWriter =
        csvMapper.schemaFor(ExportLivre::class.java)
            .withoutQuoteChar()//
            .withColumnSeparator(';')//
            .withArrayElementSeparator(", ")//
            .withHeader()
            .run { csvMapper.writer(this) }

    override fun export(livres: Collection<Livre>) = StringWriter().use { return@use ecrireCsv(it, livres.map(exportLivreMapper::toExport)) }

    private fun ecrireCsv(stringWriter: StringWriter, livres: Collection<ExportLivre>): String {
        return livreCSVWriter.writeValues(stringWriter).use { sequenceWriter ->
            livres.forEach { sequenceWriter.write(it) }
            return@use stringWriter.toString()
        }
    }

    override fun getContentTypeValue() = "text/csv"

}
