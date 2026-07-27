package org.example;

public class VoitureElectrique extends Vehicule{


    public VoitureElectrique(String marque) {
        super(marque);
    }

    @Override
    protected void demarrerMoteur() {
        System.out.println("2. Appuyer sur Start");
    }

    @Override
    protected void verifierSystemes() {
        System.out.println("Vérification de la charge de la batterie");
    }

    @Override
    protected void hookAvertissement(){
        System.out.println("Mode éco activé par défaut");
    }
}
