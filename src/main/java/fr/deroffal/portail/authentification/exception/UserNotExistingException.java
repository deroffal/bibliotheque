package fr.deroffal.portail.authentification.exception;

import org.springframework.http.HttpStatus;

import fr.deroffal.portail.exception.PortailRestException;

public class UserNotExistingException extends PortailRestException {

	private final String login;

	public UserNotExistingException(final String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String getMessageClient() {
		return "Utilisateur non-existant : " + getLogin();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
