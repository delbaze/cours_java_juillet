package org.example;

import java.util.ArrayList;
import java.util.List;

public class AgenceImmobiliere {
    private String nom;
    private List<Annonce> annonces = new ArrayList<>();

    public class StatistiquesAnnonces {
        public int compterAnnonces() {
            return annonces.size();
        }

        public double calculerPrixMoyen() {
            return annonces.stream()
                    .mapToDouble(Annonce::getPrix)
                    .average()
                    .orElse(0.0);
        }
    }
}
