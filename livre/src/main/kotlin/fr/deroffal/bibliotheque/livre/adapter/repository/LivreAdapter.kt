package fr.deroffal.bibliotheque.livre.adapter.repository

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivrePort
import fr.deroffal.bibliotheque.livre.domain.Page
import org.mapstruct.Mapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
    override fun findAll() = livreRepository.findAll().map { it.toLivre() }
    override fun findAll(page: Int, size: Int, sortBy: String, direction: String): Page<Livre> {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy))
        val result = livreRepository.findAll(pageRequest)
        return Page(result.content.map { it.toLivre() }, result.totalPages, result.totalElements)
    }

    private fun Livre.toEntity() = livreMapper.toEntity(this)
    private fun LivreEntity.toLivre() = livreMapper.toLivre(this)

}

@Mapper(config = MapperConfiguration::class)
abstract class LivreMapper {

    abstract fun toLivre(entity: LivreEntity): Livre

    fun toEntity(livre: Livre): LivreEntity = LivreEntity(titre = livre.titre, genre = livre.genre)

}
