package fr.deroffal.bibliotheque.webapp.login;

import fr.deroffal.bibliotheque.webapp.http.HttpService;
import fr.deroffal.bibliotheque.webapp.security.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    @Value("${bibliotheque.authentification.url}")
    private String authentificationUrl;

    private final AuthentificationService authentificationService;
    private final HttpService httpService;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final String url = authentificationUrl + "/user/" + username;
        final String token = authentificationService.authentification();
        final UserResponse response = httpService.get(
                url,
                Map.of("Authorization", "Bearer " + token),
                UserResponse.class,
                () -> {
                    throw new UsernameNotFoundException("Inconnu!");
                }
        );
        return User
                .withUsername(response.login())
                .password(response.password())
                .roles(response.roles())
                .build();
    }
}
