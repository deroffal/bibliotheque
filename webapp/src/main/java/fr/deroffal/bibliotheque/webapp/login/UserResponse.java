package fr.deroffal.bibliotheque.webapp.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(String login, String password, String[] roles) {

}
