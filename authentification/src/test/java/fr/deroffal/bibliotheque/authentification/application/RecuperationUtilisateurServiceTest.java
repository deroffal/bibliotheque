package fr.deroffal.bibliotheque.authentification.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import fr.deroffal.bibliotheque.authentification.domain.service.UtilisateurService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { RecuperationUtilisateurService.class, UtilisateurService.class })
class RecuperationUtilisateurServiceTest {

    @Autowired
    private RecuperationUtilisateurService recuperationUtilisateurService;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("Récupération d'un utilisateur par son username")
    void getByLogin_avecResultat() {
        final Utilisateur utilisateur = new Utilisateur(1L, "user", "ahmlzhe", List.of("USER"));
        when(userRepositoryAdapter.findByUsername("user")).thenReturn(Optional.of(utilisateur));

        final Utilisateur user = recuperationUtilisateurService.getByLogin("user");

        Assertions.assertThat(user).isEqualTo(utilisateur);
    }

    @Test
    @DisplayName("Récupération d'un utilisateur par son username lance une exception si absent")
    void getByLogin_sansResultat() {
        when(userRepositoryAdapter.findByUsername("user")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recuperationUtilisateurService.getByLogin("user"))
            .isInstanceOf(UserNotFoundException.class)
            .matches(e -> {
                final UserNotFoundException exception = (UserNotFoundException)e;
                return exception.getLogin().equals("user");
            });
    }
}
