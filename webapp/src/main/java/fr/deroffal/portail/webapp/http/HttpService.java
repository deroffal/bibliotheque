package fr.deroffal.portail.webapp.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Supplier;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * https://blog.codefx.org/java/http-2-api-tutorial/
 */
@Service
public class HttpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpService.class);

	@Autowired
	private HttpClient client;

	@Autowired
	private ObjectMapper objectMapper;

	private static boolean isSuccess(final int statusCode) {
		return statusCode < SC_BAD_REQUEST;
	}

	public <T> T get(final String uri, final Class<T> clazz, final Supplier<T> failure) {
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).GET().build();
		try {
			final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (isSuccess(response.statusCode())) {
				return objectMapper.readValue(response.body(), clazz);
			}
		} catch (Exception e) {
			LOGGER.warn("GET {} - exception", uri, e);
		}
		return failure.get();
	}

}
