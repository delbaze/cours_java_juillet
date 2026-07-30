package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class DemoFile {

    public static void main(String[] args) throws IOException {

        Path repertoire = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet");
        System.out.println(repertoire.getParent());

        Path fichier = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet/pom.xml");
        Path fichierDemo = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet/test.txt");
        Path fichierDemo2 = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet/test2.txt");
        Path fichierDemoCSV = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet/customers-100.csv");
        Path fichierDemoCSV2 = Path.of("/home/delbaze/Bureau/_NEBULA_GIT/cours_java_juillet/demo.csv");

        Path relatif = Path.of("data", "annonces.json" );

        Path parent = fichier.getParent();
        Path nomFichier = fichier.getFileName();
        System.out.println(nomFichier);

        Path resolu = repertoire.resolve("exports/rapport.csv");
        Path repertoireDemo = repertoire.resolve("hello/demo/coucou/test/");
        System.out.println(resolu);

        Path relatifDepuis = repertoire.relativize(fichier);
        System.out.println(relatifDepuis);

        Path normalise = Path.of("/a/b/../c").normalize();
        System.out.println(normalise);

        boolean memeParent = fichier.startsWith(repertoire);
        System.out.println(memeParent);

        // Files
        System.out.println(Files.exists(relatif)); // true si le fichie ou dossier existe
        System.out.println(Files.exists(fichier));

        System.out.println(Files.isRegularFile(fichier)); // true si c'est un fichier normal
        System.out.println(Files.isDirectory(repertoire)); // true si c'est un dossier

        String contenu = Files.readString(fichier);
//        System.out.println(contenu);
        List<String> lignes = Files.readAllLines(fichier);
        System.out.println(lignes.get(3));

        // Ecriture
        Files.writeString(fichierDemo, "Coucou");
        Files.writeString(fichierDemo, "Hello" + System.lineSeparator(), StandardOpenOption.APPEND);

        Files.createDirectories(repertoireDemo);

//        Files.copy(fichierDemo, fichierDemo2, StandardCopyOption.REPLACE_EXISTING );
//        Files.move(fichierDemo, fichierDemo2, StandardCopyOption.ATOMIC_MOVE);
//
//        Files.delete(fichierDemo); // leve une exception si absent
//        Files.deleteIfExists(fichierDemo); // silencieux si absent

        // Flux bufférisés

        try (BufferedReader reader = Files.newBufferedReader(fichier, StandardCharsets.UTF_8)) {
            String ligne;
            while((ligne = reader.readLine()) != null) {
                System.out.println("ici je traiterai ligne par ligne" + ligne);
            }
        }

        try (Stream<String> lignesCSV = Files.lines(fichierDemoCSV, StandardCharsets.UTF_8)) {
            lignesCSV
                    .skip(1)
                    .filter(l -> !l.isBlank())
                    .map(l -> l.split(","))
                    .forEach(colonnes -> {
                        System.out.println(colonnes);
                    });
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierDemoCSV2, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("id,titre,prix");
            writer.newLine();
        }

        try (Stream<Path> stream = Files.list(repertoire)) {
            List<Path> fichiersTxt = stream.filter(p -> p.toString().endsWith(".txt")).toList();
            System.out.println(fichiersTxt);

        }
        try (Stream<Path> stream = Files.walk(repertoire)) {
            long nbFichiers = stream.filter(Files::isRegularFile).count();
            System.out.println(nbFichiers);
        }

        // FileSystems.newFileSystem("jar:file:/chemin/archive.zip", env);
    }
}
