package org.example;

import java.util.List;

public class Utilitaires {
    public static <T extends Comparable<T>> T maximum(List<T> elements) {
        T max = elements.get(0);
        for (T element : elements) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    public static double sommeValeurs(List<? extends BienImmobilier> biens) {
        double total = 0;
        for (BienImmobilier bien : biens) {
            total += bien.calculerValeur();
        }
        return total;
    }

    public static void archiverTitres(List<? super String> archive, List<Annonce> source) {
        for (Annonce annonce : source) {
            archive.add(annonce.getTitre());
        }
    }
}