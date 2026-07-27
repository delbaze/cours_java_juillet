package org.example;

public class Annonce {
    private String titre;
    private double prix;

    private Annonce(Builder builder) {
        this.titre = builder.titre;
        this.prix = builder.prix;
    }
    public double getPrix() {
        return prix;
    }

    public static class Builder {
        private String titre;
        private double prix;

        public Builder titre(String titre) {
            this.titre = titre;
            return this;
        }
        public Builder prix(double prix) {
            this.prix = prix;
            return this;
        }

        public Annonce build() {
            // ici je checke toutes les valeurs
            // Lombok ?
            // @Builder
            return new Annonce(this);
        }
    }

}
