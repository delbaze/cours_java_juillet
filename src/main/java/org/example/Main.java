package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
       Vehicule maThermique = new VoitureThermique("Mustang");
        System.out.println("--Démarrage thermique --");

        maThermique.demarrerVehicule();

        Vehicule maTesla = new VoitureElectrique("Tesla");
        System.out.println("--Démarrage electrique --");
        maTesla.demarrerVehicule();


        Paladin p = new Paladin();
        p.sauter();
        p.soigner("Allié");
        p.attaquer();
    }


}