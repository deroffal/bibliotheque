package fr.deroffal.bibliotheque.authentification.utilisateur;

public interface PasswordEncodeAdapter {

    String encodePassword(final CharSequence password);
}
