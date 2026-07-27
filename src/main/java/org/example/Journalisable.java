package org.example;

public interface Journalisable {
    default String resume() {
        return "Log : " + getClass().getSimpleName();
    }
}