package org.example;

import java.io.IOException;

@FunctionalInterface
public interface FonctionAvecException<T, R> {
    R apply(T t) throws IOException;
}
