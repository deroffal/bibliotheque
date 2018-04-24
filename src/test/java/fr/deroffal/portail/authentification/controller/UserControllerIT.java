package fr.deroffal.portail.authentification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fr.deroffal.portail.AbstractIntegrationTest;
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

		//TODO : vérifier les rôles (besoin d'entitygraph sur l'entity)
	}

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est inconnu.")
	void rechercherUtilisateur_loginInconnu() {
		final ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user3"), UserEntity.class);
		assertNotNull(response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		//TODO comment vérifier que l'on reçoit bien le message d'erreur?
//		final Object body = response.getBody();
//		assertTrue(body instanceof ExceptionMessage);
//		final ExceptionMessage actualMessage = (ExceptionMessage) body;
//		assertEquals("/user/user3", actualMessage.getUri());
//		assertEquals("Utilisateur non-existant : user3", actualMessage.getMessage());
	}

	@Override
	protected String getDataSetName() {
		return "authentification.xml";
	}
}
