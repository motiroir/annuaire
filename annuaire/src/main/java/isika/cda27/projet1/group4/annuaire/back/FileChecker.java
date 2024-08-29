package isika.cda27.projet1.group4.annuaire.back;
import java.io.File;

public class FileChecker {

    /**
     * Vérifie si un fichier nommé "data.bin" est présent dans le dossier spécifié.
     * 
     * @return true si le fichier est présent, false sinon.
     */
    public boolean isDataBaseBinPresent() {
    	
        // Crée un objet File pour le dossier spécifié
        File folder = new File("src/main/resources/save");
        
        // Vérifie si le chemin spécifié est bien un dossier
        if (folder.isDirectory()) {
            // Crée un objet File pour le fichier "dataBase.bin" dans ce dossier
            File dataBaseBinFile = new File(folder, "stagiairesDataBase.bin");
            
            // Vérifie si le fichier existe
            return dataBaseBinFile.exists();
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier.");
            return false;
        }
    }
}
