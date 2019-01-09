package fr.deroffal.portail.authentification.exception;

import org.springframework.http.HttpStatus;

import fr.deroffal.portail.exception.PortailRestException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFoundException extends PortailRestException {

	private final String login;

	public UserNotFoundException(final String login) {
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
		return NOT_FOUND;
	}
}
