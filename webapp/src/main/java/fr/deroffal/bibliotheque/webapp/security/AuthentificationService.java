package fr.deroffal.bibliotheque.webapp.security;

import com.auth0.jwt.impl.JWTParser;
import fr.deroffal.bibliotheque.webapp.http.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthentificationService {

    @Value("${bibliotheque.authentification.url}")
    private String authentificationUrl;

    @Autowired
    private HttpService httpService;

    public String authentification() {
        if (Tokens.AUTHENTIFICATION.isTokenInvalid()) {
            HashMap post = httpService.post(
                    authentificationUrl + "/authenticate",
                    Map.of("username", "webapp", "password", "alex"),
                    HashMap.class,
                    () -> {
                        throw new RuntimeException();
                    }
            );
            Tokens.AUTHENTIFICATION.setToken((String) post.get("token"));
        }
        return Tokens.AUTHENTIFICATION.getToken();
    }

    private static enum Tokens {
        AUTHENTIFICATION;

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private boolean isTokenInvalid() {
            return token == null || new JWTParser().parsePayload(token).getExpiresAt().after(new Date());
        }
    }
}
