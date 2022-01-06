package fr.deroffal.bibliotheque.livre.adapter.controller.authentification

import fr.deroffal.bibliotheque.livre.adapter.controller.ControllerTestContextConfiguration
import fr.deroffal.bibliotheque.securite.auth.AuthentificationService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@WithMockUser
@ContextConfiguration(classes = [ControllerTestContextConfiguration::class])
internal class AuthentificationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var authentificationService: AuthentificationService

    @Test
    fun `authenticate delegue l'authentification au service`() {
        //given:
        val username = "toto"
        val password = "Azerty12*"

        //and:
        val expectedToken = "token"
        every { authentificationService.authenticate(username, password) } returns expectedToken

        //when:
        val response = mockMvc.perform(
            post("/authenticate")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(
                    """{
                        |"username": "$username",
                        |"password": "$password"
                        |}""".trimMargin()
                )
        )

        //then:
        response
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.jwtToken").value(expectedToken))

    }
}
