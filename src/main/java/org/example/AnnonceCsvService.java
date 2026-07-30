package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class AnnonceCsvService {

    private static final String EN_TETE = "titre;prix;ville;type";

    public void exporterCsv(List<Annonce> annonces, Path destination) throws IOException {
        if (destination.getParent() != null) {
            Files.createDirectories(destination.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                destination, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write(EN_TETE);
            writer.newLine();

            for (Annonce annonce : annonces) {
                String type = switch (annonce.getBien()) {
                    case Appartement a -> "APPARTEMENT";
                    case Maison m -> "MAISON";
                    case Terrain t -> "TERRAIN";
                };
//                writer.write("%s;%.2f;%s;%s".formatted(
//                        annonce.getTitre(), annonce.getPrix(), annonce.getVille(), type));
                writer.write(String.format(Locale.US, "%s;%.2f;%s;%s",
                        annonce.getTitre(), annonce.getPrix(), annonce.getVille(), type));
                writer.newLine();
            }
        }

        System.out.printf("Export CSV : %d annonce(s) -> %s%n", annonces.size(), destination);
    }

    public List<AnnonceImportee> importerCsv(Path source) throws IOException {
        List<AnnonceImportee> imports = new ArrayList<>();
        int[] compteurs = {0, 0}; // lignes traitees, lignes ignorees

        try (Stream<String> lignes = Files.lines(source, StandardCharsets.UTF_8)) {
            lignes
                    .skip(1)
                    .filter(l -> !l.isBlank())
                    .forEach(ligne -> {
                        compteurs[0]++;
                        try {
                            String[] cols = ligne.split(";", -1);
                            if (cols.length < 4) {
                                throw new IllegalArgumentException("Colonnes manquantes : " + cols.length);
                            }
                            imports.add(new AnnonceImportee(
                                    cols[0].trim(),
                                    Double.parseDouble(cols[1].trim()),
                                    cols[2].trim(),
                                    cols[3].trim()
                            ));
                        } catch (Exception e) {
                            compteurs[1]++;
                            System.err.printf("[Import] Ligne %d ignoree : %s -> %s%n",
                                    compteurs[0], ligne, e.getMessage());
                        }
                    });
        }

        System.out.printf("Import CSV : %d importee(s), %d ignoree(s) depuis %s%n",
                imports.size(), compteurs[1], source.getFileName());

        return imports;
    }
}