package fr.deroffal.portail.api.authentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static fr.deroffal.portail.api.Constantes.BASE_PACKAGE;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class AuthentificationConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationConfiguration.class, args);
	}

}
