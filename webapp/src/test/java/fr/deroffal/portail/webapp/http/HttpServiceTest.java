package fr.deroffal.portail.webapp.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HttpServiceTest {

	@InjectMocks
	private HttpService httpService;

	@Mock
	private HttpClient client;

	@Mock
	private ObjectMapper objectMapper;

	private static ArgumentMatcher<HttpRequest> requestMatches(final String url, final String method) {
		return request -> URI.create(url).equals(request.uri()) && method.equals(request.method());
	}

	@Test
	@DisplayName("Un GET en succès retourne une instance de l'objet attendu")
	void get_sucess_returnObject() throws IOException, InterruptedException {
		String url = "http://url.url";

		HttpResponse<String> response = mock(HttpResponse.class);
		final String bodyAsString = "retour en string";
		when(response.body()).thenReturn(bodyAsString);
		when(response.statusCode()).thenReturn(SC_OK);
		when(client.send(argThat(requestMatches(url, "GET")), eq(BodyHandlers.ofString()))).thenReturn(response);

		final Integer retourAttendu = 23;
		final Class<Integer> clazz = Integer.class;
		when(objectMapper.readValue(bodyAsString, clazz)).thenReturn(retourAttendu);

		final Integer retour = httpService.get(url, clazz, () -> null);

		assertEquals(retourAttendu, retour);
	}

	@Test
	@DisplayName("Un GET avec une exception retourne une instance de l'objet attendu")
	void get_execption_returnObject() throws IOException, InterruptedException {
		String url = "http://url.url";

		when(client.send(argThat(requestMatches(url, "GET")), eq(BodyHandlers.ofString()))).thenThrow(new IOException());

		final Class<Integer> clazz = Integer.class;
		final Integer retourAttendu = 18;
		final Integer retour = httpService.get(url, clazz, () -> retourAttendu);

		assertEquals(retourAttendu, retour);
	}

	@Test
	@DisplayName("Un GET avec un retour >=400 lance une exception")
	void get_failure_throwsException() throws IOException, InterruptedException {
		String url = "http://url.url";

		HttpResponse<String> response = mock(HttpResponse.class);
		when(response.statusCode()).thenReturn(SC_NOT_FOUND);
		when(client.send(argThat(requestMatches(url, "GET")), eq(BodyHandlers.ofString()))).thenReturn(response);

		assertThrows(RuntimeException.class, ()-> httpService.get(url, Integer.class, () -> { throw new RuntimeException();}));
	}

}