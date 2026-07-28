package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class ExempleException {
    public String lirefichier(String chemin) throws IOException {
        return Files.readString(Path.of(chemin));
    }

    public void validerPrix(double prix) {
        if (prix < 0) {
            throw new IllegalArgumentException("Le prix ne peut tpas êtret négatif : " + prix);
        }
    }

    public String lirefichierSansErreur(String chemin) {
        try {
            return Files.readString(Path.of(chemin));
        } catch (IOException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        // sans try with resources (pour tout objet qui implément AutotCloseable
        try {
            reader = new BufferedReader(new FileReader("fichier.txt"));
            String ligne = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    } catch (SQLException e) {
//        System.err.println(e.getMessage());
//    }
        }
    }
}
