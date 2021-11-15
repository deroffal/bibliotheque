package fr.deroffal.bibliotheque.securite.filter

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {

    private String secret

    String getSecret() {
        return secret
    }

    void setSecret(final String secret) {
        this.secret = secret
    }

    @Bean
    JwtParser parser(){
        Jwts.parser().setSigningKey(secret)
    }
}
