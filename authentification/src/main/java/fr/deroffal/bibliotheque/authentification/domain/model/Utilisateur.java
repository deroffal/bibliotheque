package fr.deroffal.bibliotheque.authentification.domain.model;

import java.util.List;

public record Utilisateur(Long id, String username, String password, List<String> roles) {

}
