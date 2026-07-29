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
        var agenceNice = new Agence("ImmoJava Nice");
        agenceNice.ajouter(new Annonce.Builder()
                .avecId(1L).avecTitre("Studio centre").avecPrix(180000.0)
                .avecVille("Nice").avecBien(new Appartement(25, 1, 4))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());
        agenceNice.ajouter(new Annonce.Builder()
                .avecId(2L).avecTitre("Appartement mer").avecPrix(320000.0)
                .avecVille("Nice").avecBien(new Appartement(65, 3, 2))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());
        agenceNice.ajouter(new Annonce.Builder()
                .avecId(3L).avecTitre("Duplex vue mer").avecPrix(520000.0)
                .avecVille("Nice").avecBien(new Appartement(110, 5, 6))
                .avecStatut(StatutAnnonce.BROUILLON)
                .build());

        var agenceAntibes = new Agence("ImmoJava Antibes");
        agenceAntibes.ajouter(new Annonce.Builder()
                .avecId(4L).avecTitre("Maison jardin").avecPrix(280000.0)
                .avecVille("Antibes").avecBien(new Maison(90, 4, 200))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());
        agenceAntibes.ajouter(new Annonce.Builder()
                .avecId(5L).avecTitre("Villa piscine").avecPrix(650000.0)
                .avecVille("Antibes").avecBien(new Maison(150, 6, 400))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());
        agenceAntibes.ajouter(new Annonce.Builder()
                .avecId(6L).avecTitre("Terrain").avecPrix(120000.0)
                .avecVille("Antibes").avecBien(new Maison(20, 1, 500))
                .avecStatut(StatutAnnonce.VENDUE)
                .build());

        List<Agence> agences = List.of(agenceNice, agenceAntibes);

        // Etape 2 — flatMap
        List<Annonce> toutesLesAnnonces = agences.stream()
                .flatMap(a -> a.getAnnonces().stream())
                .toList();

        // Etape 3 — filter puis collect
        List<String> titresAbordablesPublies = toutesLesAnnonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.PUBLIEE)
                .filter(a -> a.getPrix() < 400000)
                .map(Annonce::getTitre)
                .toList();
        System.out.println(titresAbordablesPublies);

        // Etape 4 — groupement avec comptage
        Map<String, Long> nbParVille = toutesLesAnnonces.stream()
                .collect(Collectors.groupingBy(Annonce::getVille, Collectors.counting()));
        System.out.println(nbParVille);

        // Etape 5 — groupement avec moyenne
        Map<String, Double> prixMoyenParVille = toutesLesAnnonces.stream()
                .collect(Collectors.groupingBy(Annonce::getVille, Collectors.averagingDouble(Annonce::getPrix)));
        System.out.println(prixMoyenParVille);

        // Etape 6 — tri puis limit
        toutesLesAnnonces.stream()
                .sorted(Comparator.comparingDouble(Annonce::getPrix).reversed()
                        .thenComparing(Annonce::getTitre))
                .limit(3)
                .forEach(a -> System.out.println(a.getTitre() + " - " + a.getPrix()));

        // Etape 7 — statistiques
        DoubleSummaryStatistics stats = toutesLesAnnonces.stream()
                .collect(Collectors.summarizingDouble(Annonce::getPrix));
        System.out.println("Min " + stats.getMin() + " Max " + stats.getMax()
                + " Moyenne " + stats.getAverage() + " Total " + stats.getSum());

        // Etape 8 — findFirst et ifPresentOrElse
        Optional<Annonce> grandeSurface = toutesLesAnnonces.stream()
                .filter(a -> a.getBien().getSurface() > 100)
                .findFirst();
        grandeSurface.ifPresentOrElse(
                a -> System.out.println("Trouvee : " + a.getTitre()),
                () -> System.out.println("Aucune annonce avec une surface superieure a 100")
        );

        // Etape 9 — anyMatch / allMatch
        boolean yADesCheres = toutesLesAnnonces.stream().anyMatch(a -> a.getPrix() > 500000);
        boolean toutesPubliees = toutesLesAnnonces.stream().allMatch(a -> a.getStatut() == StatutAnnonce.PUBLIEE);
        System.out.println("Au moins une chere : " + yADesCheres);
        System.out.println("Toutes publiees : " + toutesPubliees);
    }

}