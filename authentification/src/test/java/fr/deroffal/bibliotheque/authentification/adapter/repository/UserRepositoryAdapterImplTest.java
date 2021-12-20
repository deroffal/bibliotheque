package fr.deroffal.bibliotheque.authentification.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource(locations = "classpath:application-dataJpaTest.properties")
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class UserRepositoryAdapterImplTest {

    @Autowired
    private UserRepositoryAdapterImpl userRepositoryAdapter;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Récupération d'un utilisateur par son username.")
    void findByLogin() {
        final RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRole("ADMIN");
        testEntityManager.persist(roleAdmin);
        final RoleEntity roleUser = new RoleEntity();
        roleUser.setRole("USER");
        testEntityManager.persist(roleUser);

        final UserEntity userAdmin = new UserEntity();
        userAdmin.setUsername("admin");
        userAdmin.setPassword("azertyuiop");
        userAdmin.setRoles(List.of(roleAdmin, roleUser));
        testEntityManager.persist(userAdmin);

        final Optional<Utilisateur> admin = userRepositoryAdapter.findByUsername("admin");
        assertThat(admin).isNotEmpty();
        final Utilisateur actualUser = admin.get();
        assertThat(actualUser.username()).isEqualTo("admin");
        assertThat(actualUser.password()).isEqualTo("azertyuiop");
        assertThat(actualUser.roles()).containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    @DisplayName("Vérification de l'existance d'un username.")
    void existsByLogin() {
        final UserEntity userAdmin = new UserEntity();
        userAdmin.setUsername("admin");
        userAdmin.setPassword("azertyuiop");

        final UserEntity user = new UserEntity();
        user.setUsername("user");
        user.setPassword("qsdfghjklm");

        testEntityManager.persist(userAdmin);
        testEntityManager.persist(user);

        assertThat(userRepositoryAdapter.existsByUsername(userAdmin.getUsername())).isTrue();
        assertThat(userRepositoryAdapter.existsByUsername(user.getUsername())).isTrue();
        assertThat(userRepositoryAdapter.existsByUsername("un autre username")).isFalse();
    }

    @Test
    @DisplayName("Création d'un utilisateur")
    void create() {
        final Utilisateur utilisateur = new Utilisateur(null, "admin", "azertyuiop", null);

        final Utilisateur user = userRepositoryAdapter.create(utilisateur);

        final UserEntity userEntity = testEntityManager.find(UserEntity.class, user.id());

        assertThat(userEntity.getUsername()).isEqualTo(utilisateur.username());
        assertThat(userEntity.getPassword()).isNotNull();
    }
}

