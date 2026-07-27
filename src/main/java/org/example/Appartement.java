package org.example;

public final class Appartement extends Habitation {
    private final int etage;

    public Appartement(double surface, int nombrePieces, int etage) {
        super(surface, nombrePieces);
        this.etage = etage;
    }

    public int getEtage() {
        return etage;
    }
    @Override
    public double calculerValeur() {
        return surface * 2500 * (1 + etage * 0.01);
    }

    @Override
    public Appartement copier() {
        return new Appartement(surface, nombrePieces, etage);
    }
}
