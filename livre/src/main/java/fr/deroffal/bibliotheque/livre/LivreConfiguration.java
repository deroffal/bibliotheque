package fr.deroffal.bibliotheque.livre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.deroffal.bibliotheque")
public class LivreConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(LivreConfiguration.class, args);
    }
}
