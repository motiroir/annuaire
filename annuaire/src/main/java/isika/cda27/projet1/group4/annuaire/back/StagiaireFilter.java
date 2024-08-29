package isika.cda27.projet1.group4.annuaire.back;

import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;

public class StagiaireFilter {

	 private AnnuaireDAO myDAO;

	    public StagiaireFilter(AnnuaireDAO myDAO) {
	        this.myDAO = myDAO;
	
	    }
	    public List<Stagiaire> filterStagiaires(String name, String firstName, String postalCode, String promo, String year) {
	        List<Stagiaire> allStagiaires = myDAO.getStagiaires();
	        List<Stagiaire> filteredStagiaires = new ArrayList<>();

	        for (Stagiaire stagiaire : allStagiaires) {
	            boolean matches = true;

	            if (!name.isEmpty() && !stagiaire.getName().toLowerCase().contains(name.toLowerCase())) {
	                matches = false;
	            }
	            if (!firstName.isEmpty() && !stagiaire.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
	                matches = false;
	            }
	            if (!postalCode.isEmpty() && !stagiaire.getPostalCode().toLowerCase().contains(postalCode.toLowerCase())) {
	                matches = false;
	            }
	            if (!promo.isEmpty() && !stagiaire.getPromo().toLowerCase().contains(promo.toLowerCase())) {
	                matches = false;
	            }
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

	            if (matches) {
	                filteredStagiaires.add(stagiaire);
	            }
	        }
	        return filteredStagiaires;
	    }
	}
	    
	    
	    
	    
	    
	    

