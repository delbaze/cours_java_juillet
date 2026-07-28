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
    }
    public static int factorielle(int n) {
        return n <= 1 ? 1 : n * factorielle(n - 1);
    }
}