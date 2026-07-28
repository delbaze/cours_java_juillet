package org.example;

import org.example.Produit.Produit;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Annonce> annonces = List.of(
                new Annonce.Builder()
                        .avecId(1L).avecTitre("Studio centre").avecPrix(180000.0)
                        .avecVille("Nice").avecBien(new Appartement(25, 1, 4))
                        .build(),
                new Annonce.Builder()
                        .avecId(2L).avecTitre("Appartement mer").avecPrix(320000.0)
                        .avecVille("Nice").avecBien(new Appartement(65, 3, 2))
                        .build(),
                new Annonce.Builder()
                        .avecId(3L).avecTitre("Maison jardin").avecPrix(280000.0)
                        .avecVille("Antibes").avecBien(new Maison(90, 4, 200))
                        .build()
        );

        Predicate<Annonce> prixOk = a -> a.getPrix() < 300000;
        Predicate<Annonce> surfaceOk = a -> a.getBien().getSurface() > 50;
        List<Annonce> resultat = AnnonceUtils.filtrer(annonces, prixOk.and(surfaceOk));
        resultat.forEach(a -> System.out.println(a.getTitre()));

        Comparator<Annonce> parVillePuisPrix = Comparator
                .comparing(Annonce::getVille)
                .thenComparing(Annonce::getPrix);
        AnnonceUtils.trier(annonces, parVillePuisPrix)
                .forEach(a -> System.out.println(a.getVille() + " - " + a.getPrix()));

        EvaluateurAnnonce<String> description =
                a -> a.getTitre() + " (" + a.getVille() + ", " + a.getBien().getSurface() + " m2)";
        EvaluateurAnnonce<Double> prixAuM2 =
                a -> a.getPrix() / a.getBien().getSurface();

        System.out.println(description.evaluer(annonces.get(0)));
        System.out.println(prixAuM2.evaluer(annonces.get(0)));

        List<Supplier<Annonce>> suppliers = new ArrayList<>();
        for (int i = 0; i < annonces.size(); i++) {
            int index = i; // variable intermediaire obligatoire, effectivement finale
            suppliers.add(() -> annonces.get(index));
        }
        suppliers.forEach(s -> System.out.println(s.get().getTitre()));

        Supplier<ArrayList<Produit>> creerListe = ArrayList::new;
        var produits = creerListe.get();

        // depuis une collection
        Stream<Produit> stream = produits.stream();
        stream.forEach(System.out::println);
//        Stream<Produit> stream2 = produits.stream();
//        System.out.println(stream2.count());


        // depuis un tableau
        Stream<String> stream2 = Arrays.stream(new String[]{"a", "b", "c"});

        // depuis des valeurs
        Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);

        // stream infini avec limite
        Stream<Double> aleatoires = Stream.generate(Math::random).limit(10);

        // stream itératif
        Stream<Integer> pairs = Stream.iterate(0, i -> i + 2).limit(100);

        //Itératif avec prédicat d'arrêt
        Stream<Integer> pairesSous100 = Stream.iterate(0, n -> n < 100, n -> n + 2);

        Stream<Produit> streamP = produits.stream().filter(p -> {
            System.out.println("Filtrage de " + p.getNom());
            return p.getPrix() < 50;
        }).map(p -> {
            System.out.println("Transformation de " + p.getNom());
            p.nom = p.getNom().toUpperCase();
            return p;
        });
        System.out.println("Rien ne s'esst encore affiché ci -dessus");

        List<Produit> resultat2 = streamP.collect(Collectors.toList());
        System.out.println(resultat2);

        produits.stream()
                .filter(p -> p.getPrix() > 200000)
                .filter(p -> p.quantite > 19)
                .toList();
//                .collect(Collectors.toList());

        produits.stream()
                .filter(p -> p.getPrix() > 200000 && p.quantite > 19)
//                .filter(p -> p.quantite > 19)
                .toList();

        Predicate<Produit> pasCher = p -> p.getPrix() < 100000;
        Predicate<Produit> bonneQuantite = p -> p.quantite > 100;

        produits.stream().filter(pasCher.and((bonneQuantite))).toList();

        Stream<String> noms = produits.stream().map(Produit::getNom);
        DoubleStream prix = produits.stream().mapToDouble(Produit::getPrix);

