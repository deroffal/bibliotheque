package fr.deroffal.bibliotheque.authentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.deroffal.bibliotheque")
public class AuthentificationConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(AuthentificationConfiguration.class, args);
    }
}
