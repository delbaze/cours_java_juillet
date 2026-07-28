package org.example;

@FunctionalInterface
public interface EvaluateurAnnonce<R> {
    R evaluer(Annonce annonce);
}