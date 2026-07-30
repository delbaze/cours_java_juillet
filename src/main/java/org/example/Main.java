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

        TreeSet<Double> prixTries = new TreeSet<>(Set.of(150.0, 320.0, 89.0, 500.0));
        System.out.println(prixTries.first());
        System.out.println(prixTries.last());
        System.out.println(prixTries.higher(150.0));
        System.out.println(prixTries.floor(200.0));

        Map<String, Produit> parNom = new HashMap();
        Map<String, Produit> parNomOrdonne = new LinkedHashMap<>();
        Map<String, Produit> parNomTrie = new TreeMap<>();


        Map<String, Integer> compteur = new HashMap();

        compteur.computeIfAbsent("Clavier", k -> 0);

        compteur.merge("Clavier", 1, Integer::sum);// incrément ou initialise à 1 si absent

        int quantite = compteur.getOrDefault("Souris", 0);
        compteur.putIfAbsent("Ecran", 5);

        compteur.compute("Clavier", (k, v) -> v == null ? 1 : v + 1);

//        Map<String, Integer> stockParCategorie = new HashMap();
//        for (Produit p : produits) {
//            stockParCategorie.merge(p.getCategorie(), p.getStock(), Integer::sum);
//        }
        // { "Informatique" : 145, "Jardin": 50, ...}
//
//        Deque<Produit> pile = new ArrayDeque<>();
//        pile.push(produit1);
//        pile.pop();
//
//        Deque<Produit> file = new ArrayDeque<>();
//        file.offer(produit1); // enfile
//        file.poll(); // défile
//
//        Queue<Produit> parPrix = new PriorityQueue<>(Comparator.comparing(Produit::getPrix));
//        parPrix.offer((new Produit("Clavier", 59.0, 10, 3L)));
//        parPrix.poll(); // retourne et retire le moins cher
//
//        Queue<String> file = new ArrayDeque<>();
//        file.offer("Clavier");
//        file.poll(); // gere le cas vide
//
//        file.peek(); // plante pas si vide
//
//        List<String> liste = new ArrayList<>();
//        liste.add("Clavier");
//        liste.remove(0);
//        liste.get(0);

        // add(0, e) / remove(0)
//        push() pop()

//        ArrayList remove(0) => O(n)
//        ArrayDeque : retire le premier ou dernier est O(1)
        //


        // Créer directmeent une collection immuable
        List<String> categories = List.of("Informatique", "Jardiin", "Mode");
        Set<String> tags = Set.of("promo", "nouveau");
        Map<String, Double> prixParDefaut = Map.of("Clavier", 49.00, "Souris", 19.90);

        List<String> modifiable = new ArrayList<>(List.of("a", "b"));
        List<String> vueImmuable = Collections.unmodifiableList(modifiable);
        modifiable.add("c");

        System.out.println(vueImmuable);
//        vueImmuable.add("c");

        Produit p1 = new Produit("test 1", 10.00, 30, 1L);
        Produit p2 = new Produit("test 2", 10.00, 0, 2L);
        Produit p3 = new Produit("test 1", 20.00, 43, 3L);

        List<Produit> produits = new ArrayList<>(List.of(p1, p2, p3));

        for (Produit p : produits) {
            System.out.println(p.getQuantite());
            if (p.getQuantite() == 0) {
                produits.remove(p); // ConcurrentModificationException
            }
        }

        produits.removeIf(p -> p.getQuantite() == 0); // garantie qu'on peut modifié un itérateur pendant qu'on le parcours

        // sinon manuellement :
//        Iterator<Produit> it = produits.iterator();
//        while (it.hasNext()) {
//            Produit p = it.next();
//            if (p.getQuantite() == 0) {
//                it.remove(); // sûr l'itérateur sait qu'il vient lui même de modifier la structure
//            }
//        }
    }


}