package fr.deroffal.bibliotheque.securite.filter

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
//@ConfigurationProperties(prefix = "jwt")
class JwtConfig {

    private final String secret

    JwtConfig(@Value("\${jwt.secret}") final String secret) {
        this.secret = secret
    }

    String getSecret() {
        return secret
    }

    @Bean
    JwtParser parser() {
        Jwts.parser().setSigningKey(secret)
    }
}
