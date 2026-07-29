package org.example;

import java.util.*;

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

    public Map<StatutAnnonce, List<Annonce>> grouperParStatut() {
        Map<StatutAnnonce, List<Annonce>> parStatut = new EnumMap<>(StatutAnnonce.class);
        for (Annonce annonce : annonces) {
            parStatut.computeIfAbsent(annonce.getStatut(), s -> new ArrayList<>()).add(annonce);
        }
        return parStatut;
    }

    public List<Annonce> annoncesPubliables() {
        EnumSet<StatutAnnonce> statutsOk = StatutAnnonce.statutsPubliables();
        List<Annonce> resultat = new ArrayList<>();
        for (Annonce annonce : annonces) {
            if (statutsOk.contains(annonce.getStatut())) {
                resultat.add(annonce);
            }
        }
        return resultat;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }
}
