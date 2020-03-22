package fr.deroffal.bibliotheque.api.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionMessage {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	private String date;

	private String uri;

	private String message;

	public ExceptionMessage() {
		super();
	}

	public ExceptionMessage(final LocalDateTime date, final String uri, final Exception exception) {
		this.date = date.format(DATE_TIME_FORMATTER);
		this.uri = uri;
		this.message = "Une erreur interne est survenue : " + exception.getMessage();
	}

	public ExceptionMessage(final LocalDateTime date, final String uri, final BibliothequeRestException exception) {
		this.date = date.format(DATE_TIME_FORMATTER);
		this.uri = uri;
		this.message = exception.getMessageClient();
	}

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
