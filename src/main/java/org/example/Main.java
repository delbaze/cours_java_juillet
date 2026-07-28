package org.example;

import org.example.Produit.Produit;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var agence = new Agence("ImmoJava Nice");

        agence.ajouter(new Annonce.Builder()
                .avecId(1L).avecTitre("Studio centre-ville").avecPrix(120000.0)
                .avecBien(new Appartement(25, 1, 4))
                .avecStatut(StatutAnnonce.BROUILLON)
                .build());

        agence.ajouter(new Annonce.Builder()
                .avecId(2L).avecTitre("Maison avec jardin").avecPrix(380000.0)
                .avecBien(new Maison(110, 4, 200))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());

        for (var entry : agence.grouperParStatut().entrySet()) {
            System.out.println(entry.getKey().decrire() + " : " + entry.getValue().size() + " annonce(s)");
        }

        System.out.println("Publiables : " + agence.annoncesPubliables().size());


        Function<Produit, String> extraireNom = produit -> produit.getNom();

        Predicate<Produit> enStock = produit -> produit.getQuantite() > 0;
        Predicate<Produit> prixAbordable = produit -> produit.getPrix() < 50;

        Predicate<Produit> bonneAffaire = enStock.and(prixAbordable);
        Predicate<Produit> cherOuRupture = enStock.negate().or(prixAbordable.negate());

        Consumer<Produit> afficher = p -> System.out.println(p.getNom());
        Consumer<Produit> logger = p -> System.out.println("[LOG] " + p);

        Consumer<Produit> afficherEtLogger = afficher.andThen(logger);

//        produits.forEach(afficherEtLogger);

        Supplier<List<Produit>> listeVide = ArrayList::new;
        Supplier<LocalDateTime> maintenant = LocalDateTime::now;
        LocalDateTime date = maintenant.get();
        System.out.println("Maintenant : " + date);


        LocalDateTime date2 = maintenant.get();
        System.out.println("Maintenant : " + date2);

//        Supplier<Annonce> annonceParDefaut = ()  -> new Annonce();
//
//        Annonce a = annonceParDefaut.get();

        BiFunction<Double, Double, Double> calculerPrixAuM2 = (prix, surface) -> prix / surface;


        // Méthode statique (Classe::methodestatique)
        // Lambda
        Function<String, Integer> parser = s -> Integer.parseInt(s);
        // reference de méthode
        Function<String, Integer> parser2 = Integer::parseInt;

        // méthode d'instane sur un objet (instance::methode);
        String prefixe = "REF-";
        Function<String, String> ajouterPrefixe = prefixe::concat;

        // Méthode d'instance sur un type (Classe::methodeInstance)
        Function<String, String> majuscules = String::toUpperCase;
        BiFunction<String, String, Boolean> contient = String::contains;

        //Constructeur (Classe::new)
        Supplier<ArrayList<Produit>> creerListe = ArrayList::new;

        double seuilPrix = 40.0; // effectivement finale si elle ne change pas après
        Predicate<Produit> sousLeSeuil = p -> p.getPrix() < seuilPrix;

//        seuilPrix = 50.0;

        UnaryOperator<String> enMajuscules = s -> s.toUpperCase();
        System.out.println(enMajuscules.apply("coucou"));

        UnaryOperator<Double> remiseCinqPourcent = prix -> prix * 0.95;
        List<String> villes = new ArrayList<>(List.of("nice", "cannes", "antibes"));
        villes.replaceAll(s -> s.toUpperCase());

        BinaryOperator<Integer> addition = (a, b) -> a + b;
        System.out.println(addition.apply(11, 11));

        List<Double> prixMetresCarres = List.of(3500.0, 5200.0);
        double somme = prixMetresCarres.stream().reduce(0.0, (a, b) -> a + b);


        // composition de fonction andThen et compose

        Function<Double, Double> appliquerTva = prix -> prix * 1.20;
        Function<Double, Double> appliquerRemise = prix -> prix * 0.9;

        // andThen: f.andThen(g) = g(f(x)) // f d'abord puis g
        Function<Double, Double> tvaEnsuiteRemise = appliquerTva.andThen(appliquerRemise);
        tvaEnsuiteRemise.apply(100.0); // (100 * 1.20) * 0.9

        // compose f.compose(g) = f(g(x)) g d'abord et f ensuite
        Function<Double, Double> remiseEnsuiteTva = appliquerTva.compose(appliquerRemise);

        remiseEnsuiteTva.apply(100.0); // (100 * 0..9) * 1.20

//        Comparator<Produit> comparateur = (p1, p2) ->  {
//            int parPrix = Double.compare(p1.getPrix(), p2.getPrix());
//            if  (parPrix != 0) return parPrix;
//            return p1.getNom().compareTo(p2.getNom());
//        };

        var produits = creerListe.get();
        // ici je pourrais ajouter des produits, puis....
        Comparator<Produit> parPrixPuisNom = Comparator.comparingDouble(Produit::getPrix).thenComparing(Produit::getNom);
        produits.sort(parPrixPuisNom.reversed());
        produits.sort(Comparator.comparingDouble(Produit::getPrix).reversed());

        // nullsFirst et nullsLast
        Comparator<Produit> parCategorieAvecNullEnDernier = Comparator.comparing(Produit::getCategorie, Comparator.nullsLast(Comparator.naturalOrder()));
//        Function<String, byte[]> lireFichier = chemin -> Files.readAllBytes(Path.of(""));
//        lireFichier.apply("arr");

        Function<String, byte[]> lireFichier = chemin -> {
            try {
                return Files.readAllBytes(Path.of(""));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };

        List<Supplier<Integer>> suppliers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int copie = i; // variable locale recréée à chaque itération, effectivement finale
            suppliers.add(() -> copie);
        }

        suppliers.forEach(s -> System.out.println(s.get()));

//        for (Produit p : produits) {
//            // p est effectivement finale à l'interieur de cette itération
////            consumer
//        }
        TriFunction<String, Double, Integer, Produit> creerProduit = (nom, prix, quantite) -> new Produit(nom, prix, quantite);

        Produit p = creerProduit.apply("Clavier", 49.00, 100);

//        Function<Integer, Integer> factorielle = n -> n <= 1 ? 1 : n * factorielle.apply(n - 1);
//        Function<Integer, Integer> factorielle = new Function[1];
//        factorielle[0] = n -> n <=1 ? 1 : n * factorielle[0].apply(n - 1);
//
//        factorielle.apply(5);
    }
    public static int factorielle(int n) {
        return n <= 1 ? 1 : n * factorielle(n - 1);
    }
}