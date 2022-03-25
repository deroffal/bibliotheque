package fr.deroffal.bibliotheque.livre.domain

import org.springframework.stereotype.Service
import java.util.*

@Service
class LivreService(
    private val livrePort: LivrePort
) : LivreRetriever, LivreAdministrationService {

    override fun findById(id: UUID): Livre = livrePort.findById(id).orElseThrow { LivreNotFoundException(id) }
    override fun findAllByGenre(genre: String) = livrePort.findAllByGenre(genre)
    override fun findAllPaginated(page: Int, size: Int, sortBy: String, direction: String) = livrePort.findAll(page, size, sortBy, direction)

    override fun create(livre: Livre) = livrePort.create(livre).id!!
    override fun findAll() = livrePort.findAll()

}

interface LivrePort {
    fun findById(id: UUID): Optional<Livre>
    fun findAllByGenre(genre: String): Collection<Livre>
    fun create(livre: Livre): Livre
    fun findAll(): Collection<Livre>
    fun findAll(page: Int, size: Int, sortBy: String, direction: String): Page<Livre>
}

interface LivreRetriever {
    fun findById(id: UUID): Livre
    fun findAllByGenre(genre: String): Collection<Livre>
    fun findAllPaginated(page: Int, size: Int, sortBy: String, direction: String): Page<Livre>
}

interface LivreAdministrationService {
    fun create(livre: Livre): UUID
    fun findAll(): Collection<Livre>
}
