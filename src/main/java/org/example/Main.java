package org.example;

import org.example.Produit.Produit;
import org.example.Produit.Produit2;

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
                .avecVille("Antibes").avecBien(new Terrain(500, true))
                .avecStatut(StatutAnnonce.VENDUE)
                .build());

        List<Agence> agences = List.of(agenceNice, agenceAntibes);

        agences.stream()
                .flatMap(a -> a.getAnnonces().stream())
                .forEach(a -> System.out.println(a.getTitre() + " : " + BienService.description(a.getBien())));

        List<Annonce> toutesLesAnnonces = agences.stream()
                .flatMap(a -> a.getAnnonces().stream())
                .toList();

        List<String> titresAbordablesPublies = toutesLesAnnonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.PUBLIEE)
                .filter(a -> a.getPrix() < 400000)
                .map(Annonce::getTitre)
                .toList();
        System.out.println(titresAbordablesPublies);

        Map<String, Long> nbParVille = toutesLesAnnonces.stream()
                .collect(Collectors.groupingBy(Annonce::getVille, Collectors.counting()));
        System.out.println(nbParVille);

        Map<String, Double> prixMoyenParVille = toutesLesAnnonces.stream()
                .collect(Collectors.groupingBy(Annonce::getVille, Collectors.averagingDouble(Annonce::getPrix)));
        System.out.println(prixMoyenParVille);

        toutesLesAnnonces.stream()
                .sorted(Comparator.comparingDouble(Annonce::getPrix).reversed()
                        .thenComparing(Annonce::getTitre))
                .limit(3)
                .forEach(a -> System.out.println(a.getTitre() + " - " + a.getPrix()));

        DoubleSummaryStatistics stats = toutesLesAnnonces.stream()
                .collect(Collectors.summarizingDouble(Annonce::getPrix));
        System.out.println("Min " + stats.getMin() + " Max " + stats.getMax()
                + " Moyenne " + stats.getAverage() + " Total " + stats.getSum());

        Optional<Annonce> grandeSurface = toutesLesAnnonces.stream()
                .filter(a -> a.getBien().surface() > 100)
                .findFirst();
        grandeSurface.ifPresentOrElse(
                a -> System.out.println("Trouvee : " + a.getTitre()),
                () -> System.out.println("Aucune annonce avec une surface superieure a 100")
        );

        boolean yADesCheres = toutesLesAnnonces.stream().anyMatch(a -> a.getPrix() > 500000);
        boolean toutesPubliees = toutesLesAnnonces.stream().allMatch(a -> a.getStatut() == StatutAnnonce.PUBLIEE);
        System.out.println("Au moins une chere : " + yADesCheres);
        System.out.println("Toutes publiees : " + toutesPubliees);


        Repository<Annonce> repository = new Repository<>(() -> null);
        toutesLesAnnonces.forEach(repository::ajouter); // toutesLesAnnonces deja construite comme dans l'exercice streams

        Optional<Annonce> studio = repository.trouver(a -> a.getTitre().equals("Studio centre"));
        studio.ifPresent(a -> System.out.println("Trouve : " + a.getTitre()));

        System.out.println("Nombre total dans le repository : " + repository.tous().size());


        Annonce laPlusChere = Utilitaires.maximum(toutesLesAnnonces);
        System.out.println("La plus chere : " + laPlusChere.getTitre());

        // wildcard extends, testee avec List<Appartement> precisement
        List<Appartement> appartements = List.of(
                new Appartement(25, 1, 4),
                new Appartement(65, 3, 2)
        );
        System.out.println("Valeur totale appartements : " + Utilitaires.sommeValeurs(appartements));

        // Wildcard super
        List<Object> archive = new ArrayList<>();
        Utilitaires.archiverTitres(archive, toutesLesAnnonces);
        System.out.println(archive);

        Repository<Annonce> repositoryAvecFabrique = new Repository<>(() ->
                new Annonce.Builder().avecId(0L).avecTitre("Vide").avecPrix(1.0)
                        .avecVille("").avecBien(new Terrain(1, false)).build()
        );
        Annonce vide = repositoryAvecFabrique.creerVide();
        System.out.println(vide.getTitre());

    }

}