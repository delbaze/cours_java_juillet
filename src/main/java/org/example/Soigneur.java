package org.example;

public interface Soigneur {
    default void soigner(String cible) {
        System.out.println("Soin appliqué sur " + cible + " (+50pv)");
    }
}
