package fr.deroffal.bibliotheque.authentification.utilisateur.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import fr.deroffal.bibliotheque.commons.exception.BibliothequeRestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends BibliothequeRestException {

    private final String login;

    @Override
    public String getMessageClient() {
        return "Utilisateur non-existant : " + getLogin();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return NOT_FOUND;
    }
}
