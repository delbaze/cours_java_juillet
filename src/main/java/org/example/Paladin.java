package org.example;

public class Paladin implements Sauteur, Soigneur, Loggable{
    @Override
    public int getHauteurSaut() {
        return 2;
    }

    public void attaquer() {
        log("Le paladin attaaque avec son marteau");
    }
}
