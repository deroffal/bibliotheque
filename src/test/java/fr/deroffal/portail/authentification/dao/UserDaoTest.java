package fr.deroffal.portail.authentification.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.deroffal.portail.authentification.entity.RoleEntity;
import fr.deroffal.portail.authentification.entity.UserEntity;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TestEntityManager testEntityManager;

	private UserEntity userAdmin;

	private UserEntity user;

	private RoleEntity roleAdmin;

	private RoleEntity roleUser;

	@BeforeEach
	void beforeEach() {
		userAdmin = new UserEntity();
		userAdmin.setLogin("admin");
		userAdmin.setPassword("azertyuiop");

		roleAdmin = new RoleEntity();
		roleAdmin.setRole("ADMIN");
		testEntityManager.persist(roleAdmin);
		roleUser = new RoleEntity();
		roleUser.setRole("USER");
		testEntityManager.persist(roleUser);

		userAdmin.setRoles(List.of(roleAdmin, roleUser));

		user = new UserEntity();
		user.setLogin("user");
		user.setPassword("qsdfghjklm");

		testEntityManager.persist(userAdmin);
		testEntityManager.persist(user);
	}

	@Test
	@DisplayName("Récupération de tous les utilisateurs.")
	void findAll() {
		final List<UserEntity> collect = StreamSupport.stream(userDao.findAll().spliterator(), false).collect(Collectors.toList());

		assertAll("On doit retrouver les 2 utilisateurs créés.",
				()-> assertEquals(2, collect.size()),
				()-> assertTrue(collect.contains(userAdmin)),
				()-> assertTrue(collect.contains(user))
				);

	}

	@Test
	@DisplayName("Récupération d'un utilisateur par son login.")
	void findByLogin() {
		final UserEntity actualUser = userDao.findByLogin("admin");
		assertEquals(userAdmin, actualUser);

		final Collection<RoleEntity> actualUserRoles = actualUser.getRoles();
		assertAll("L'utilisateur 'admin' est USER et ADMIN",
				  ()-> assertEquals(2, actualUserRoles.size()),
				  ()-> assertTrue(actualUserRoles.contains(roleAdmin)),
				  ()-> assertTrue(actualUserRoles.contains(roleUser))
		);

	}
}
