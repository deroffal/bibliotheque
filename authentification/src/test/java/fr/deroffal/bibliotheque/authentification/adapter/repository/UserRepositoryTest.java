package fr.deroffal.bibliotheque.authentification.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-dataJpaTest.properties")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Récupération d'un utilisateur par son username.")
    void findByUsername() {
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

        final Optional<UserEntity> admin = repository.findByUsername("admin");
        assertThat(admin).isNotEmpty();

        final UserEntity actualUser = admin.get();
        assertThat(actualUser.getUsername()).isEqualTo("admin");
        assertThat(actualUser.getPassword()).isEqualTo("azertyuiop");

        final Stream<String> roles = actualUser.getRoles().stream().map(RoleEntity::getRole);
        assertThat(roles).containsExactlyInAnyOrder("ADMIN", "USER");
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

        assertThat(repository.existsByUsername(userAdmin.getUsername())).isTrue();
        assertThat(repository.existsByUsername(user.getUsername())).isTrue();
        assertThat(repository.existsByUsername("un autre username")).isFalse();
    }
}
