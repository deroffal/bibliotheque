package fr.deroffal.bibliotheque.authentification.domain.service;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;

public interface UserRepositoryAdapter {

    Optional<Utilisateur> findByUsername(final String username);

    boolean existsByUsername(final String username);

    Utilisateur create(final Utilisateur utilisateur);
}
