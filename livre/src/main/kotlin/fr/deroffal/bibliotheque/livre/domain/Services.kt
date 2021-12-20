package fr.deroffal.bibliotheque.livre.domain

import org.springframework.stereotype.Service

@Service
class LivreService(
    private val livreAdapter: LivreAdapter
) {

    fun findById(id: String) = livreAdapter.findById(id)
    fun findAllByGenre(genre: String) = livreAdapter.findAllByGenre(genre)

}

interface LivreAdapter {
    fun findById(id: String): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
}
