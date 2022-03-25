package fr.deroffal.bibliotheque.livre.adapter.controller.administration

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.deroffal.bibliotheque.livre.configuration.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import org.mapstruct.Mapper

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


