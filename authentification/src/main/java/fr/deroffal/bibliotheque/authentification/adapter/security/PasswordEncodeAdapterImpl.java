package fr.deroffal.bibliotheque.authentification.adapter.security;

import fr.deroffal.bibliotheque.authentification.adapter.repository.mapping.PasswordEncodeAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PasswordEncodeAdapterImpl implements PasswordEncodeAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(final CharSequence password) {
        return passwordEncoder.encode(password);
    }
}
