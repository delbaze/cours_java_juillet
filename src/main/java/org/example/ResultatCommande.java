package org.example;

public sealed interface ResultatCommande {
    record Success(String numeroCommande, double montant) implements ResultatCommande {}
    record EchecStock(String produitManquant) implements ResultatCommande {}
    record EchecPaiement(String raison) implements ResultatCommande {}
}

