package fr.deroffal.bibliotheque.authentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static fr.deroffal.bibliotheque.commons.Constantes.BASE_PACKAGE;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class AuthentificationConfiguration {

	public static void main(final String[] args) {
		SpringApplication.run(AuthentificationConfiguration.class, args);
	}

}
