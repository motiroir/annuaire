package isika.cda27.projet1.group4.annuaire.front;

import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;

public class AnnuaireDAO {
	public BinarySearchTree searchTree;
	

	public AnnuaireDAO() {
		super();
		this.searchTree = new BinarySearchTree();
		
	}

	public List<Stagiaire> getStagiaires() {
		return searchTree.affichage();
	}
}

//	public void setStagiaires() {
//		this.searchTree = stagiaires;
//	}
//
//	public void addStagiaire(Stagiaire stagiaire) {
//		searchTree.add(stagiaire);
//	}
//
//	public void removeStagiaire(Stagiaire stagiaire) {
//		searchTree.remove(stagiaire);
//	}
//
//}
