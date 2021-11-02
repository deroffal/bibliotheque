package fr.deroffal.bibliotheque.livre.adapter.authentification;

import fr.deroffal.bibliotheque.securite.details.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${bibliotheque.authentification.url}")
    private String authentificationUrl;

    //TODO
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final ResponseEntity<JwtUserDetails> entity = restTemplate.getForEntity(authentificationUrl + "/public/user/" + username, JwtUserDetails.class);
        return entity.getBody();
    }
}
