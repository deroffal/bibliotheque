package fr.deroffal.bibliotheque.livre.adapter.repository

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreAdapter
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Component
class LivreAdapterImpl(
    private val livreRepository: LivreRepository,
    private val livreMapper: LivreMapper
) : LivreAdapter {

    override fun findById(id: String): Livre = livreRepository.findById(id).map { livreMapper.toLivre(it) }.orElseThrow()

    override fun findAllByGenre(genre: String): Collection<Livre> = livreRepository.findAllByGenre(genre).map { livreMapper.toLivre(it) }
}

@Mapper(config = MapperConfiguration::class)
interface LivreMapper {

    fun toLivre(entity: LivreEntity): Livre
}
