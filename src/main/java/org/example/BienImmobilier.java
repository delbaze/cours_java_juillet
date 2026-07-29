package org.example;

public sealed interface BienImmobilier extends Affichable, Journalisable permits Appartement, Maison, Terrain {
    double surface();
    double calculerValeur();
    int nombrePieces();
    default boolean estGrande() {
        return nombrePieces() >= 5;
    }

    @Override
    default String resume() {
        return Affichable.super.resume() + " / " + Journalisable.super.resume();
    }
//
//    public BienImmobilier(double surface) {
//        if (surface <= 0) {
//            throw new IllegalArgumentException("La surface doit etre positive");
//        }
//        this.surface = surface;
//    }



//    public double getSurface() {
//        return surface;
//    }
}

