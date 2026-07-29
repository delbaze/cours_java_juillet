package org.example;
public class Annonce implements Comparable<Annonce> {
    private final Long id;
    private final String titre;
    private final double prix;
    private final BienImmobilier bien;
    private final String description;
    private final String ville;
    private final StatutAnnonce statut;

    private Annonce(Builder builder) {
        this.id = builder.id;
        this.titre = builder.titre;
        this.prix = builder.prix;
        this.bien = builder.bien;
        this.description = builder.description;
        this.ville = builder.ville;
        this.statut = builder.statut;

    }
    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public double getPrix() { return prix; }
    public String getVille() { return ville; }
    public BienImmobilier getBien() { return bien; }
    public String getDescription() { return description; }
    public StatutAnnonce getStatut() { return statut; }

    @Override
    public int compareTo(Annonce autre) {
        return Double.compare(this.prix, autre.prix);
    }

    public static class Builder {
        private Long id;
        private String titre;
        private double prix;
        private BienImmobilier bien;
        private String description;
        private String ville;
        private StatutAnnonce statut = StatutAnnonce.BROUILLON;



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

        public Builder avecVille(String ville) { this.ville = ville; return this; }

        public Builder avecStatut(StatutAnnonce statut) {
            this.statut = statut;
            return this;
        }

        public Annonce build() {
            if (id == null || titre == null || prix <= 0 || bien == null) {
                throw new IllegalStateException("Champs obligatoires manquants pour construire une Annonce");
            }
            return new Annonce(this);
        }
    }

//    public Annonce findById(long id) {
//        return repository.findById(id)
//                .orElseThrow(() => new AnnonceNotFoundException(id));
//    }
}