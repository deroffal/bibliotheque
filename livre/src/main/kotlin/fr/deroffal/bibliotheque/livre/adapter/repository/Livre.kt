package fr.deroffal.bibliotheque.livre.adapter.repository

import org.springframework.data.repository.PagingAndSortingRepository
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LivreEntity(
    @Id
    val id: String,
    val titre: String,
    val genre: String
)

interface LivreRepository : PagingAndSortingRepository<LivreEntity, String> {
    fun findAllByGenre(genre: String): Collection<LivreEntity>
}
