package fr.deroffal.bibliotheque.authentification.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private final Long id;

    private final String login;

    private final String password;

    private final List<String> roles;
}
