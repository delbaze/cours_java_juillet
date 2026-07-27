package org.example;

public class VoitureThermique  extends Vehicule{
    public VoitureThermique(String marque) {
        super(marque);
    }

    @Override
    protected void demarrerMoteur() {
        System.out.println("2. Tourner la clé de contact");
    }

    @Override
    protected void verifierSystemes() {
        System.out.println("3. Vérification de la pression d'huile etc...");
    }
}
