package org.example;

//public abstract class Habitation extends BienImmobilier implements Affichable, Journalisable {
public abstract class Habitation implements Affichable, Journalisable {

    protected int nombrePieces;

    public Habitation(double surface, int nombrePieces) {
//        super(surface);
        this.nombrePieces = nombrePieces;
    }

    public boolean estGrande() {
        return nombrePieces >= 5;
    }

    @Override
    public String resume() {
        return Affichable.super.resume() + " / " + Journalisable.super.resume();
    }
}
