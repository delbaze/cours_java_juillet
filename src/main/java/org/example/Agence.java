package org.example;

import java.util.ArrayList;
import java.util.List;

public class Agence {
    private final String nomAgence;
    private final List<Annonce> annonces = new ArrayList<>();

    public Agence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    public void ajouter(Annonce annonce) {
        annonces.add(annonce);
    }

    public String rapportPour(String nomVisiteur) {
        RapportVisite rapport = new RapportVisite();
        rapport.nomVisiteur = nomVisiteur;
        return rapport.genererRapport();
    }

    public class RapportVisite {
        String nomVisiteur;

        String genererRapport() {
            return "Rapport pour " + nomVisiteur + ", agence " + nomAgence;
        }
    }
}
