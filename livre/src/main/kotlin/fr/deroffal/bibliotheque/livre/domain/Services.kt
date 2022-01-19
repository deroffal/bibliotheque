package fr.deroffal.bibliotheque.livre.domain

import org.springframework.stereotype.Service
import java.util.*

@Service
class LivreService(
    private val livrePort: LivrePort
) : LivreRetriever, LivreAdministrationService {

    override fun findById(id: UUID): Livre = livrePort.findById(id).orElseThrow { LivreNotFoundException(id) }
    override fun findAllByGenre(genre: String) = livrePort.findAllByGenre(genre)
    override fun create(livre: Livre) = livrePort.create(livre).id!!
    override fun findAll() = livrePort.findAll()

}

interface LivrePort {
    fun findById(id: UUID): Optional<Livre>
    fun findAllByGenre(genre: String): Collection<Livre>
    fun create(livre: Livre): Livre
    fun findAll(): Collection<Livre>
}

interface LivreRetriever {
    fun findById(id: UUID): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
}

interface LivreAdministrationService {
    fun create(livre: Livre): UUID
    fun findAll(): Collection<Livre>
}
