package fr.deroffal.bibliotheque.livre.adapter.repository

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivrePort
import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class LivreAdapter(
    private val livreRepository: LivreRepository,
    private val livreMapper: LivreMapper
) : LivrePort {

    override fun findById(id: UUID): Optional<Livre> = livreRepository.findById(id).map { livreMapper.toLivre(it) }
    override fun findAllByGenre(genre: String): Collection<Livre> = livreRepository.findAllByGenre(genre).map { livreMapper.toLivre(it) }
    override fun create(livre: Livre) = livreRepository.save(livre.toEntity()).toLivre()

    private fun Livre.toEntity() = livreMapper.toEntity(this)
    private fun LivreEntity.toLivre() = livreMapper.toLivre(this)

}

@Mapper(config = MapperConfiguration::class)
abstract class LivreMapper {

    abstract fun toLivre(entity: LivreEntity): Livre

    fun toEntity(livre: Livre): LivreEntity = LivreEntity(titre = livre.titre, genre = livre.genre)

}
