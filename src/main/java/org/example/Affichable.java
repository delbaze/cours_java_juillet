package org.example;

public interface Affichable {
    default String resume() {
        return "Bien de " + getClass().getSimpleName();
    }
}