package isika.cda27.projet1.group4.annuaire.back;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
	public Node root;
	public RandomAccessFile raf;

	public BinarySearchTree() {
		root = new Node(null);
		try {
			raf = new RandomAccessFile("src/main/resources/save/stagiairesDataBase.bin", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BinarySearchTree(Node root) {
		super();
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return (this.root == null);
	}

	// ajouter un element
	// on utilise cette fonction pour créer l'arbre à l'aide d'une boucle sur une
	// liste de stagiaires
	// annuaire dans le main à partir d'un fichier texte
	public void ajouter(Stagiaire stagiaire) {
		try {
			// System.out.println("dbt : taille fichier = " + raf.length());
			if (raf.length() == 0) {
				this.root = new Node(stagiaire);
				this.root.newNodeWriter(raf, stagiaire);
			} else {
				this.root = this.root.nodeReader(raf, 0);
				this.root.addNode(raf, stagiaire);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// affichage par ordre alphabetique
	public List<Stagiaire> affichage() {
		List<Stagiaire> stagiaires = new ArrayList<>();
		try {
			if (raf.length() == 0) {
				System.out.println("L'arbre est vide");
			} else {
				this.root = this.root.nodeReader(raf, 0);
				this.root.read(raf, stagiaires);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stagiaires;
	}

	// recherche d'un element dans l'arbre
	public List<Stagiaire> searchStagiaireInTree(Stagiaire searchedStagiaire) {
		List<Stagiaire> stagiairesFound = new ArrayList<>();
		try {
			if (raf.length() == 0) {
				System.out.println("L'arbre est vide");
			} else {
				this.root = this.root.nodeReader(raf, 0);
				this.root.searchStagiaire(raf, searchedStagiaire, stagiairesFound);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stagiairesFound;
	}

	// suppression d'un element de l'arbre
	public void deleteInTree(Stagiaire stagiaire) {
		try {
			if (raf.length() == 0) {
				System.out.println("l'arbre est vide");
			} else {
				this.root = this.root.nodeReader(raf, 0);
				this.root.delete(raf, stagiaire);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// modification d'un element de l'arbre
	public void updateInTree(Stagiaire oldStagiaire, Stagiaire newStagiaire) {
		try {
			if (raf.length() == 0) {
				System.out.println("l'arbre est vide");
			} else {
				this.root.delete(raf, oldStagiaire);
				this.root.addNode(raf, newStagiaire);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//fonction équilibrage de l'arbre
	public void balanceTree() {
		List<Stagiaire> balancedList = this.balancedList();
		 try {
			new FileOutputStream("src/main/resources/save/stagiairesDataBase.bin").close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Stagiaire stag: balancedList) {
			ajouter(stag);
		};
	}
	
	//Création d'une liste équilibrée
	//renvoie une liste dans un ordre d'insertion équilibré
	private List<Stagiaire> balancedList() {
		List<Stagiaire> originalList = this.affichage();
		List<Stagiaire> balancedList = new ArrayList<>();
		addBalanced(originalList, balancedList, 0, originalList.size() - 1);
        return balancedList;
	}
	
	//Ajout d'un element dans une liste équilibrée
	private void addBalanced(List<Stagiaire> originalList, List<Stagiaire> balancedList, int start, int end) {
			if (start > end) {
            return;
        }
        
        int mid = (start + end) / 2;
        balancedList.add(originalList.get(mid));
        
        addBalanced(originalList, balancedList, start, mid - 1);
        addBalanced(originalList, balancedList, mid + 1, end);
	}
}

