package org.example;

public record Maison(double surface, int nombrePieces, double surfaceJardin) implements BienImmobilier {
    public Maison {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit etre positive");
        }
    }

    @Override
    public double calculerValeur() {
        return surface * 2000 + surfaceJardin * 300;
    }
}


//public final class Maison extends Habitation {
//    private final double surfaceJardin;
//
//    public Maison(double surface, int nombrePieces, double surfaceJardin) {
//        super(surface, nombrePieces);
//        this.surfaceJardin = surfaceJardin;
//    }
//
//    @Override
//    public double calculerValeur() {
//        return surface * 2000 + surfaceJardin * 300;
//    }
//}
