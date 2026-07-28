package org.example;

import java.util.EnumSet;

public enum StatutAnnonce implements Describable {
    BROUILLON {
        @Override
        public String decrire() {
            return "En cours de redaction, non visible publiquement";
        }

        @Override
        public boolean peutEtrePubliee() {
            return true;
        }
    },
    PUBLIEE {
        @Override
        public String decrire() {
            return "Visible sur la plateforme";
        }

        @Override
        public boolean peutEtrePubliee() {
            return false;
        }
    },
    VENDUE {
        @Override
        public String decrire() {
            return "Transaction conclue";
        }

        @Override
        public boolean peutEtrePubliee() {
            return false;
        }
    };

    public abstract boolean peutEtrePubliee();

    public static EnumSet<StatutAnnonce> statutsPubliables() {
        EnumSet<StatutAnnonce> resultat = EnumSet.noneOf(StatutAnnonce.class);
        for (StatutAnnonce statut : values()) {
            if (statut.peutEtrePubliee()) {
                resultat.add(statut);
            }
        }
        return resultat;
    }
}
