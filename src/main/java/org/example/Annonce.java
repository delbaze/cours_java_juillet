package org.example;
public class Annonce {
    private final Long id;
    private final String titre;
    private final double prix;
    private final BienImmobilier bien;
    private final String description;

    private Annonce(Builder builder) {
        this.id = builder.id;
        this.titre = builder.titre;
        this.prix = builder.prix;
        this.bien = builder.bien;
        this.description = builder.description;
    }

    public static class Builder {
        private Long id;
        private String titre;
        private double prix;
        private BienImmobilier bien;
        private String description;

        public Builder avecId(Long id) {
            this.id = id;
            return this;
        }

        public Builder avecTitre(String titre) {
            this.titre = titre;
            return this;
        }

        public Builder avecPrix(double prix) {
            this.prix = prix;
            return this;
        }

        public Builder avecBien(BienImmobilier bien) {
            this.bien = bien;
            return this;
        }

        public Builder avecDescription(String description) {
            this.description = description;
            return this;
        }

        public Annonce build() {
            if (id == null || titre == null || prix <= 0 || bien == null) {
                throw new IllegalStateException("Champs obligatoires manquants pour construire une Annonce");
            }
            return new Annonce(this);
        }
    }
}