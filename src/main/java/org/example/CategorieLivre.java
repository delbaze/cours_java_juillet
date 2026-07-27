package org.example;

public enum CategorieLivre {
    ROMAN("Roman", 21),
    BD("Bande dessinée", 14),
    ESSAI("Essai", 21),
    JEUNESSE("Jeunesse", 14),
    MANGA("Manga", 7);

    private final String libelle;
    private final int dureeEmpruntJours;

    CategorieLivre(String libelle, int dureeEmpruntJours) {
        this.libelle = libelle;
        this.dureeEmpruntJours = dureeEmpruntJours;
    }

    public String getLibelle() {
        return libelle;
    }
    public int getDureeEmpruntJours() {
        return dureeEmpruntJours;
    }

    public double calculerFraisRetard(int joursRetard) {
        return joursRetard * (dureeEmpruntJours < 14 ? 0.50 : 0.20);
    }

    public static CategorieLivre fromLibelle(String libelle) {
        for (CategorieLivre categorie : values()) {
            if (categorie.libelle.equalsIgnoreCase(libelle)) {
                return categorie;
            }
        }
        throw new IllegalArgumentException("Le libelle n'existe pas : " + libelle);
    }
}
