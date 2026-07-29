package org.example;

import java.util.List;
import java.util.function.Supplier;

public class Boite<T> {

    public T creerVide(Supplier<T> fabrique) {
        return fabrique.get();
    }

    public boolean estInstance(Object o, Class<T> type)  {
        return type.isInstance(o);
    }

    public List<T> filtrerParType(List<?> objets, Class<T> type)  {
        return objets.stream()
                .filter(type::isInstance)
                .map(type::cast) // transtypage dynamique sécurisé
                .toList();
    }
    public static void main(String[] args) {
        Boite<String> boite = new Boite<>();
        String vide = boite.creerVide(String::new);
        Object texte = "Bonjour";

        boolean b1 = texte instanceof String;
        System.out.println(b1);

        boolean b2 = boite.estInstance(texte, String.class);
        System.out.println(b2);
    }
}
