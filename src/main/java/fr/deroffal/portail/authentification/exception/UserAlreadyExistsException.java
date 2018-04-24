package fr.deroffal.portail.authentification.exception;

import org.springframework.http.HttpStatus;

import fr.deroffal.portail.exception.PortailRestException;

public class UserAlreadyExistsException extends PortailRestException {

	private final String login;

	public UserAlreadyExistsException(final String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String getMessageClient() {
		return "Utilisateur " + getLogin()+" existe déjà!";
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}
}
