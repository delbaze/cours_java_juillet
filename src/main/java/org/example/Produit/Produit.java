package org.example.Produit;

public class Produit {

    public Long id;
    public String nom;
    public int quantite;
    public double prix;
    public String categorie;


    public Produit(String nom, double prix, int  quantite, Long id) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = "";
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrix() {
        return prix;
    }

    public String getCategorie() {

        return categorie;
    }

    public Long getId() {
        return id;
    }
}
