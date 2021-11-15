package fr.deroffal.bibliotheque.securite.auth

class AuthentificationException extends RuntimeException {
    AuthentificationException(final Throwable cause) {
        super(cause)
    }
}
