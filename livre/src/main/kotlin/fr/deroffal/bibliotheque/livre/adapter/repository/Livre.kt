package fr.deroffal.bibliotheque.livre.adapter.repository

import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "livre")
data class LivreEntity(
    @Id
    val id: UUID,
    val titre: String,
    val genre: String
)

interface LivreRepository : PagingAndSortingRepository<LivreEntity, UUID> {
    fun findAllByGenre(genre: String): Collection<LivreEntity>
}
