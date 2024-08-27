package isika.cda27.projet1.group4.annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Random;

public class Node {

	private Stagiaire key;
	private int leftChild;
	private int rightChild;
	private int doublon;
	public static final int NODE_SIZE_OCTET = Stagiaire.STAGIAIRE_SIZE_OCTET + 12;
	public static final int LEFT_CHILD_POSITION = 12;
	public static final int RIGHT_CHILD_POSITION = 8;
	public static final int DOUBLON_POSITION = 4;

	public Node(Stagiaire key) {
		this.key = key;
		this.rightChild = -1;
		this.leftChild = -1;
		this.doublon = -1;
	}

	public Node(Stagiaire key, int leftChild, int rightChild, int doublon) {
		super();
		this.key = key;
		this.rightChild = rightChild;
		this.leftChild = leftChild;
		this.doublon = doublon;
	}

	public Stagiaire getKey() {
		return key;
	}

	public void setKey(Stagiaire key) {
		this.key = key;
	}

	public int getRightChild() {
		return rightChild;
	}

	public void setRightChild(int rightChild) {
		this.rightChild = rightChild;
	}

	public int getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(int leftChild) {
		this.leftChild = leftChild;
	}

	public int getDoublon() {
		return doublon;
	}

	public void setDoublon(int doublon) {
		this.doublon = doublon;
	}

