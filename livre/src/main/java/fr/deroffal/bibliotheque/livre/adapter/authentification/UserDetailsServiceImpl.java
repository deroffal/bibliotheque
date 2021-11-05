package fr.deroffal.bibliotheque.livre.adapter.authentification;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsClient userDetailsClient;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return Try.of(() -> userDetailsClient.loadUserByUsername(username))
            .map(userDetailsMapper::toUserDetails)
            .getOrElseThrow(()-> new UsernameNotFoundException(username));
    }

}
