package org.example;

import org.example.Produit.Produit;
import org.example.Produit.Produit2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// T (Type) E (Element) K/V (Key/Value), R (Result)
public class Demo<A, B> {
    private final A premier;
    private final B second;

    // erreur de compilation, A n'existe plus à l'exécution
//    public boolean estInstance(Object o) {
//        return o instanceof A;
//    }

//    public T creerVide() {
//        return new T(); // on ne pas instancer un type
//    }

//    public T[] creerTableau(int taille) {
//        return new T[taille]; // erreur de compilation
//    }

    public Demo(A premier, B second) {
        this.premier = premier;
        this.second = second;
    }

    public A getPremier() {
        return premier;
    }
    public B getSecond() {
        return second;
    }

    public static <T> T premierNonNull(T valeur1, T valeur2) {
        return valeur1 != null ? valeur1 : valeur2;
    }

    public static <T extends Comparable<T> & Serializable> T maximum(List<T> elements) {
        if (elements == null || elements.isEmpty()) {
               throw new IllegalArgumentException("La liste ne peut pas êtret vide ou nulle");
        }
        T max = elements.get(0);
        for (T element : elements) {
            if (element.compareTo(max) > 0) {
                max = element;

            }

        }
        return max;
    }
//
//    public static <T extends Comparable<T>> Optional<T> maximum(List<T> elements) {
//        if (elements == null || elements.isEmpty()) {
//            return Optional.empty();
    ////            throw new IllegalArgumentException("La liste ne peut pas êtret vide ou nulle");
//        }
//        T max = elements.get(0);
//        for (T element : elements) {
//            if (element.compareTo(max) > 0) {
//                max = element;
//
//            }
//
//        }
//        return Optional.of(max);
//    }
    public static void afficherTout(List<Object> liste) {
        for (Object o : liste){
            System.out.println(o);
        }
    }

    public static double sommePrix(List<? extends Produit> produits) {
        double total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }
        return total;
//        produits.add()
    }
//
//    public static void ajouterProduitsPromo(List<? super ProduitPromo> destination) {
//        destination.add(new ProduitPromo("Clavier", 39.90));
//    }

    // wildcard non borné
    public static void afficherTaille(List<?> liste) {
        System.out.println("Taille : " + liste.size());
    }


    public static void main(String[] args) {

        Demo<String, Double> ligne = new Demo<>("Clavier", 40.00);
        String nom = premierNonNull(null, "Clavier");
        Produit clavier = new Produit("clavier", 30.00, 2, 1L);
        Produit souris = new Produit("souris", 10.00, 3, 2L);

        List<Produit> produits = new ArrayList<>(List.of(clavier, souris));
//        ajouterProduitsPromo(produits);
        Collections.sort(produits);
//        List<ProduitPromo> promos = List.of(....);
//        sommePrix(promos);
//        List<String> noms = List.of("clavier", "souris");
//        afficherTout(noms);
    }
}
