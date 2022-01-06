package fr.deroffal.bibliotheque.livre.adapter.controller

import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreNotFoundException
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
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
        every { livreRetriever.findById(uuid) } throws LivreNotFoundException()

        //when:
        val response = mockMvc.perform(get("/livre/$uuid"))

        //then:
        response
            .andExpect(status().isNotFound)
            .andExpect(content().contentTypeCompatibleWith(TEXT_PLAIN))
    }
}
