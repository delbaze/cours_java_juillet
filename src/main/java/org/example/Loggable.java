package org.example;

public interface Loggable {

    default void log(String message) {
        System.out.println("[LOG  " + System.currentTimeMillis()  + "] : " + message);
    }
}
