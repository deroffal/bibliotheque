package fr.deroffal.bibliotheque.livre.domain

import org.springframework.stereotype.Service

@Service
class LivreService(
    private val livrePort: LivrePort
) : LivreRetriever {

    override fun findById(id: String) = livrePort.findById(id)
    override fun findAllByGenre(genre: String) = livrePort.findAllByGenre(genre)

}

interface LivrePort {
    fun findById(id: String): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
}

interface LivreRetriever {
    fun findById(id: String): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
}
