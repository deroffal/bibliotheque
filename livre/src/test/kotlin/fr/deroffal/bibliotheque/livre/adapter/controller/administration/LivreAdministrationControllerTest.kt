package fr.deroffal.bibliotheque.livre.adapter.controller.administration

import fr.deroffal.bibliotheque.livre.adapter.controller.ControllerTestContextConfiguration
import fr.deroffal.bibliotheque.livre.domain.Livre
import fr.deroffal.bibliotheque.livre.domain.LivreAdministrationService
import io.mockk.every
import org.hamcrest.core.StringStartsWith
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.UUID.randomUUID

@WebMvcTest
@WithMockUser
@ContextConfiguration(classes = [ControllerTestContextConfiguration::class])
internal class LivreAdministrationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var livreAdministrationService: LivreAdministrationService

    @Test
    fun `export retourne un export csv pour un media-type text-csv`() {
        //given:
        val livre1 = Livre(randomUUID(), "Harry Potter", "Fantastique")
        val livre2 = Livre(randomUUID(), "Dune", "Science-fiction")

        every { livreAdministrationService.findAll() } returns listOf(livre1, livre2)

        //when:
        val response = mockMvc.perform(get("/admin/livre/export").accept("text/csv"))

        //then:
        response
            .andExpect(status().isOk)//
            .andExpect(
                header().string("Content-Disposition", StringStartsWith("attachment; filename="))
            )//
            .andExpect(content().contentTypeCompatibleWith("text/csv"))//
            .andExpect(
                content().string(
                    """titre;genre
Harry Potter;Fantastique
Dune;Science-fiction
"""
                )
            )
    }


    @Test
    fun `export retourne un export en json pour un media-type application-json`() {
        //given:
        val livre1 = Livre(randomUUID(), "Harry Potter", "Fantastique")
        val livre2 = Livre(randomUUID(), "Dune", "Science-fiction")

        every { livreAdministrationService.findAll() } returns listOf(livre1, livre2)

        //when:
        val response = mockMvc.perform(get("/admin/livre/export").accept("application/json"))

        //then:
        response
            .andExpect(status().isOk)//
            .andExpect(header().string("Content-Disposition", StringStartsWith("attachment; filename=")))//
            .andExpect(content().contentTypeCompatibleWith("application/json"))//
            .andExpect(content().string("""[{"titre":"Harry Potter","genre":"Fantastique"},{"titre":"Dune","genre":"Science-fiction"}]"""))
    }
}
