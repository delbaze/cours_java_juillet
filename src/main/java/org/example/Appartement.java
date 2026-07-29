package org.example;

public record Appartement(double surface, int nombrePieces, int etage) implements BienImmobilier {
//    private final int etage;

//    public Appartement(double surface, int nombrePieces, int etage) {
//        super(surface, nombrePieces);
//        this.etage = etage;
//    }

//    public int getEtage() {
//        return etage;
//    }
//    @Override
//    public double calculerValeur() {
//        return surface * 2500 * (1 + etage * 0.01);
//    }

    public Appartement {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit etre positive");
        }
    }

    @Override
    public double calculerValeur() {
        return surface * 2500 * (1 + etage * 0.01);
    }

    public Appartement avecEtage(int nouvelEtage) {
        return new Appartement(surface, nombrePieces, nouvelEtage);
    }
}
