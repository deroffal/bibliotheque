package fr.deroffal.bibliotheque.webapp.security;

import com.auth0.jwt.impl.JWTParser;
import fr.deroffal.bibliotheque.webapp.http.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthentificationService {

    @Value("${bibliotheque.authentification.url}")
    private String authentificationUrl;

    private final HttpService httpService;

    public String authentification() {
        if (Tokens.AUTHENTIFICATION.isTokenInvalid()) {
            Map<String, String> post = httpService.post(
                    authentificationUrl + "/authenticate",
                    Map.of("username", "webapp", "password", "alex"),
                    HashMap.class,
                    () -> { throw new UsernameNotFoundException("Impossible de se connecter !"); }
            );
            Tokens.AUTHENTIFICATION.setToken( post.get("jwttoken"));
        }
        return Tokens.AUTHENTIFICATION.getToken();
    }

    private enum Tokens {
        AUTHENTIFICATION;

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(final String token) {
            this.token = token;
        }

        private boolean isTokenInvalid() {
            return token == null || new JWTParser().parsePayload(token).getExpiresAt().after(new Date());
        }
    }

}
