package fr.deroffal.bibliotheque.authentification.securite;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import fr.deroffal.bibliotheque.commons.exception.BibliothequeRestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthentificationException extends BibliothequeRestException {

    private final String messageClient;

    public AuthentificationException(final String messageClient, final Exception e) {
        this.messageClient = messageClient;
        initCause(e);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return UNAUTHORIZED;
    }
}
