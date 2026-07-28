package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var agence = new Agence("ImmoJava Nice");

        agence.ajouter(new Annonce.Builder()
                .avecId(1L).avecTitre("Studio centre-ville").avecPrix(120000.0)
                .avecBien(new Appartement(25, 1, 4))
                .avecStatut(StatutAnnonce.BROUILLON)
                .build());

        agence.ajouter(new Annonce.Builder()
                .avecId(2L).avecTitre("Maison avec jardin").avecPrix(380000.0)
                .avecBien(new Maison(110, 4, 200))
                .avecStatut(StatutAnnonce.PUBLIEE)
                .build());

        for (var entry : agence.grouperParStatut().entrySet()) {
            System.out.println(entry.getKey().decrire() + " : " + entry.getValue().size() + " annonce(s)");
        }

        System.out.println("Publiables : " + agence.annoncesPubliables().size());
    }
}