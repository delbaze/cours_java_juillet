package org.example;

public record Terrain(double surface, boolean constructible) implements BienImmobilier {
    public Terrain {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit etre positive");
        }
    }

    @Override
    public double calculerValeur() {
        return constructible ? surface * 800 : surface * 200;
    }

    @Override
    public int nombrePieces() {
        return 0;
    }
}