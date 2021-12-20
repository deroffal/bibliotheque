package fr.deroffal.bibliotheque.authentification.application.cucumber;

import fr.deroffal.bibliotheque.authentification.application.CreationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import fr.deroffal.bibliotheque.authentification.domain.service.UtilisateurService;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@CucumberContextConfiguration
@ContextConfiguration(classes = { CreationUtilisateurService.class, RecuperationUtilisateurService.class, UtilisateurService.class })
public class ApplicationCucumberConfig {

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;
}
