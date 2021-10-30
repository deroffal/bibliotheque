package fr.deroffal.bibliotheque.authentification.securite;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

    private final String jwttoken;
}
