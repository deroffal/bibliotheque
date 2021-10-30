package fr.deroffal.bibliotheque.authentification.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { RecuperationUserService.class, UserService.class })
class RecuperationUserServiceTest {

    @Autowired
    private RecuperationUserService recuperationUserService;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("Récupération d'un utilisateur par son login")
    void getByLogin_avecResultat() {
        final UserDto utilisateur = new UserDto(1L, "user", "ahmlzhe", List.of("USER"));
        when(userRepositoryAdapter.findByLogin("user")).thenReturn(Optional.of(utilisateur));

        final UserDto user = recuperationUserService.getByLogin("user");

        Assertions.assertThat(user).isEqualTo(utilisateur);
    }

    @Test
    @DisplayName("Récupération d'un utilisateur par son login lance une exception si absent")
    void getByLogin_sansResultat() {
        when(userRepositoryAdapter.findByLogin("user")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recuperationUserService.getByLogin("user"))
            .isInstanceOf(UserNotFoundException.class)
            .matches(e -> {
                final UserNotFoundException exception = (UserNotFoundException)e;
                return exception.getLogin().equals("user");
            });
    }
}
