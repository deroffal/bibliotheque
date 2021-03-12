package fr.deroffal.bibliotheque.api.exception;

import static fr.deroffal.bibliotheque.api.Constantes.BASE_PACKAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.servlet.http.HttpServletRequest;

import fr.deroffal.bibliotheque.api.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(BASE_PACKAGE)
public class BibliothequeRestControllerAdvice {

    private final DateUtils dateUtils;

    @ExceptionHandler(BibliothequeRestException.class)
    public ResponseEntity<ExceptionMessage> handleBibliothequeRestException(final HttpServletRequest request, final BibliothequeRestException exception) {
        final ExceptionMessage exceptionMessage = new ExceptionMessage(dateUtils.newLocalDateTime(), request.getRequestURI(), exception);
        return new ResponseEntity<>(exceptionMessage, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleException(final HttpServletRequest request, final Exception exception) {
        final ExceptionMessage exceptionMessage = new ExceptionMessage(dateUtils.newLocalDateTime(), request.getRequestURI(), exception);
        return new ResponseEntity<>(exceptionMessage, INTERNAL_SERVER_ERROR);
    }
}
