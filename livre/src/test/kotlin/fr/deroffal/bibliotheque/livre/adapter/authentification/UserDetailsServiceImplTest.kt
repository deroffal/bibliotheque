package fr.deroffal.bibliotheque.livre.adapter.authentification

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@SpringBootTest
@AutoConfigureWireMock(port = 8989)
internal class UserDetailsServiceImplTest {

    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Test
    fun `load existing user`() {
        val user = userDetailsServiceImpl.loadUserByUsername("dupont")

        user.username shouldBe "dupont"
        user.password shouldBe "\$2a\$10\$6joC/UrDxKR4CZtkDEOi9uovbsM7nt1RA7TsFczudmr7Ir5dHPImK"
    }

    @Test
    fun `load unknown user`() {
        val exception = shouldThrow<UsernameNotFoundException> { userDetailsServiceImpl.loadUserByUsername("dupond") }

        exception.message shouldBe "Utilisateur dupond inconnu !"
    }
}
