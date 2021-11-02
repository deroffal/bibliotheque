package fr.deroffal.bibliotheque.authentification.domain.model;

import java.util.List;

public record UserDto(Long id, String username, String password, List<String> roles) {

}
