package isika.cda27.projet1.group4.annuaire.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Annuaire représente un annuaire de stagiaires.
 * Elle permet de lire un fichier contenant les informations des stagiaires et de les stocker dans une liste.
 * Chaque stagiaire est représenté par un objet de la classe Stagiaire.
 */
public class Annuaire {
    private List<Stagiaire> stagiaires;

    /**
     * Constructeur par défaut de la classe Annuaire.
     * Initialise la liste des stagiaires comme une ArrayList vide.
     */
    public Annuaire() {
        this.stagiaires = new ArrayList<>();
    }

    /**
     * Lit un fichier texte et extrait les informations des stagiaires.
     * Le fichier doit avoir un format spécifique où chaque stagiaire est décrit par cinq lignes:
     * nom, prénom, département, promotion et année. Une ligne contenant "*" sépare les informations de chaque stagiaire.
     *
     * @param filePath Le chemin du fichier texte à lire.
     */
    public void lireFichier(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("*")) {
                    continue; // Passer la ligne contenant "*"
                }

                String nom = ligne;
                String prenom = br.readLine();
                String departement = br.readLine();
                String promotion = br.readLine();
                int annee = Integer.parseInt(br.readLine());

                Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, promotion, annee);
                stagiaires.add(stagiaire);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche dans la console la liste des stagiaires présents dans l'annuaire.
     * Chaque stagiaire est affiché en utilisant la méthode toString() de la classe Stagiaire.
     */
    public void afficherStagiaires() {
        for (Stagiaire stagiaire : stagiaires) {
            System.out.println(stagiaire);
        }
    }

    /**
     * Retourne la liste des stagiaires présents dans l'annuaire.
     *
     * @return Une liste d'objets Stagiaire.
     */
    public List<Stagiaire> getStagiaires() {
        return stagiaires;
    }

    /**
     * Remplace la liste des stagiaires présents dans l'annuaire par une nouvelle liste.
     *
     * @param stagiaires La nouvelle liste de stagiaires.
     */
    public void setStagiaires(List<Stagiaire> stagiaires) {
        this.stagiaires = stagiaires;
    }

}
