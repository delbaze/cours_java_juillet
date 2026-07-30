package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Compteur {
    private final AtomicInteger total = new AtomicInteger(0);

//    public synchronized void incrementer() {
    public  void incrementer() {
//        total++; // pas atomique : lire, incrémenter, écrire - 3 oéprations séparées
        total.incrementAndGet(); // atomique sans synchronized
    }

}
