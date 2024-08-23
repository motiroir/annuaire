package isika.cda27.projet1.group4.annuaire.back;

public class Node {

	//private static final int NODE_SIZE_OCTET = Stagiaire.STAGIAIRE_SIZE_OCTET + 12;
	
	private Stagiaire key;
	private Node rightChild;
	private Node leftChild;
	private Node doublon;

	public Node(Stagiaire key) {
		this.key = key;
		this.rightChild = null;
		this.leftChild = null;
		this.doublon = null;
	}

	
	
	
	public Stagiaire getKey() {
		return key;
	}




	public void setKey(Stagiaire key) {
		this.key = key;
	}




	public Node getRightChild() {
		return rightChild;
	}




	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}




	public Node getLeftChild() {
		return leftChild;
	}




	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}




	public Node getDoublon() {
		return doublon;
	}




	public void setDoublon(Node doublon) {
		this.doublon = doublon;
	}




	// Ajout d'un noeud
	public void addNode(Stagiaire stagiaire) {
		if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
			if (this.leftChild == null) {
				this.leftChild = new Node(stagiaire);
				
			} else {
				this.leftChild.addNode(stagiaire);
			}
		} else if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) < 0) {
			if (this.rightChild == null) {
				this.rightChild = new Node(stagiaire);
			} else {
				this.rightChild.addNode(stagiaire);
			}
		} else if (doublon == null) {
			this.doublon = new Node(stagiaire);
		} else {
			this.doublon.addNode(stagiaire);
		}
	}

	// parcours infixe (affichage par ordre alphabetique)
	// le parcours infixe Gauche Node Droit
	public void read() {
		if (this.leftChild != null) {
			this.leftChild.read();
		}
		System.out.println(this.key.toString());
		if (this.doublon!=null) {
			System.out.println(this.doublon.key.toString());
		}
		if (this.rightChild != null) {
			this.rightChild.read();
		}
	}

	//recherche d'un noeud par sa cle
	public Stagiaire searchStagiaire(Stagiaire searchedStagiaire) {
		if(this.key.getName().compareTo(searchedStagiaire.getName()) == 0) {
			return this.key;
		} else if(this.key.getName().compareTo(searchedStagiaire.getName()) > 0) {
			return this.leftChild.searchStagiaire(searchedStagiaire);
		} else {
			return this.rightChild.searchStagiaire(searchedStagiaire);
		}
	}
	
	//suppression d'un noeud à partir de sa cle
	//premiere etape recherche du noeud a supprimer
	public Node delete(Stagiaire stagiaire) {
		if(this.key.getName().compareTo(stagiaire.getName()) == 0) {
			// TODO : gérer les doublons
			return this.deleteRoot();
		} else if(this.key.getName().compareTo(stagiaire.getName()) > 0){
			this.leftChild = this.leftChild.delete(stagiaire);
		} else {
			this.rightChild = this.rightChild.delete(stagiaire);
		}
		return this;
	}

	//methode de suppression
	//deuxieme etape suppression du noeud une fois trouve
	//le noeud a supprimer a ete trouve c'est "this"
	private Node deleteRoot() {
		//si le fils gauche est nul on retourne le fils droit 
		//si le fils droit est aussi null le noeud a supprimer sera remplace par null, 
		//sinon le noeud a supprimer sera remplace par le fils droit
		if(this.leftChild == null) {
			return this.rightChild;
		}
		//si on arrive ici c'est que le fils gauche n'est pas null. 
		//si le fils droit est null on retourne l'autre fils (le fils gauche) 
		//qui remplacera le noeud a supprimer
		if (this.rightChild == null) {
			return this.leftChild;
		}
		//si le noeud a deux fils 
		//on cherche son remplacant dans le sous arbre droit
		Node substitute = searchSubstitute(this.rightChild);
		this.key = substitute.key;
		this.rightChild = this.rightChild.delete(substitute.key);
		return this;
		
	}

	//methode de recherche du noeud substitute pour le cas noeud avec deux fils
	private Node searchSubstitute(Node courant) {
		//on est dans le sous arbre droit et on cherche le noeud le plus a gauche du sous arbre droit
		if(courant.leftChild == null) {
			return courant;
		}
		return searchSubstitute(courant.leftChild);
	}
	
}
