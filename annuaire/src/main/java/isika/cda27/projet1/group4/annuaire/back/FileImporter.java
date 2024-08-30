package isika.cda27.projet1.group4.annuaire.back;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;
import isika.cda27.projet1.group4.annuaire.front.HomePage;

public class FileImporter {

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
					// on ferme la lecture du fichier pour pouvoir le supprimer
					app.myDAO.searchTree.raf.close();
					// Suppression d'un autre fichier après vérification réussie
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

	private String deleteAnotherFile(String filePath) {

		File fileToDelete = new File(filePath);

		if (fileToDelete.exists()) {

			try {
				// on essaye de renommer le fichier avant la suppression pour vérifier s'il est
				// verrouillé
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
