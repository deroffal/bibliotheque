package fr.deroffal.bibliotheque.authentification.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    @DisplayName("L'exception possède le nom de l'utilisateur")
    void messageEtHttpStatus() {
        final UserNotFoundException e = new UserNotFoundException("identifiant");

        assertEquals("identifiant", e.getLogin());
    }
}
