package fr.deroffal.bibliotheque.securite.auth;

class AuthentificationException extends RuntimeException {
    public AuthentificationException(final Throwable cause) {
        super(cause);
    }
}
