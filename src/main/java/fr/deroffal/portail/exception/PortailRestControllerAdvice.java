package fr.deroffal.portail.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PortailRestControllerAdvice {

	@ExceptionHandler(PortailRestException.class)
	public ResponseEntity<ExceptionMessage> portailRestExceptionHandler(final HttpServletRequest request, final PortailRestException exception) {
		final ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDateTime.now(), request.getRequestURI(), exception);
		return new ResponseEntity<>(exceptionMessage, exception.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> exceptionHandler(final HttpServletRequest request, final Exception exception) {
		final ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDateTime.now(), request.getRequestURI(), exception);
		return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
