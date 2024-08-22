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

}
