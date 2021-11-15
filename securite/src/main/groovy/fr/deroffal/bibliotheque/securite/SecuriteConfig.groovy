package fr.deroffal.bibliotheque.securite

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "securite.authentification")
class SecuriteConfig {

    private Collection<String> listeBlanche

    SecuriteConfig(final Collection<String> listeBlanche) {
        this.listeBlanche = listeBlanche
    }

    Collection<String> getListeBlanche() {
        listeBlanche
    }
}
