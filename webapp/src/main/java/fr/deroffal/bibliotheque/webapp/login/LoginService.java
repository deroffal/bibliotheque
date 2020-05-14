package fr.deroffal.bibliotheque.webapp.login;

import fr.deroffal.bibliotheque.webapp.http.HttpService;
import fr.deroffal.bibliotheque.webapp.security.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService implements UserDetailsService {

    @Value("${bibliotheque.authentification.url}")
    private String authentificationUrl;

    @Autowired
    private AuthentificationService authentificationService;

    @Autowired
    private HttpService httpService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String url = authentificationUrl + "/user/" + username;
        final UserResponse response = httpService.get(
                url,
                Map.of("Authorization", "Bearer " + authentificationService.authentification()),
                UserResponse.class,
                () -> {
                    throw new UsernameNotFoundException("Inconnu!");
                }
        );
        return User
                .withUsername(response.getLogin())
                .password(response.getPassword())
                .roles(response.getRoles())
                .build();
    }
}
