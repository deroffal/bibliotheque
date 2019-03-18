package fr.deroffal.portail.authentification.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import fr.deroffal.portail.authentification.entity.RoleEntity;
import fr.deroffal.portail.authentification.entity.UserEntity;

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
		RoleEntity roleAdmin = new RoleEntity("ADMIN");
		testEntityManager.persist(roleAdmin);
		RoleEntity roleUser = new RoleEntity("USER");
		testEntityManager.persist(roleUser);

		UserEntity userAdmin = new UserEntity();
		userAdmin.setLogin("admin");
		userAdmin.setPassword("azertyuiop");
		userAdmin.setRoles(Arrays.asList(roleAdmin, roleUser));
		testEntityManager.persist(userAdmin);

		final UserEntity actualUser = userDao.findByLogin("admin");
		assertEquals(userAdmin, actualUser);

		final Collection<RoleEntity> actualUserRoles = actualUser.getRoles();
		assertAll("L'utilisateur 'admin' est USER et ADMIN",
				  () -> assertEquals(2, actualUserRoles.size()),
				  () -> assertTrue(actualUserRoles.contains(roleAdmin)),
				  () -> assertTrue(actualUserRoles.contains(roleUser))
		);
	}

}