//        flatMap transforme chaque élément en un stream et aplatit le tout
//        List<Produit> tousLesProduitsCommandes = commandes.stream().flatMap(commande -> commande.getLignes().stream()).toList();

        //distinct() : equals()


        produits.stream().sorted(Comparator.comparingDouble(Produit::getPrix).reversed().thenComparing(Produit::getNom));

        // limit et skip
        produits.stream().sorted(Comparator.comparing(Produit::getNom)).skip(10).limit(10);

        //peek (attention : ne doit servir qu'au débogage ou à la journalisation)

        produits.stream()
                .filter(p -> p.getPrix() > 200000)
                .peek(p -> System.out.println("Trouvé : " + p.getNom()))
                .map(Produit::getPrix)
                .forEach(System.out::println);


        List<String> noms2 = produits.stream().map(Produit::getNom).collect(Collectors.toList()); // List
        List<String> nomsImmuables = produits.stream().map(Produit::getNom).toList(); // List immuable
        Set<String> categories = produits.stream().map(Produit::getCategorie).collect(Collectors.toSet());
        Map<Long, Produit> parId = produits.stream().collect(Collectors.toMap(Produit::getId, p -> p));
        Map<String, List<Produit>> parCategorie = produits.stream().collect(Collectors.groupingBy(Produit::getCategorie));

        Map<String, Long> nbProduitsParCategorie = produits.stream().collect(Collectors.groupingBy(Produit::getCategorie, Collectors.counting()));

        Map<Boolean, List<Produit>> enStockOuNon = produits.stream().collect(Collectors.partitioningBy(p -> p.getQuantite()  > 0));

        List<Produit> enStock = enStockOuNon.get(true);

        Optional<Produit> premier = produits.stream().filter(p -> p.getPrix() > 200000).findFirst();

        boolean ilYaDesProduitsChers = produits.stream().anyMatch(p -> p.getPrix() > 200000);
        boolean tousEnStock = produits.stream().allMatch(p -> p.getPrix() > 200000);

        OptionalDouble somme = produits.stream().mapToDouble(Produit::getPrix).reduce(Double::sum);

        double total = produits.stream().mapToDouble(Produit::getPrix).reduce(0.0, Double::sum);
        Optional<Produit> leMoinCher = produits.stream().min(Comparator.comparingDouble(Produit::getPrix));
        leMoinCher.ifPresent(p -> System.out.println("Meilleur prix : " + p.getNom()));


        Optional<Produit> resultat3 = produits.parallelStream()
                .peek(p -> System.out.println("Examen de " + p.getNom()))
                .filter(p -> p.getPrix() > 500)
                .findFirst();

        DoubleSummaryStatistics stats = produits.stream().mapToDouble(Produit::getPrix).summaryStatistics();
//        DoubleSummaryStatistics stats = produits.stream().collect(Collectors.summarizingDouble(Produit::getPrix));


        System.out.println("Nombre : " + stats.getCount());
        System.out.println("Minimum : " + stats.getMin());
        System.out.println("Maximum : " + stats.getMax());
        System.out.println("Moyenne  " + stats.getAverage());
        System.out.println("Total : " + stats.getSum());

//        Stream<Produit> tous = Stream.concat(produitsPromo.stream(), produitsStandards.stream())

        IntStream.range(0, 10); // 0 à 9
        IntStream.rangeClosed(0, 10) ;// 0 à 10 inclus

        List<Integer> liste = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        System.out.println(liste);

//        // Antipattern : accumulation manuelle non thread safe en parallèle
//        List<String> resultat4 = new ArrayList<>();
//        produits.parallelStream()
//                .map(Produit::getNom)
//                .forEach(resultat::add); // plusieurs threads qui modifient la même liste en meme temps (comportement imprévisible

        // Correct : on laisse le Collector gérer l'accumulation Thread safe
        List<String> resultatCorrect = produits.parallelStream().map(Produit::getNom).collect(Collectors.toList());
    }

    public static int factorielle(int n) {
        return n <= 1 ? 1 : n * factorielle(n - 1);
    }
}