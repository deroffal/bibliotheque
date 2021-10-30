package fr.deroffal.bibliotheque.commons.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
