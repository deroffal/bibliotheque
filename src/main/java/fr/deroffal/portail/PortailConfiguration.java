package fr.deroffal.portail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortailConfiguration {

	public static final String SCHEMA_AUTHENTIFICATION = "authentification";

	public static void main(String[] args) {
		SpringApplication.run(PortailConfiguration.class, args);
	}

}
