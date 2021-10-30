package fr.deroffal.bibliotheque.authentification.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class UserRepositoryAdapterImplTest {

    @Autowired
    private UserRepositoryAdapterImpl userRepositoryAdapter;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Récupération d'un utilisateur par son login.")
    void findByLogin() {
        final RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRole("ADMIN");
        testEntityManager.persist(roleAdmin);
        final RoleEntity roleUser = new RoleEntity();
        roleUser.setRole("USER");
        testEntityManager.persist(roleUser);

        final UserEntity userAdmin = new UserEntity();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("azertyuiop");
        userAdmin.setRoles(List.of(roleAdmin, roleUser));
        testEntityManager.persist(userAdmin);

        final Optional<UserDto> admin = userRepositoryAdapter.findByLogin("admin");
        assertThat(admin).isNotEmpty();
        final UserDto actualUser = admin.get();
        assertThat(actualUser.login()).isEqualTo("admin");
        assertThat(actualUser.password()).isEqualTo("azertyuiop");
        assertThat(actualUser.roles()).containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    @DisplayName("Vérification de l'existance d'un login.")
    void existsByLogin() {
        final UserEntity userAdmin = new UserEntity();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("azertyuiop");

        final UserEntity user = new UserEntity();
        user.setLogin("user");
        user.setPassword("qsdfghjklm");

        testEntityManager.persist(userAdmin);
        testEntityManager.persist(user);

        assertThat(userRepositoryAdapter.existsByLogin(userAdmin.getLogin())).isTrue();
        assertThat(userRepositoryAdapter.existsByLogin(user.getLogin())).isTrue();
        assertThat(userRepositoryAdapter.existsByLogin("un autre login")).isFalse();
    }

    @Test
    @DisplayName("Création d'un utilisateur")
    void create() {
        final UserDto userDto = new UserDto(null, "admin", "azertyuiop", null);

        final UserDto user = userRepositoryAdapter.create(userDto);

        final UserEntity userEntity = testEntityManager.find(UserEntity.class, user.id());

        assertThat(userEntity.getLogin()).isEqualTo(userDto.login());
        assertThat(userEntity.getPassword()).isNotNull();
    }
}

