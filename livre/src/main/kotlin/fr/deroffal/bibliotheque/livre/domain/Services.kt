package fr.deroffal.bibliotheque.livre.domain

import org.springframework.stereotype.Service
import java.util.*

@Service
class LivreService(
    private val livrePort: LivrePort
) : LivreRetriever, LivreCreator {

    override fun findById(id: UUID): Livre = livrePort.findById(id).orElseThrow { LivreNotFoundException() }
    override fun findAllByGenre(genre: String) = livrePort.findAllByGenre(genre)
    override fun create(livre: Livre) = livrePort.create(livre).id

}

interface LivrePort {
    fun findById(id: UUID): Optional<Livre>
    fun findAllByGenre(genre: String): Collection<Livre>
    fun create(livre: Livre): Livre
}

interface LivreRetriever {
    fun findById(id: UUID): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
}

interface LivreCreator {
    fun create(livre: Livre): UUID
}
