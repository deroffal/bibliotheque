package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreNotFoundException
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
import fr.deroffal.bibliotheque.livre.domain.Page
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest
@WithMockUser
@ContextConfiguration(classes = [ControllerTestContextConfiguration::class])
internal class LivreControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var livreRetriever: LivreRetriever

    @Test
    fun `getById retourne l'objet s'il existe`() {
        //given:
        val uuid = UUID.randomUUID()

        //and:
        every { livreRetriever.findById(uuid) } returns Livre(uuid, "Harry Potter", "Fantastique")

        //when:
        val response = mockMvc.perform(get("/livre/$uuid"))

        //then:
        response
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(uuid.toString()))
            .andExpect(jsonPath("$.titre").value("Harry Potter"))
            .andExpect(jsonPath("$.genre").value("Fantastique"))
    }

    @Test
    fun `getById retourne une erreur 404 si l'objet n'existe pas`() {
        //given:
        val uuid = UUID.randomUUID()

        //and:
        every { livreRetriever.findById(uuid) } throws LivreNotFoundException(uuid)

        //when:
        val response = mockMvc.perform(get("/livre/$uuid"))

        //then:
        response
            .andExpect(status().isNotFound)
            .andExpect(content().contentTypeCompatibleWith(TEXT_PLAIN))
    }

    @Test
    fun `getByGenre retourne les objets par genre`() {
        //given:
        val genre = "fantastique"

        //and:
        every { livreRetriever.findAllByGenre(genre) } returns listOf(
            Livre(null, "Harry Potter à l'école des sorciers", "Fantastique"),
            Livre(null, "Harry Potter et la Chambre des secrets", "Fantastique"),
            Livre(null, "Harry Potter et le Prisonnier d'Azkaban", "Fantastique")
        )

        //when:
        val response = mockMvc.perform(get("/livre/genre/").param("genre", genre))

        //then:
        response
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$[0].genre").value("Fantastique"))
            .andExpect(jsonPath("$[0].titre").value("Harry Potter à l'école des sorciers"))
            .andExpect(jsonPath("$[1].genre").value("Fantastique"))
            .andExpect(jsonPath("$[1].titre").value("Harry Potter et la Chambre des secrets"))
            .andExpect(jsonPath("$[2].genre").value("Fantastique"))
            .andExpect(jsonPath("$[2].titre").value("Harry Potter et le Prisonnier d'Azkaban"))
    }

    @Test
    fun `getAll retourne les objets`() {
        //given:
        val page = 0
        val size = 20
        val sortBy = "genre"
        val direction = "desc"

        //and:

        every { livreRetriever.findAllPaginated(page, size, sortBy, direction) } returns Page(listOf(
            Livre(null, "Mars la rouge", "Science-Fiction"),
            Livre(null, "Mars la bleue", "Science-Fiction"),
            Livre(null, "Mars la verte", "Science-Fiction"),
            Livre(null, "La Communauté de l'anneau", "Fantastique"),
            Livre(null, "Les Deux Tours", "Fantastique"),
            Livre(null, "Le Retour du Roi", "Fantastique"),
            Livre(null, "Harry Potter à l'école des sorciers", "Fantastique"),
            Livre(null, "Harry Potter et la Chambre des secrets", "Fantastique"),
            Livre(null, "Harry Potter et le Prisonnier d'Azkaban", "Fantastique")
        ), 1, 9)

        //when:
        val response = mockMvc.perform(
            get("/livre/")
                .param("page", "$page")
                .param("size", "$size")
                .param("sortBy", sortBy)
                .param("direction", direction)
        )

        //then:
        response
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.totalPage").value(1))
            .andExpect(jsonPath("$.totalElements").value(9))
            .andExpect(jsonPath("$.datas[0].genre").value("Science-Fiction"))
            .andExpect(jsonPath("$.datas[0].titre").value("Mars la rouge"))
            .andExpect(jsonPath("$.datas[1].genre").value("Science-Fiction"))
            .andExpect(jsonPath("$.datas[1].titre").value("Mars la bleue"))
            .andExpect(jsonPath("$.datas[2].genre").value("Science-Fiction"))
            .andExpect(jsonPath("$.datas[2].titre").value("Mars la verte"))
            .andExpect(jsonPath("$.datas[3].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[3].titre").value("La Communauté de l'anneau"))
            .andExpect(jsonPath("$.datas[4].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[4].titre").value("Les Deux Tours"))
            .andExpect(jsonPath("$.datas[5].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[5].titre").value("Le Retour du Roi"))
            .andExpect(jsonPath("$.datas[6].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[6].titre").value("Harry Potter à l'école des sorciers"))
            .andExpect(jsonPath("$.datas[7].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[7].titre").value("Harry Potter et la Chambre des secrets"))
            .andExpect(jsonPath("$.datas[8].genre").value("Fantastique"))
            .andExpect(jsonPath("$.datas[8].titre").value("Harry Potter et le Prisonnier d'Azkaban"))
    }
}
