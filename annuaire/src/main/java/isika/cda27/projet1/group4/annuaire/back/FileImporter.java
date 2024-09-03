package isika.cda27.projet1.group4.annuaire.back;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;

/**
 * La classe FileImporter permet d'importer un fichier texte contenant des informations 
 * sur des stagiaires et de les charger dans une structure de données appropriée.
 * Elle gère l'ouverture de fichier, la validation de la structure du fichier, 
 * et la mise à jour des données de l'application.
 */
public class FileImporter {

    /**
     * Importe un fichier texte sélectionné par l'utilisateur, vérifie sa structure, 
     * et charge les données dans l'application.
     *
     * @param stage La fenêtre principale de l'application utilisée pour afficher la boîte de dialogue d'ouverture de fichier.
     * @param app   L'instance principale de l'application qui contient les données et la logique d'accès aux données.
     * @return Un message indiquant si l'importation a réussi, a échoué, ou a été annulée.
     */
    public String importer(Stage stage, App app) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier texte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Texte", "*.txt", "*.DON"));

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

                File dataBase = new File("src/main/resources/save/stagiairesDataBase.bin");
                if (dataBase.exists()) {
                    // Fermer la lecture du fichier pour pouvoir le supprimer
                    app.myDAO.searchTree.raf.close();
                    // Suppression du fichier existant après vérification réussie
                    String deletionResult = deleteAnotherFile(dataBase.toString());
                    if (deletionResult != null) {
                        return deletionResult;
                    }
                }

                // Construction du nouvel annuaire
                Annuaire annuaire = new Annuaire();
                annuaire.lireFichier(filePath.toString());
                app.myDAO = new AnnuaireDAO();
                for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
                    app.myDAO.searchTree.ajouter(annuaire.getStagiaires().get(i));
                }

                app.myObservableArrayList.setAll(app.myDAO.getStagiaires());
                
            } catch (IOException e) {
                e.printStackTrace();
                return "Erreur lors de la lecture du fichier.";
            }
            return "Importation d'un nouveau fichier texte réussie";
        } else {
            return "Aucun fichier sélectionné.";
        }
    }

    /**
     * Vérifie la structure d'un fichier texte pour s'assurer qu'il suit un format valide.
     *
     * @param fileLines La liste des lignes lues à partir du fichier texte.
     * @return Un message d'erreur si la structure est invalide, ou null si la structure est valide.
     */
    private String testFileStructure(List<String> fileLines) {
        // Taille du fichier doit être un multiple de 6 pour correspondre au schéma
        if (fileLines.size() % 6 != 0) {
            return "Erreur : Le fichier ne respecte pas le format attendu (multiples de 6 lignes).";
        }

        for (int i = 0; i < fileLines.size(); i += 6) {
            // Vérification de la 6e ligne
            if (!fileLines.get(i + 5).equals("*")) {
                return "Erreur : La ligne " + (i + 6) + " doit être un '*'.";
            }
        }
        return null; // null signifie que la structure est valide
    }

    /**
     * Supprime un fichier spécifié après avoir vérifié qu'il peut être supprimé (pas verrouillé).
     *
     * @param filePath Le chemin du fichier à supprimer.
     * @return Un message d'erreur si la suppression échoue, ou null si la suppression réussit.
     */
    private String deleteAnotherFile(String filePath) {

        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {

            try {
                // Vérifier si le fichier est verrouillé en tentant de le renommer
                File tempFile = new File(fileToDelete.getAbsolutePath() + ".tmp");
                boolean renamed = fileToDelete.renameTo(tempFile);
                if (renamed) {
                    tempFile.delete();
                    return null; // Suppression réussie
                } else {
                    return "Erreur : Le fichier est peut-être utilisé par un autre processus.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Erreur : Impossible de supprimer le fichier " + filePath;
            }
        } else {
            return "Erreur : Le fichier à supprimer n'existe pas.";
        }
    }
}
