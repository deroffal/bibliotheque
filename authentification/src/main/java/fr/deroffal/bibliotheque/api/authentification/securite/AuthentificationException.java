package fr.deroffal.bibliotheque.api.authentification.securite;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import fr.deroffal.bibliotheque.api.exception.BibliothequeRestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthentificationException extends BibliothequeRestException {

    private static final long serialVersionUID = 3501259216479125381L;

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
