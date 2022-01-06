package fr.deroffal.bibliotheque.livre.adapter.controller

import com.ninjasquad.springmockk.MockkBean
import fr.deroffal.bibliotheque.livre.domain.LivreCreator
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
import fr.deroffal.bibliotheque.securite.auth.AuthentificationService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Classe de configuration pour les tests d'intégration de l'adapter Controller.
 */
@Configuration
@ComponentScan(basePackages = ["fr.deroffal.bibliotheque.livre.adapter.controller"])
class ControllerTestContextConfiguration {

    @MockkBean
    private lateinit var livreRetriever: LivreRetriever

    @MockkBean
    private lateinit var livreCreator: LivreCreator

    @MockkBean
    private lateinit var authentificationService: AuthentificationService
}
