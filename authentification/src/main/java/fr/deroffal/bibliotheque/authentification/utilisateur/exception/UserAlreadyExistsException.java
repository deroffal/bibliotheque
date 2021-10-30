package fr.deroffal.bibliotheque.authentification.utilisateur.exception;

import fr.deroffal.bibliotheque.commons.exception.BibliothequeRestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistsException extends BibliothequeRestException {

	private final String login;

	@Override
	public String getMessageClient() {
		return "Utilisateur " + getLogin() + " existe déjà!";
	}

	@Override
	public HttpStatus getHttpStatus() {
		return CONFLICT;
	}
}
