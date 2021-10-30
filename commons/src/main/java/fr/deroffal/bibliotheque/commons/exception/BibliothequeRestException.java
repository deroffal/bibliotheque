package fr.deroffal.bibliotheque.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception pouvant être retournée dans un RestController.
 */
public abstract class BibliothequeRestException extends RuntimeException {

	/**
	 * @return le message à afficher au client.
	 */
	public abstract String getMessageClient();

	/**
	 * @return le status HTTP pour la réponse.
	 */
	public abstract HttpStatus getHttpStatus();

}
