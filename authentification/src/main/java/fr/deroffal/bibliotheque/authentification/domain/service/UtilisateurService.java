package fr.deroffal.bibliotheque.authentification.domain.service;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UserRepositoryAdapter userRepositoryAdapter;

    public Optional<Utilisateur> getByUsername(final String username) {
        return userRepositoryAdapter.findByUsername(username);
    }

    public boolean existsByLogin(final String login) {
        return userRepositoryAdapter.existsByUsername(login);
    }

    public Utilisateur create(final Utilisateur utilisateur) {
        return userRepositoryAdapter.create(utilisateur);
    }
}
