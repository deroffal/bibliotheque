package fr.deroffal.bibliotheque.authentification.securite;

import fr.deroffal.bibliotheque.authentification.utilisateur.PasswordEncodeAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodeAdapterImpl implements PasswordEncodeAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(final CharSequence password) {
        return passwordEncoder.encode(password);
    }
}
