package fr.deroffal.portail.api.exception;

import fr.deroffal.portail.api.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static fr.deroffal.portail.api.Constantes.BASE_PACKAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(BASE_PACKAGE)
public class PortailRestControllerAdvice {

	@Autowired
	private DateUtils dateUtils;

	@ExceptionHandler(PortailRestException.class)
	public ResponseEntity<ExceptionMessage> handle(final HttpServletRequest request, final PortailRestException exception) {
		final ExceptionMessage exceptionMessage = new ExceptionMessage(dateUtils.newLocalDateTime(), request.getRequestURI(), exception);
		return new ResponseEntity<>(exceptionMessage, exception.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> handle(final HttpServletRequest request, final Exception exception) {
		final ExceptionMessage exceptionMessage = new ExceptionMessage(dateUtils.newLocalDateTime(), request.getRequestURI(), exception);
		return new ResponseEntity<>(exceptionMessage, INTERNAL_SERVER_ERROR);
	}

}
