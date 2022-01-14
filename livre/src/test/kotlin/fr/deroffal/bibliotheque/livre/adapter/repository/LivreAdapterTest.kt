package fr.deroffal.bibliotheque.livre.adapter.repository

import fr.deroffal.bibliotheque.livre.domain.Livre
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import java.util.*

@TestPropertySource(locations = ["classpath:application-repositoryAdapterTest.properties"])
@AutoConfigureTestEntityManager
@SpringBootTest
@Transactional
internal class LivreAdapterTest {

    @Autowired
    private lateinit var livreAdapter: LivreAdapter

    @Autowired
    private lateinit var testEntityManager: TestEntityManager

    @Test
    fun `findById retourne le livre correspondant`() {
        //given:
        val entity = newLivreEntity("Harry Potter", "Fantastique")

        //when:
        val livreOptional = livreAdapter.findById(entity.id)

        //then:
        livreOptional.shouldBePresent()
        val livre = livreOptional.get()

        //and:
        livre.titre shouldBe "Harry Potter"
        livre.genre shouldBe "Fantastique"
    }

    @Test
    fun `findAllByGenre retourne tous les elements d'un genre donne`() {
        //given:
        val entity11 = newLivreEntity("Harry Potter", "Fantastique")
        val entity12 = newLivreEntity("Le Seigneur des Anneaux", "Fantastique")
        val entity21 = newLivreEntity("Dune", "Science-fiction")

        //when:
        val livres = livreAdapter.findAllByGenre("Fantastique")

        //then:
        livres shouldHaveSize 2
        livres.map { it.titre }.sorted() shouldContainExactly listOf("Harry Potter", "Le Seigneur des Anneaux")
    }

    @Test
    fun `create retourne le livre cree`() {
        //given:
        val livreACree = Livre(UUID.randomUUID(), "Harry Potter", "Fantastique")

        //when:
        val livre = livreAdapter.create(livreACree)

        //then:
        livre.titre shouldBe "Harry Potter"
        livre.genre shouldBe "Fantastique"

        //and:
        val entity = testEntityManager.find(LivreEntity::class.java, livre.id)
        entity.id shouldBe livre.id
        entity.titre shouldBe "Harry Potter"
        entity.genre shouldBe "Fantastique"
    }

    private fun newLivreEntity(titre: String, genre: String) = testEntityManager.merge(LivreEntity(UUID.randomUUID(), titre, genre))
}
