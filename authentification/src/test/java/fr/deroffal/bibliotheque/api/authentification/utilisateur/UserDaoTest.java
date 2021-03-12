package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Récupération de tous les utilisateurs.")
    void findAll() {
        UserEntity userAdmin = new UserEntity();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("azertyuiop");

        UserEntity user = new UserEntity();
        user.setLogin("user");
        user.setPassword("qsdfghjklm");

        testEntityManager.persist(userAdmin);
        testEntityManager.persist(user);

        final Iterable<UserEntity> users = userDao.findAll();
        final List<UserEntity> collect = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

        assertAll("On doit retrouver les 2 utilisateurs créés.",
            () -> assertEquals(2, collect.size()),
            () -> assertTrue(collect.contains(userAdmin)),
            () -> assertTrue(collect.contains(user))
        );
    }

    @Test
    @DisplayName("Récupération d'un utilisateur par son login.")
    void findByLogin() {
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRole("ADMIN");
        testEntityManager.persist(roleAdmin);
        RoleEntity roleUser = new RoleEntity();
        roleUser.setRole("USER");
        testEntityManager.persist(roleUser);

        UserEntity userAdmin = new UserEntity();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("azertyuiop");
        userAdmin.setRoles(List.of(roleAdmin, roleUser));
        testEntityManager.persist(userAdmin);

        final Optional<UserEntity> actualUserOp = userDao.findByLogin("admin");
        Assertions.assertThat(actualUserOp).isNotEmpty();

        final UserEntity actualUser = actualUserOp.get();
        assertEquals(userAdmin, actualUser);

        final Collection<RoleEntity> actualUserRoles = actualUser.getRoles();
        assertAll("L'utilisateur 'admin' est USER et ADMIN",
            () -> assertEquals(2, actualUserRoles.size()),
            () -> assertTrue(actualUserRoles.contains(roleAdmin)),
            () -> assertTrue(actualUserRoles.contains(roleUser))
        );
    }

    @Test
    @DisplayName("Vérification de l'existance d'un login.")
    void existsByLogin() {
        UserEntity userAdmin = new UserEntity();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("azertyuiop");

        UserEntity user = new UserEntity();
        user.setLogin("user");
        user.setPassword("qsdfghjklm");

        testEntityManager.persist(userAdmin);
        testEntityManager.persist(user);

        assertTrue(userDao.existsByLogin(userAdmin.getLogin()));
        assertTrue(userDao.existsByLogin(user.getLogin()));
        assertFalse(userDao.existsByLogin("un autre login"));
    }
}

