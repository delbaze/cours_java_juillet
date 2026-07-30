package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DemoThread {
    public static void main(String[] args) throws InterruptedException {
        Runnable tache = () -> {
            System.out.println("Exécuté par " + Thread.currentThread().getName());
        };


        Thread thread = new Thread(tache, "mon-thread");
        Thread thread2 = new Thread(tache, "mon-thread-2");

        thread.start();
        thread2.start();
//        thread.run();// jamais thread.run() directement => ça n'exécuterait pas dans un nouveau thread

//        tache.run();
        Compteur compteur = new Compteur();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i ++) {
            Thread t = new Thread(compteur::incrementer);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) t.join();
//        System.out.println(compteur.total);

//        ExecutorService pool = Executors.newFixedThreadPool(4);
//        pool.execute(() -> traiter(annonce));
//
//        Future<Double> future = pool.submit(() -> calculerValeurTotal(annonce));
//
//        try {
//            Double resultat = future.get(5, TimeUnit.SECONDS);
//        } catch (ExecutionException e) {
//            Throwable cause = e.getCause();
//        } catch (TimeoutException e) {
//            future.cancel(true);
//        }
//
//        pool.shutdown(); // refuse les nouvelles tâches, attend la fin de celles en cours
//        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
//
//
//        CompletableFuture<List<Annonce>> futureNice = CompletableFuture.supplyAsync(() -> rechercherParVille("Nice"), pool);
//
//        CompletableFuture<Integer> futureNombre = futureNice.thenApply(List::size);
    }
}
