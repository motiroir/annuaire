package isika.cda27.projet1.group4.annuaire.back;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileImporter {

    public String importer(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier texte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Texte", "*.txt","*.DON"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path filePath = selectedFile.toPath();
                List<String> fileLines = Files.readAllLines(filePath);
                
                // Vérifier la structure du fichier
                String structureTestResult = testFileStructure(fileLines);
                if (structureTestResult != null) {
                    return structureTestResult;
                }
                
                // Suppression d'un autre fichier après vérification réussie
                String deletionResult = deleteAnotherFile("src/main/resources/save/stagiairesDataBase.bin");
                if (deletionResult != null) {
                    return deletionResult;
                }
                
                // Retourner le contenu si le fichier est valide
                Annuaire annuaire = new Annuaire();
    			annuaire.lireFichier(filePath.toString());
    			BinarySearchTree searchTree = new BinarySearchTree();
    			for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
    				searchTree.ajouter(annuaire.getStagiaires().get(i));
    			}
                return "import ok";
            } catch (IOException e) {
                e.printStackTrace();
                return "Erreur lors de la lecture du fichier.";
            }
        } else {
            return "Aucun fichier sélectionné.";
        }
    }

    private String testFileStructure(List<String> fileLines) {
        // Taille du fichier doit être un multiple de 6 pour correspondre au schéma
        if (fileLines.size() % 6 != 0) {
            return "Erreur : Le fichier ne respecte pas le format attendu (multiples de 6 lignes).";
        }

        for (int i = 0; i < fileLines.size(); i += 6) {
            // Vérification des 3 premières lignes
            for (int j = 0; j < 3; j++) {
                if (!fileLines.get(i + j).matches("[a-zA-Z ]+")) {
                    return "Erreur : La ligne " + (i + j + 1) + " doit être une chaîne sans ponctuation.";
                }
            }

            // Vérification de la 3e et 5e ligne
            if (!fileLines.get(i + 3).matches("[a-zA-Z ]+/\\d+")) {
                return "Erreur : La ligne " + (i + 4) + " doit être une chaîne suivie d'un nombre (format String/Int sans ponctuation).";
            }
            if (!fileLines.get(i + 4).matches("[a-zA-Z ]+/\\d+")) {
                return "Erreur : La ligne " + (i + 5) + " doit être une chaîne suivie d'un nombre (format String/Int sans ponctuation).";
            }

            // Vérification de la 6e ligne
            if (!fileLines.get(i + 5).equals("*")) {
                return "Erreur : La ligne " + (i + 6) + " doit être un '*'.";
            }
        }
        return null; // null signifie que la structure est valide
    }

    private String deleteAnotherFile(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                return null; // Suppression réussie
            } else {
                return "Erreur : Impossible de supprimer le fichier " + filePath;
            }
        } else {
            return "Erreur : Le fichier à supprimer n'existe pas.";
        }
    }
}
