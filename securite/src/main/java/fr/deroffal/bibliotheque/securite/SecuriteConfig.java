package fr.deroffal.bibliotheque.securite;

import java.util.Collection;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "securite.authentification")
class SecuriteConfig {

    @Setter
    private Collection<String> listeBlanche;

    public String[] getListeBlanche() {
        return listeBlanche.toArray(new String[0]);
    }
}
