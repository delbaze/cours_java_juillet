package org.example.Produit;

import java.util.List;

public record Produit2(String nom, double prix, int stock) {
    public Produit2 {
        if (prix < 0) throw new IllegalArgumentException("Prix invalide");
        if (stock < 0) throw new IllegalArgumentException("Stock invalide");
//        demo = List.copyOf(demo); // liste immuable, indépendante de l'originale, donc on ne pourra pas appeler add() par exemple dessus
    }
    //constructeur secondaire qui créé un produit en rupture de stock
    public Produit2(String nom, double prix) {
        this(nom, prix, 0);
    }

    public static Produit2 enRupture(String nom, double prix) {
        return new Produit2(nom, prix, 0);
    }
    public static Produit2 avecStockInitial(String nom, double prix, int stock) {
        return new Produit2(nom, prix, stock);
    }
}
