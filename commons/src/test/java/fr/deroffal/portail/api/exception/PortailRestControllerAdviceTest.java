package fr.deroffal.portail.api.exception;

import fr.deroffal.portail.api.util.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.HTTP_VERSION_NOT_SUPPORTED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ExtendWith(MockitoExtension.class)
class PortailRestControllerAdviceTest {

	@InjectMocks
	private PortailRestControllerAdvice controllerAdvice;

	@Mock
	private DateUtils dateUtils;

	@Test
	@DisplayName("Gestion d'une exception de type PortailRestException")
	void handlePortailRestException() {
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("request uri");

		final LocalDateTime now = LocalDateTime.now();
		when(dateUtils.newLocalDateTime()).thenReturn(now);

		final PortailRestException e = new PortailRestExceptionImpl("message attendu", HTTP_VERSION_NOT_SUPPORTED);

		final ResponseEntity<ExceptionMessage> response = controllerAdvice.handle(request, e);

		assertEquals(HTTP_VERSION_NOT_SUPPORTED, response.getStatusCode());
		final ExceptionMessage message = response.getBody();
		assertAll(
				() -> assertEquals("request uri", message.getUri()),
				() -> assertEquals("message attendu", message.getMessage()),
				() -> assertEquals(now.format(ISO_LOCAL_DATE_TIME), message.getDate())
				);
	}

	@Test
	@DisplayName("Gestion d'une exception")
	void handleException() {
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("request uri");

		final LocalDateTime now = LocalDateTime.now();
		when(dateUtils.newLocalDateTime()).thenReturn(now);

		final Exception e = new Exception("message attendu");

		final ResponseEntity<ExceptionMessage> response = controllerAdvice.handle(request, e);

		assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
		final ExceptionMessage message = response.getBody();
		assertAll(
				() -> assertEquals("request uri", message.getUri()),
				() -> assertEquals("Une erreur interne est survenue : message attendu", message.getMessage()),
				() -> assertEquals(now.format(ISO_LOCAL_DATE_TIME), message.getDate())
		);
	}
	private final class PortailRestExceptionImpl extends PortailRestException {

		private final String messageClient;
		private final HttpStatus httpStatus;

		private PortailRestExceptionImpl(final String messageClient, final HttpStatus httpStatus) {
			this.messageClient = messageClient;
			this.httpStatus = httpStatus;
		}

		@Override
		public String getMessageClient() {
			return messageClient;
		}

		@Override
		public HttpStatus getHttpStatus() {
			return httpStatus;
		}
	}

}