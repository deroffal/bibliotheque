package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Long id;

    private String login;

    private String password;

    private List<String> roles;
}
