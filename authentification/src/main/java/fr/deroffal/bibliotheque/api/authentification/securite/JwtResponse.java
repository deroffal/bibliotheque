package fr.deroffal.bibliotheque.api.authentification.securite;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

    private final String jwttoken;
}
