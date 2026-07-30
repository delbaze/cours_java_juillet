package org.example;

import org.example.Produit.Produit;
import org.example.Produit.Produit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    public static void main(String[] args) throws IOException, InterruptedException {
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


        // exo collections
        TreeSet<Annonce> parPrix = new TreeSet<>(Comparator.comparingDouble(Annonce::getPrix));
        parPrix.addAll(toutesLesAnnonces);
        parPrix.forEach(a -> System.out.println(a.getTitre() + " - " + a.getPrix()));
        System.out.println("Moins chere : " + parPrix.first().getTitre());
        System.out.println("Plus chere : " + parPrix.last().getTitre());

        Map<String, Integer> compteParVille = new HashMap<>();
        for (Annonce a : toutesLesAnnonces) {
            compteParVille.merge(a.getVille(), 1, Integer::sum);
        }
        System.out.println(compteParVille);

        PriorityQueue<Annonce> file = new PriorityQueue<>(Comparator.comparingDouble(Annonce::getPrix));
        file.addAll(toutesLesAnnonces);
        while (!file.isEmpty()) {
            System.out.println(file.poll().getTitre());
        }

        List<Annonce> modifiable = new ArrayList<>(toutesLesAnnonces);
        List<Annonce> vue = Collections.unmodifiableList(modifiable);
        modifiable.add(toutesLesAnnonces.get(0)); // ajout sur la liste d'origine
        System.out.println("Taille vue apres modif de l'original : " + vue.size()); // a change, c'est une vue

        List<Annonce> copie = List.copyOf(modifiable);
        modifiable.add(toutesLesAnnonces.get(1));
        System.out.println("Taille copie apres modif de l'original : " + copie.size()); // inchangee, vraie copie

        List<Annonce> aNettoyer1 = new ArrayList<>(toutesLesAnnonces);
        aNettoyer1.removeIf(a -> a.getStatut() == StatutAnnonce.VENDUE);

        List<Annonce> aNettoyer2 = new ArrayList<>(toutesLesAnnonces);
        Iterator<Annonce> it = aNettoyer2.iterator();
        while (it.hasNext()) {
            Annonce a = it.next();
            if (a.getStatut() == StatutAnnonce.VENDUE) {
                it.remove();
            }
        }
        System.out.println(aNettoyer1.size() == aNettoyer2.size()); // true, meme resultat

        // ConcurrentModificationException volontaire
        List<Annonce> aCasser = new ArrayList<>(toutesLesAnnonces);
        try {
            for (Annonce a : aCasser) {
                if (a.getStatut() == StatutAnnonce.VENDUE) {
                    aCasser.remove(a); // leve ConcurrentModificationException
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Exception attendue : " + e.getClass().getSimpleName());
        }

        System.out.println("----Exercice Files----");
        AnnonceCsvService csvService = new AnnonceCsvService();
        Path fichierTemp = Files.createTempFile("immojava-", ".csv");

        csvService.exporterCsv(toutesLesAnnonces, fichierTemp);
        List<AnnonceImportee> reimportees = csvService.importerCsv(fichierTemp);
        System.out.println("Correspondance : " + (reimportees.size() == toutesLesAnnonces.size()));

        Files.writeString(fichierTemp, "Annonce cassee;PAS_UN_PRIX;Nice;APPARTEMENT\n", StandardOpenOption.APPEND);
        List<AnnonceImportee> apresErreur = csvService.importerCsv(fichierTemp);
        System.out.println("Toujours " + toutesLesAnnonces.size() + " lignes valides : " + (apresErreur.size() == toutesLesAnnonces.size()));


        List<Thread> threads = new ArrayList<>();
        for (Annonce a : toutesLesAnnonces) {
            Thread t = new Thread(() -> traiter(a));
//            t.setDaemon(true); // à false : la JVM attend qu'il finisse avant de s'arrêter (cas : Traitements importants), à true : la JVM ne l'attend pas, elle s'arrête même s'il tourne encore (cas : surveillance, logs, nettoyage)
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            t.join(); // bloque le thread principal jusqu'à ce que le thread t soit terminé, et comme on le fait pour chaque thread on attend que tous les threads soient finis.
        }

        System.out.println("Tous les traitements sont terminés");


    }

    private static void traiter(Annonce a) {
        System.out.println("Annonce : " + a.getTitre());
    }


}