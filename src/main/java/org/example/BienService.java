package org.example;

public class BienService {
    public static String description(BienImmobilier bien) {
        return switch (bien) {
            case Appartement(double surface, int nombrePieces, int etage) ->
                    "Appartement de %.1f m2, %d pieces, etage %d".formatted(surface, nombrePieces, etage);
            case Maison(double surface, int nombrePieces, double surfaceJardin) ->
                    "Maison de %.1f m2, %d pieces, jardin de %.1f m2".formatted(surface, nombrePieces, surfaceJardin);
            case Terrain(double surface, boolean constructible) ->
                    "Terrain de %.1f m2, %s".formatted(surface, constructible ? "constructible" : "non constructible");
        };
    }
}