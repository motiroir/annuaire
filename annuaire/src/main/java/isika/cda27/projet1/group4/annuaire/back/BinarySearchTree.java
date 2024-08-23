package isika.cda27.projet1.group4.annuaire.back;

public class BinarySearchTree {
	Node root;
	
	public BinarySearchTree() {
        root = null;
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
	
	//ajouter un element
	public void ajouter(Stagiaire stagiaire) {
		if(isEmpty()){
			this.root = new Node(stagiaire);
		} else {
			this.root.addNode(stagiaire);
		}
	}
	
	//affichage par ordre alphabetique
	public void affichage() {
		if(isEmpty()) {
			System.out.println("L'arbre est vide");
		} else {
			this.root.read();
		}
	}

	//recherche d'un element dans l'arbre
	public Stagiaire searchStagiaireInTree(Stagiaire searchedStagiaire) {
		if(isEmpty()) {
			System.out.println("L'arbre est vide");
			return null;
		} else {
			return this.root.searchStagiaire(searchedStagiaire);
		}
	}
	
	//suppression d'un element de l'arbre
	public void deleteInTree (Stagiaire stagiaire) {
		if(isEmpty()) {
			System.out.println("l'arbre est vide");
		} else {
			this.root.delete(stagiaire);
		}
	}
}