	// Ajout d'un noeud
	public void addNode(RandomAccessFile raf, Stagiaire stagiaire) {
		try {
			if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
				if (this.leftChild == -1) {
					raf.seek(raf.getFilePointer() - LEFT_CHILD_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
					newNodeWriter(raf, stagiaire);
				} else {
					Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
					leftNode.addNode(raf, stagiaire);
				}
			} else if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) < 0) {
				if (this.rightChild == -1) {
					raf.seek(raf.getFilePointer() - RIGHT_CHILD_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
					newNodeWriter(raf, stagiaire);
				} else {
					Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
					rightNode.addNode(raf, stagiaire);
				}
			} else {
				if (doublon == -1) {
					raf.seek(raf.getFilePointer() - DOUBLON_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
					newNodeWriter(raf, stagiaire);
				} else {
					Node doublonNode = nodeReader(raf, this.doublon * NODE_SIZE_OCTET);
					doublonNode.addNode(raf, stagiaire);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// parcours infixe (affichage par ordre alphabetique)
	// le parcours infixe Gauche Node Droit
	public void read(RandomAccessFile raf, List<Stagiaire> stagiaires) {
		if (this.leftChild != -1) {
			Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
			if (leftNode != null) {
				leftNode.read(raf, stagiaires);
			}
		}
		System.out.println(this.key.toString() + ", LC : " + this.leftChild + ", RC : " + this.rightChild + ", D : "
				+ this.doublon);
		stagiaires.add(this.key);
		if (this.doublon != -1) {
			Node doublonNode = nodeReader(raf, this.doublon * NODE_SIZE_OCTET);
			if (doublonNode != null) {
				doublonNode.read(raf, stagiaires);
			}
		}
		if (this.rightChild != -1) {
			Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
			if (rightNode != null) {
				rightNode.read(raf, stagiaires);
			}
		}
	}

	// recherche d'un noeud par sa cle
	public List<Stagiaire> searchStagiaire(RandomAccessFile raf, Stagiaire searchedStagiaire,
			List<Stagiaire> stagiaires) {
		if (this.key.getName().compareTo(searchedStagiaire.getName()) == 0) {
//				System.out.println("trouve");
			stagiaires.add(this.key);
			if (this.doublon != -1) {
//					System.out.println("doublon trouve");
				return nodeReader(raf, this.doublon * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
			return stagiaires;
		} else if (this.key.getName().compareTo(searchedStagiaire.getName()) > 0) {
			if (this.leftChild == -1) {
//					System.out.println("non trouve");
				return null;
			} else {
//					System.out.println("cherche à gauche de "+this.key.getName());
				return nodeReader(raf, this.leftChild * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
		} else {
			if (this.rightChild == -1) {
//					System.out.println("non trouve");
				return null;
			} else {
//					System.out.println("cherche à droite de "+this.key.getName());
				return nodeReader(raf, this.rightChild * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
		}
	}

	// suppression d'un noeud à partir de sa cle
	// premiere etape recherche du noeud a supprimer
	public Node delete(RandomAccessFile raf, Stagiaire stagiaire, long indexFinParent, boolean isLeftChild) {
		if (this.key.getName().compareTo(stagiaire.getName()) == 0) {
//				if (this.doublon != null) {
//					// Promouvoir le doublon pour remplacer le nœud actuel
//					this.key = this.doublon.getKey();
//					this.doublon = this.doublon.getDoublon();
//
//				} else {
//					// Si pas de doublon, on procède à la suppression normale

			try {
				int substituteNodePosition = this.deleteRoot(raf);
				if (isLeftChild == true) {
					raf.seek(indexFinParent - LEFT_CHILD_POSITION);
					raf.writeInt(substituteNodePosition);
				} else {
					raf.seek(indexFinParent - RIGHT_CHILD_POSITION);
					raf.writeInt(substituteNodePosition);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

//				}

		} else if (this.key.getName().compareTo(stagiaire.getName()) > 0) {
			// on va chercher l'enfant gauche
			isLeftChild = true;
			// on récupère l'adresse du noeud en cours (futur parent)
			try {
				indexFinParent = raf.getFilePointer();
				System.out.println(this.getKey().getName() + " est à l'index (de fin) " + indexFinParent);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// lire le fils droit
			Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
			// récursivité de la méthode
			leftNode.delete(raf, stagiaire, indexFinParent, isLeftChild);
		} else {
			// on va chercher l'enfant droit
			isLeftChild = false;
			// on récupère l'adresse du noeud en cours (futur parent)
			try {
				indexFinParent = raf.getFilePointer();
				System.out.println(this.getKey().getName() + " est à l'index (de fin) " + indexFinParent);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// lire le fils gauche
			Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
			// récursivité de la méthode
			rightNode.delete(raf, stagiaire, indexFinParent, isLeftChild);
		}
		return this;
	}

	// methode de suppression
	// deuxieme etape suppression du noeud une fois trouve
	// le noeud a supprimer a ete trouve c'est "this"
	private int deleteRoot(RandomAccessFile raf) {

		// si le fils gauche est nul on retourne le fils droit
		// si le fils droit est aussi null le noeud a supprimer sera remplace par null,
		// sinon le noeud a supprimer sera remplace par le fils droit
		if (this.leftChild == -1) {
			return this.rightChild;
		}
		// si on arrive ici c'est que le fils gauche n'est pas null.
		// si le fils droit est null on retourne l'autre fils (le fils gauche)
		// qui remplacera le noeud a supprimer
		if (this.rightChild == -1) {
			return this.leftChild;
		}
		// si le noeud a deux fils
		// on cherche son remplacant dans le sous arbre droit
		Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
		int subsitutePosition=0;
		try {
			searchSubstitute(raf, rightNode, raf.getFilePointer());
			subsitutePosition = (int) ((raf.getFilePointer() - NODE_SIZE_OCTET) / NODE_SIZE_OCTET);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return subsitutePosition;

	}

	// methode de recherche du noeud substitute pour le cas noeud avec deux fils
	private Node searchSubstitute(RandomAccessFile raf, Node courant, long indexFinParent) {
		// on est dans le sous arbre droit et on cherche le noeud le plus a gauche du
		// sous arbre droit
		try {
			if (courant.leftChild == -1) {
				// on vient écrire dans le parent qu'il n'aura plus d'enfant (-1)
				raf.seek(indexFinParent - LEFT_CHILD_POSITION);
				raf.writeInt(-1);
				// on renvoie le noeud enfant
				return courant;
			}
			// on change l'index du parent
			indexFinParent = raf.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// recursivité de la fonction
		Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
		return searchSubstitute(raf, leftNode, indexFinParent);

	}

	// Binary File Writer
	public void newNodeWriter(RandomAccessFile raf, Stagiaire stagiaire) {
		try {

			// se mettre à la fin du fichier
			raf.seek(raf.length());

			raf.writeChars(stagiaire.getNameLong());
			raf.writeChars(stagiaire.getFirstNameLong());
			raf.writeChars(stagiaire.getPostalCodeLong());
			raf.writeChars(stagiaire.getPromoLong());
			raf.writeInt(stagiaire.getYear());
			raf.writeInt(-1);
			raf.writeInt(-1);
			raf.writeInt(-1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Node nodeReader(RandomAccessFile raf, int position) {

		Node newNode = null;

		try {

			raf.seek(position);
			String name = "";
			String firstName = "";
			String postalCode = "";
			String promo = "";
			int year = 0;
			int leftChild;
			int rightChild;
			int doublon;

			for (int i = 0; i < Stagiaire.NAME_SIZE; i++) {
				name += raf.readChar();
			}

			for (int i = 0; i < Stagiaire.FIRSTNAME_SIZE; i++) {
				firstName += raf.readChar();
			}

			for (int i = 0; i < Stagiaire.POSTALCODE_SIZE; i++) {
				postalCode += raf.readChar();
			}

			for (int i = 0; i < Stagiaire.PROMO_SIZE; i++) {
				promo += raf.readChar();
			}

			year += raf.readInt();
			leftChild = raf.readInt();
			rightChild = raf.readInt();
			doublon = raf.readInt();

			Stagiaire stagiaire = new Stagiaire(name.trim(), firstName.trim(), postalCode.trim(), promo.trim(), year);
			newNode = new Node(stagiaire, leftChild, rightChild, doublon);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return newNode;
	}

	@Override
	public String toString() {
		return "Node [key=" + key + ", leftChild=" + leftChild + ", rightChild=" + rightChild + ", doublon=" + doublon
				+ "]";
	}

}
