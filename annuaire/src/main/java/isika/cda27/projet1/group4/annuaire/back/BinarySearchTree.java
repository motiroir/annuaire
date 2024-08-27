package isika.cda27.projet1.group4.annuaire.back;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
	Node root;
	RandomAccessFile raf;  

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
	//on utilise cette fonction pour créer l'arbre à l'aide d'une boucle sur une liste de stagiaires
	//annuaire dans le main à partir d'un fichier texte
	public void ajouter(Stagiaire stagiaire) {
		try {
			//System.out.println("dbt : taille fichier = " + raf.length());
			if (raf.length()== 0) {
				this.root = new Node(stagiaire);
				this.root.newNodeWriter(raf, stagiaire);
			} else {
				this.root = this.root.nodeReader(raf, 0);
				//System.out.println("racine :" + this.root);
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
			if (raf.length()== 0) {
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
				if (raf.length()== 0) {
					System.out.println("L'arbre est vide");
				} else {
					this.root = this.root.nodeReader(raf, 0);
					this.root.searchStagiaire(raf, searchedStagiaire, stagiairesFound);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return stagiairesFound;
		}

	// suppression d'un element de l'arbre
	public void deleteInTree(Stagiaire stagiaire) {
		try {
			if (raf.length()== 0) {
				System.out.println("l'arbre est vide");
			} else {
				this.root = this.root.nodeReader(raf, 0);
				this.root.delete(raf, stagiaire, Node.NODE_SIZE_OCTET, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
