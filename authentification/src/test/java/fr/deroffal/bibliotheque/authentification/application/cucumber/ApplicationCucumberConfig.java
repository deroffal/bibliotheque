package fr.deroffal.bibliotheque.authentification.application.cucumber;

import fr.deroffal.bibliotheque.authentification.application.CreationUserService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUserService;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@CucumberContextConfiguration
@ContextConfiguration(classes = { CreationUserService.class, RecuperationUserService.class, UserService.class })
public class ApplicationCucumberConfig {

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;
}
