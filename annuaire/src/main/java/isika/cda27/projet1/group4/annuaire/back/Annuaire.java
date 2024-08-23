package isika.cda27.projet1.group4.annuaire.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Annuaire {
	private List<Stagiaire> stagiaires;

	public Annuaire() {
		this.stagiaires = new ArrayList<>();
	}

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherStagiaires() {
		for (Stagiaire stagiaire : stagiaires) {
			System.out.println(stagiaire);
		}
	}

	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(List<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}

}
