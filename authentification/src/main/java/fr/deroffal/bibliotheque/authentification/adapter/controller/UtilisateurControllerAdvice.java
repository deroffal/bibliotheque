package fr.deroffal.bibliotheque.authentification.adapter.controller;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import fr.deroffal.bibliotheque.authentification.application.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.authentification.application.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class UtilisateurControllerAdvice {

    private static final String MESSAGE_ERREUR_GENERIQUE = "Erreur lors de l'appel du service {} avec les paramètres {}";

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public String exceptionHandler(final Exception e, final WebRequest request) {
        return traiterMessage(request, e);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(value = CONFLICT)
    public String userAlreadyExistsExceptionHandler(final UserAlreadyExistsException e, final WebRequest request) {
        log.error(MESSAGE_ERREUR_GENERIQUE, recupererUri(request), recupererParametres(request), e);
        return String.format("Utilisateur %s déjà existant.", e.getLogin());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public String userNotFoundExceptionHandler(final UserNotFoundException e, final WebRequest request) {
        log.error(MESSAGE_ERREUR_GENERIQUE, recupererUri(request), recupererParametres(request), e);
        return String.format("Utilisateur %s inconnu.", e.getLogin());
    }

    private static String recupererParametres(final WebRequest request) {
        return request.getParameterMap().entrySet().stream()//
            .map(entry -> String.format("%s : %s", entry.getKey(), String.join(", ", entry.getValue())))//
            .collect(joining(", ", "[", "]"));
    }

    private static String recupererUri(final WebRequest request) {
        return request.getDescription(false);
    }

    private static String traiterMessage(final WebRequest request, final Exception exception) {
        log.error(MESSAGE_ERREUR_GENERIQUE, recupererUri(request), recupererParametres(request), exception);
        return exception.getMessage();
    }
}
