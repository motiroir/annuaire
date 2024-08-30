package isika.cda27.projet1.group4.annuaire.back;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileExporter {

    public void exporterAnnuaire(Stage stage, List<Stagiaire> annuaire) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer l'annuaire sous un fichier texte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Texte", "*.txt"));

        // Ouvrir une boîte de dialogue pour choisir le fichier de destination
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            try (FileWriter writer = new FileWriter(selectedFile)) {
                // Parcourir chaque stagiaire de l'annuaire
                for (Stagiaire stagiaire : annuaire) {
                    writer.write(stagiaire.getName() + "\n");
                    writer.write(stagiaire.getFirstName() + "\n");
                    writer.write(stagiaire.getPostalCode() + "\n");
                    writer.write(stagiaire.getPromo() + "\n");
                    writer.write(stagiaire.getYear() + "\n");
                    writer.write("*\n");  // Séparateur entre les stagiaires
                }
                // Afficher une alerte après l'exportation réussie
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportation réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier texte a été exporté avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur d'écriture dans le fichier
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'exportation");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de l'exportation du fichier texte.");
                alert.showAndWait();
            }
        } else {
            // Gestion du cas où l'utilisateur annule l'exportation
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exportation annulée");
            alert.setHeaderText(null);
            alert.setContentText("L'exportation du fichier texte a été annulée.");
            alert.showAndWait();
        }
    }
}
