package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var appartement = new Appartement(65, 3, 3);
        var appartementCopie = appartement.copier(); // type Appartement grace au retour covariant
        System.out.println("Copie etage " + appartementCopie.getEtage() + ", valeur " + appartementCopie.calculerValeur());

        System.out.println(appartement.resume());

        var annonce = new Annonce.Builder()
                .avecId(1L)
                .avecTitre("Bel appartement")
                .avecPrix(250000.0)
                .avecBien(appartement)
                .build();

        var agence = new Agence("ImmoJava Nice");
        agence.ajouter(annonce);
        System.out.println(agence.rapportPour("Client Dupont"));

        Agence.RapportVisite rapportExterne = agence.new RapportVisite();
        rapportExterne.nomVisiteur = "Visiteur externe";
        System.out.println(rapportExterne.genererRapport());

        var cat = CategorieLivre.fromLibelle("Bande dessinée");
        System.out.println(cat.ordinal());
        System.out.println(cat.getDureeEmpruntJours());

        CategorieLivre a = CategorieLivre.BD;
        CategorieLivre b = CategorieLivre.valueOf("BD");

        System.out.println(a == b);

        String message = switch (cat) {
            case ROMAN, ESSAI -> "Emprunt long";
            case BD, JEUNESSE, MANGA -> "Emprunt court";
        };
        System.out.println(message);

        Map<StatutEmprunt, List<Emprunt>> parStatut = new EnumMap<>(StatutEmprunt.class);

        for (StatutEmprunt statut : StatutEmprunt.values()) {
            parStatut.put(statut, new ArrayList<>());
        }
        Emprunt e1 = new Emprunt();
        parStatut.get(e1.getStatut()).add(e1);

        Set<StatutEmprunt> statutsProlongeables = EnumSet.of(StatutEmprunt.EN_COURS);
        Set<StatutEmprunt> tousLesStatuts = EnumSet.allOf(StatutEmprunt.class);
        Set<StatutEmprunt> aucun = EnumSet.noneOf(StatutEmprunt.class);
        Set<StatutEmprunt> sansEnCours = EnumSet.complementOf(EnumSet.of(StatutEmprunt.EN_COURS));

//        if (statut == StatutEmprunt.EN_COURS || statut == StatutEmprunt.RENDU){}
        Set<StatutEmprunt> statutsActifs = EnumSet.of(StatutEmprunt.EN_COURS, StatutEmprunt.RENDU);
//        if (statutsActifs.contains(statut)){
//            ///
//        }
    }
}