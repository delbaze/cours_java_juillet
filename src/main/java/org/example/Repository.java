package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Repository<T> {
    private final List<T> elements = new ArrayList<>();
    private final Supplier<T> fabrique;

    public Repository(Supplier<T> fabrique) {
        this.fabrique = fabrique;
    }

    public void ajouter(T item) {
        elements.add(item);
    }

    public List<T> tous() {
        return List.copyOf(elements);
    }

    public Optional<T> trouver(Predicate<T> critere) {
        return elements.stream().filter(critere).findFirst();
    }

    public T creerVide() {
        return fabrique.get();
    }
}
