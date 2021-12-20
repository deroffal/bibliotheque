package fr.deroffal.bibliotheque.authentification.application;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import fr.deroffal.bibliotheque.authentification.domain.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreationUtilisateurService {

    private final UtilisateurService userService;

    public Utilisateur create(final Utilisateur user) {
        final String login = user.username();
        if (userService.existsByLogin(login)) {
            throw new UserAlreadyExistsException(login);
        }
        return userService.create(user);
    }
}
