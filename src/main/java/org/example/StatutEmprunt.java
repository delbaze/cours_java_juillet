package org.example;

public enum StatutEmprunt implements Describable {
    //    EN_COURS, RENDU, EN_RETARD;
    EN_COURS {
        @Override
        public String decrire() {
            return "Emprunt actif, retour prévu";
        }

        @Override
        public boolean peutEtreProlonge() {
            return true;
        }
    }, RENDU {
        @Override
        public String decrire() {
            return "Livre restitué, aucune action requise";
        }

        @Override
        public boolean peutEtreProlonge() {
            return false;
        }
    }, EN_RETARD {
        @Override
        public String decrire() {
            return "Retour attendu, pénalité en cours";
        }

        @Override
        public boolean peutEtreProlonge() {
            return false;
        }
    };

    public abstract boolean peutEtreProlonge();
//    public String decrire(){
//        return "En cours";
//    }
}
