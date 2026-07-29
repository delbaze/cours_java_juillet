package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class AnnonceUtils {
    public static List<Annonce> filtrer(List<Annonce> annonces, Predicate<Annonce> critere) {
        List<Annonce> resultat = new ArrayList<>();
        for (Annonce a : annonces) {
            if (critere.test(a)) {
                resultat.add(a);
            }
        }
        return resultat;
    }

    public static List<Annonce> trier(List<Annonce> annonces, Comparator<Annonce> comparateur) {
        List<Annonce> copie = new ArrayList<>(annonces);
        copie.sort(comparateur);
        return copie;
    }


}
