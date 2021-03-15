package fr.deroffal.bibliotheque.webapp.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private String login;
    private String password;
    private String[] roles;
}
