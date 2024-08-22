package isika.cda27.projet1.group4.annuaire.back;

public class Node {
	
	private Stagiaire key;
	private Node rightChild;
	private Node leftChild;
	
	public Node(Stagiaire key) {
		this.key = key;
		this.rightChild = null;
		this.leftChild = null;
	}
	
	//Ajout d'un noeud
	public void addNode(Stagiaire stagiaire) {
		if(this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
			if(this.leftChild == null) {
				this.leftChild = new Node(stagiaire);
			} else {
				this.leftChild.addNode(stagiaire);
			}
		} else {
			if(this.rightChild == null) {
				this.rightChild = new Node(stagiaire);
			} else {
				this.rightChild.addNode(stagiaire);
			}
		}
	}
	
	//parcours infixe (affichage par ordre alphabetique)
	//le parcours infixe Gauche Node Droit
	public void read() {
		if (this.leftChild != null) {
			this.leftChild.read();
		}
		System.out.println(this.key.toString());
		if(this.rightChild != null) {
			this.rightChild.read();
		}
	}

}
