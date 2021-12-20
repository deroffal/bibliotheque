package fr.deroffal.bibliotheque.authentification.application;

import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import fr.deroffal.bibliotheque.authentification.domain.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecuperationUtilisateurService {

    private final UtilisateurService userService;

    public Utilisateur getByLogin(final String login) {
        return userService.getByUsername(login).orElseThrow(() -> new UserNotFoundException(login));
    }
}
