package org.example;

public abstract class BienImmobilier {
    protected double surface;

    public BienImmobilier(double surface) {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit etre positive");
        }
        this.surface = surface;
    }

    public abstract double calculerValeur();


    public double getSurface() {
        return surface;
    }
}

