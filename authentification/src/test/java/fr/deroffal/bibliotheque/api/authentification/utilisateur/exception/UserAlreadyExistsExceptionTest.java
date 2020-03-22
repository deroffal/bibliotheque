package fr.deroffal.bibliotheque.api.authentification.utilisateur.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CONFLICT;

class UserAlreadyExistsExceptionTest {

	@Test
	@DisplayName("L'exception retourne le message et le code HTTP 409.")
	void messageEtHttpStatus() {
		UserAlreadyExistsException e = new UserAlreadyExistsException("identifiant");

		assertEquals(CONFLICT, e.getHttpStatus());
		assertEquals("Utilisateur identifiant existe déjà!", e.getMessageClient());
	}

}