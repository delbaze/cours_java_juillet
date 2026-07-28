package org.example;

public class AnnonceNotFoundException extends RuntimeException {
    public AnnonceNotFoundException(long id) {
        super("Annonce introuvable : " + id);
    }
}
