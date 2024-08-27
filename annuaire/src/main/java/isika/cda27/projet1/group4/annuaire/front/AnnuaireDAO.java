package isika.cda27.projet1.group4.annuaire.front;

import java.util.ArrayList;

import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;

public class AnnuaireDAO {
public ArrayList <Stagiaire> stagiaires;

public AnnuaireDAO() {
	super();
	stagiaires = new ArrayList < Stagiaire>();
	BinarySearchTree searchTree = new BinarySearchTree();
	stagiaires.addAll(searchTree.affichage());
	
}

public ArrayList<Stagiaire> getStagiaires() {
	return stagiaires;
}

public void setStagiaires(ArrayList<Stagiaire> stagiaires) {
	this.stagiaires = stagiaires;
}



}
