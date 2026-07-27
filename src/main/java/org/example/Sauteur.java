package org.example;

public interface Sauteur {
    int getHauteurSaut();

    // méthode concrète (trait)
    default void sauter() {
        System.out.println("Saut à " + getHauteurSaut() + " mètres de hauteur!");
    }


}

