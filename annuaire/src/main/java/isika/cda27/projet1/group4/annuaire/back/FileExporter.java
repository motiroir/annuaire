package isika.cda27.projet1.group4.annuaire.back;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * La classe FileExporter permet d'exporter un annuaire de stagiaires dans un fichier texte.
 * L'utilisateur peut choisir l'emplacement de sauvegarde à l'aide d'une boîte de dialogue.
 */
public class FileExporter {

    /**
     * Exporte l'annuaire des stagiaires dans un fichier texte sélectionné par l'utilisateur.
     * Chaque stagiaire est écrit dans le fichier avec ses informations sur des lignes séparées,
     * et un séparateur "*" est utilisé pour distinguer chaque stagiaire.
     *
     * @param stage    La fenêtre principale de l'application utilisée pour afficher la boîte de dialogue de sauvegarde.
     * @param annuaire La liste des stagiaires à exporter.
     * @return Un message indiquant si l'exportation a réussi, a échoué, ou a été annulée.
     */
    public String exporterAnnuaire(Stage stage, List<Stagiaire> annuaire) {
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
                return "Le fichier texte a été exporté avec succès !";
            } catch (IOException e) {
                e.printStackTrace();
                return "Une erreur est survenue lors de l'exportation du fichier texte.";
            }
        } else {
            return "L'exportation du fichier texte a été annulée.";
        }
    }
}
