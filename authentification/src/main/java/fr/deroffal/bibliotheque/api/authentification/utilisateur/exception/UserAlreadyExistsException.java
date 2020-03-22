package fr.deroffal.bibliotheque.api.authentification.utilisateur.exception;

import fr.deroffal.bibliotheque.api.exception.BibliothequeRestException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

public class UserAlreadyExistsException extends BibliothequeRestException {

	private final String login;

	public UserAlreadyExistsException(final String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String getMessageClient() {
		return "Utilisateur " + getLogin() + " existe déjà!";
	}

	@Override
	public HttpStatus getHttpStatus() {
		return CONFLICT;
	}
}
