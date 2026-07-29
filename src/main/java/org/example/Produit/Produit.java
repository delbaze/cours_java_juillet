package org.example.Produit;

import org.example.ResultatCommande;

import java.util.Comparator;
import java.util.List;

public class Produit implements Comparable<Produit> {

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

    public List<String> produitsChersFormates(List<Produit> produits) {
        record ProduitFormate(String nom, String prixAffiche) {} // record local

        return produits.stream()
                .filter(p -> p.prix > 100)
                .map(p -> new ProduitFormate(p.nom, "%.2f €".formatted(p.prix)))
                .map(pf -> pf.nom() + " : " + pf.prixAffiche())
                .toList();

    }

    public List<String> demoProduitLocal(List<Produit> produits) {
        record ProduitFormate(String nom, String prixAffiche) {} // record local

        return produits.stream()
                .map(p -> new ProduitFormate(p.nom, "%.2f €".formatted(p.prix)))
                .filter(pf -> pf.nom().startsWith("A"))
                .sorted(Comparator.comparing(ProduitFormate::prixAffiche))
                .map(pf -> pf.nom() + " : " + pf.prixAffiche())
                .toList();

    }
    public String traiter(ResultatCommande resultat) {
        return switch (resultat) {
            case ResultatCommande.Success(String numero, double montant) -> "Commande " + numero + " validée pour " + montant  + " €";
            case ResultatCommande.EchecStock(String produit) -> "Rupture de stock  " + produit;
            case ResultatCommande.EchecPaiement(String raison) -> "Paiement refusé " + raison;


        };
    }

    @Override
    public int compareTo(Produit autre) {
        return Double.compare(this.prix, autre.prix);
    }
}
