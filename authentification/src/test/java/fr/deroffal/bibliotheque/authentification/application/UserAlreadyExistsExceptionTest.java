package fr.deroffal.bibliotheque.authentification.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserAlreadyExistsExceptionTest {

    @Test
    @DisplayName("L'exception possède le nom de l'utilisateur")
    void messageEtHttpStatus() {
        final UserAlreadyExistsException e = new UserAlreadyExistsException("identifiant");

        assertEquals("identifiant", e.getLogin());
    }
}
