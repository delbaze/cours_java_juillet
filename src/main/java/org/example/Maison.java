package org.example;

public final class Maison extends Habitation {
    private final double surfaceJardin;

    public Maison(double surface, int nombrePieces, double surfaceJardin) {
        super(surface, nombrePieces);
        this.surfaceJardin = surfaceJardin;
    }

    @Override
    public double calculerValeur() {
        return surface * 2000 + surfaceJardin * 300;
    }
}
