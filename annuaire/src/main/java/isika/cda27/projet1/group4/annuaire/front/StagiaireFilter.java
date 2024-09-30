package isika.cda27.projet1.group4.annuaire.front;

import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.Stagiaire;

/**
 * La classe {@code StagiaireFilter} permet de filtrer les stagiaires
 * selon plusieurs critères (nom, prénom, code postal, promotion, année).
 * Elle utilise une instance de {@link AnnuaireDAO} pour récupérer 
 * les stagiaires à filtrer.
 */
public class StagiaireFilter {

    /** 
     * DAO utilisé pour accéder aux données des stagiaires. 
     */
    private AnnuaireDAO myDAO;

    /**
     * Constructeur de la classe {@code StagiaireFilter}.
     *
     * @param myDAO Le DAO permettant d'accéder aux données des stagiaires.
     */
    public StagiaireFilter(AnnuaireDAO myDAO) {
        this.myDAO = myDAO;
    }

    /**
     * Filtre la liste des stagiaires en fonction des critères donnés.
     *
     * @param name       Le nom à filtrer (ou chaîne vide pour ignorer).
     * @param firstName  Le prénom à filtrer (ou chaîne vide pour ignorer).
     * @param postalCode Le code postal à filtrer (ou chaîne vide pour ignorer).
     * @param promo      La promotion à filtrer (ou chaîne vide pour ignorer).
     * @param year       L'année à filtrer (ou chaîne vide pour ignorer).
     * @return Une liste de stagiaires qui correspondent à tous les critères donnés.
     */
    public List<Stagiaire> filterStagiaires(String name, String firstName, String postalCode, String promo, String year) {
        List<Stagiaire> allStagiaires = myDAO.getStagiaires();
        List<Stagiaire> filteredStagiaires = new ArrayList<>();

        for (Stagiaire stagiaire : allStagiaires) {
            boolean matches = true;

            // Vérifie si le nom correspond
            if (!name.isEmpty() && !stagiaire.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            // Vérifie si le prénom correspond
            if (!firstName.isEmpty() && !stagiaire.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
                matches = false;
            }
            // Vérifie si le code postal correspond
            if (!postalCode.isEmpty() && !stagiaire.getPostalCode().toLowerCase().contains(postalCode.toLowerCase())) {
                matches = false;
            }
            // Vérifie si la promotion correspond
            if (!promo.isEmpty() && !stagiaire.getPromo().toLowerCase().contains(promo.toLowerCase())) {
                matches = false;
            }
            // Vérifie si l'année correspond
            if (!year.isEmpty()) {
                try {
                    int yearInt = Integer.parseInt(year);
                    if (stagiaire.getYear() != yearInt) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    matches = false;
                }
            }

            // Ajoute le stagiaire filtré à la liste si tous les critères correspondent
            if (matches) {
                filteredStagiaires.add(stagiaire);
            }
        }
        return filteredStagiaires;
    }
}
