package fr.deroffal.bibliotheque.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;

@SpringBootApplication
public class WebappConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(WebappConfiguration.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClient.newHttpClient();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
