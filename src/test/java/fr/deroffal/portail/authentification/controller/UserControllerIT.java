package fr.deroffal.portail.authentification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fr.deroffal.portail.AbstractIntegrationTest;
import fr.deroffal.portail.authentification.entity.UserEntity;

class UserControllerIT extends AbstractIntegrationTest {

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est connu.")
	void rechercherUtilisateur_loginConnu() {
		ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user2"), UserEntity.class);
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
		ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user3"), UserEntity.class);
		assertNotNull(response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Override
	protected String getDataSetName() {
		return "authentification.xml";
	}
}
