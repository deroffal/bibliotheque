package fr.deroffal.bibliotheque.api.authentification.utilisateur.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class UserNotFoundExceptionTest {

	@Test
	@DisplayName("L'exception retourne le message et le code HTTP 404.")
	void messageEtHttpStatus() {
		UserNotFoundException e = new UserNotFoundException("identifiant");

		assertEquals(NOT_FOUND, e.getHttpStatus());
		assertEquals("Utilisateur non-existant : identifiant", e.getMessageClient());
	}

}