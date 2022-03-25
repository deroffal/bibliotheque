package fr.deroffal.bibliotheque.livre.adapter.controller

import com.ninjasquad.springmockk.MockkBean
import fr.deroffal.bibliotheque.livre.domain.LivreAdministrationService
import fr.deroffal.bibliotheque.livre.domain.LivreRetriever
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
    private lateinit var livreAdministrationService: LivreAdministrationService

}
