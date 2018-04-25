package fr.deroffal.portail.authentification.controller;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fr.deroffal.portail.AbstractIntegrationTest;
import fr.deroffal.portail.authentification.entity.RoleEntity;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.exception.ExceptionMessage;

class UserControllerIT extends AbstractIntegrationTest {

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est connu.")
	void rechercherUtilisateur_loginConnu() {
		final ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user2"), UserEntity.class);
		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		final UserEntity actualUser = response.getBody();

		assertEquals(3L, actualUser.getId().longValue());
		assertEquals("user2", actualUser.getLogin());
		assertEquals("$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC", actualUser.getPassword());

		final Collection<RoleEntity> actualUserRoles = actualUser.getRoles();
		assertAll("Vérififcation des rôles",
				  () -> assertEquals(1, actualUserRoles.size()),
				  () -> assertEquals("USER", actualUserRoles.iterator().next().getRole())

		);
	}

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est inconnu.")
	void rechercherUtilisateur_loginInconnu() {
		final ResponseEntity<?> response = restTemplate.getForEntity(buildUrl("/user/user3"), Object.class);
		assertNotNull(response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		//L'objet reçu correspond à un ExceptionMessage.
		final Object body = response.getBody();
		Map<String, Object> messages = (Map<String, Object>) body;
		assertEquals("/user/user3", messages.get("uri"));
		assertEquals("Utilisateur non-existant : user3", messages.get("message"));
	}

	@Override
	protected String getDataSetName() {
		return "authentification.xml";
	}
}
