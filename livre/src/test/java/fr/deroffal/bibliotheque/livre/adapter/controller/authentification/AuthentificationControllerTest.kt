package fr.deroffal.bibliotheque.livre.adapter.controller.authentification

import fr.deroffal.bibliotheque.securite.auth.AuthentificationService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class AuthentificationControllerTest {

    @Test
    fun `authenticate delegue l'authentification au service`() {
        //given:
        val username = "username"
        val password = "password"

        val request = JwtRequest(username, password)

        //and:
        val expectedToken = "token"
        val authentificationService = mock<AuthentificationService> {
            on { it.authenticate(username, password) }.thenReturn(expectedToken)
        }

        //when:
        val authentificationController = AuthentificationController(authentificationService)
        val response = authentificationController.authenticate(request)

        //then:
        response.jwtToken shouldBe expectedToken
    }
}
