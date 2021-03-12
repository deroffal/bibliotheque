package fr.deroffal.bibliotheque.api.authentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static fr.deroffal.bibliotheque.api.Constantes.BASE_PACKAGE;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class AuthentificationConfiguration {

	public static void main(final String[] args) {
		SpringApplication.run(AuthentificationConfiguration.class, args);
	}

}
