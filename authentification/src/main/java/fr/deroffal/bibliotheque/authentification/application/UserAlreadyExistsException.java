package fr.deroffal.bibliotheque.authentification.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistsException extends RuntimeException {

    private final String login;
}
