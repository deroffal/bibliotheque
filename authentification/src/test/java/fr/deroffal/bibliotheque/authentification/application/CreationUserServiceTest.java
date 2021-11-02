package fr.deroffal.bibliotheque.authentification.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserRepositoryAdapter;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { CreationUserService.class, UserService.class })
class CreationUserServiceTest {

    @Autowired
    private CreationUserService creationUserService;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("Création d'un utilisateur en succès")
    void createUser_OK() {
        final UserDto demandeCreation = new UserDto(null, "admin", "azerty", List.of("ADMIN"));

        when(userRepositoryAdapter.existsByUsername("admin")).thenReturn(false);

        final UserDto utilisateurCree = new UserDto(1L, "admin", "azerty", List.of("ADMIN"));
        when(userRepositoryAdapter.create(demandeCreation)).thenReturn(utilisateurCree);

        final UserDto userDto = creationUserService.create(demandeCreation);

        assertThat(userDto).isEqualTo(utilisateurCree);
    }

    @Test
    @DisplayName("Création d'un utilisateur impossible car il existe déjà")
    void createUser_KO() {
        final UserDto demandeCreation = new UserDto(null, "admin", "azerty", List.of("ADMIN"));

        when(userRepositoryAdapter.existsByUsername("admin")).thenReturn(true);

        assertThatThrownBy(() -> creationUserService.create(demandeCreation)).isInstanceOf(UserAlreadyExistsException.class).matches(e -> {
            UserAlreadyExistsException expection = (UserAlreadyExistsException) e;
            return expection.getMessageClient().equals("Utilisateur admin existe déjà!");
        });
    }
}
