package org.example;

abstract public class Vehicule {
    protected String marque;
    protected int niveauCarburant; // État ou variable partagée

    public Vehicule(String marque) {
        this.marque = marque;
        this.niveauCarburant = 100;
    }

    // méthode concrète (code partagé par tous les véhicules)
    public void faireLePlein() {
        this.niveauCarburant = 100;
        System.out.println("Plein effectué pour la " + marque);
    }

    // méthode abstraite : chaque véhicule démarre de façon différente
//    public abstract void demarrer();

    public final void demarrerVehicule(){
        deverrouiller();
        demarrerMoteur();
        verifierSystemes();
        hookAvertissement();
        partir();
    }
    // Etape commune à tous les véhicules
    private void deverrouiller(){
        System.out.println("1.Portières déverouillées");
    }

    // Étapes à implémenter obligatoirement par chaque véhicule
    protected abstract void demarrerMoteur();
    protected abstract void verifierSystemes();

    protected void hookAvertissement(){

    }

    private void partir()  {
        System.out.println("Le vehicule roule");
    }

}

interface Electrique {
    void rechargerBatterie(); // contrat pour ce qui se branche
}

interface Louable {
    void louer(String client); // contrat pour ce qui peut être loué
}