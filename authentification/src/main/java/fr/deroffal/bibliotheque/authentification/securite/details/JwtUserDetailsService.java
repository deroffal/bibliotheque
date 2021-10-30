package fr.deroffal.bibliotheque.authentification.securite.details;

import static java.util.stream.Collectors.toMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final  Map<String, String> USERS = Stream.of(
            new String[]{"webapp", "$2a$10$VU4rLphKq5Jaw/pc3s2lCu1U5zO5V3d4qiiFSKvPEYZFeVxMDkPfy"},
            new String[]{"admin", "$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e"}
    ).collect(toMap(it -> it[0], it -> it[1]));

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return Optional.ofNullable(USERS.get(username))
                .map(password -> new JwtUserDetails(username, password))
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }
}
