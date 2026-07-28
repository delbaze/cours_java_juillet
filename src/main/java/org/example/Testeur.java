package org.example;

public class Testeur {
    private String nom = "Testeur";
    public void tester() {
        Runnable avecLambda = () -> System.out.println(this.nom);

        Runnable avecClasseAnonyme = new Runnable() {
            private String nom = "Anonyme";
            @Override
            public void run() {
                System.out.println(this.nom);
            }
        };
    }

}
