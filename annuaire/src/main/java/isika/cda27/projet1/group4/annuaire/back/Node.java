package isika.cda27.projet1.group4.annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Node {

	private Stagiaire key;
	private int rightChild;
	private int leftChild;
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

	public Node(Stagiaire key, int rightChild, int leftChild, int doublon) {
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
		// ici on veut lire dans le fichier binaire l'objet au lieu de travailler
		// avec l'objet "virtuel"
		try {
			if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
				if (this.leftChild == -1) {
					// ici écrire la position de l'enfant gauche dans la case enfant gauche du
					// parent
					raf.seek(raf.getFilePointer() - LEFT_CHILD_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));

					// créer (écrire dans le fichier binaire) l'enfant gauche de this à la fin du
					// fichier
					newNodeWriter(raf, stagiaire);

				} else {
					Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
					leftNode.addNode(raf, stagiaire);
				}
			} else if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) < 0) {
				if (this.rightChild == -1) {
					raf.seek(raf.getFilePointer() - RIGHT_CHILD_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
				} else {
					Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
					rightNode.addNode(raf, stagiaire);
				}
			} else {
				if (doublon == -1) {
					raf.seek(raf.getFilePointer() - DOUBLON_POSITION);
					raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
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
	public void read(RandomAccessFile raf) {
		try {
		if (this.leftChild != -1) {
			raf.seek(raf.getFilePointer() - LEFT_CHILD_POSITION);
			nodeReader(raf, raf.readInt()* NODE_SIZE_OCTET).read(raf);
		}
		System.out.println(this.key.toString());
		if (this.doublon != -1) {
			raf.seek(raf.getFilePointer() - DOUBLON_POSITION);
			nodeReader(raf, raf.readInt()* NODE_SIZE_OCTET).read(raf);
			System.out.println(this.key.toString());
		}
		
		if (this.rightChild != -1) {
			raf.seek(raf.getFilePointer() - RIGHT_CHILD_POSITION);
			nodeReader(raf, raf.readInt()* NODE_SIZE_OCTET).read(raf);
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

//	// recherche d'un noeud par sa cle
//	public Stagiaire searchStagiaire(Stagiaire searchedStagiaire) {
//		if (this.key.getName().compareTo(searchedStagiaire.getName()) == 0) {
//			return this.key;
//		} else if (this.key.getName().compareTo(searchedStagiaire.getName()) > 0) {
//			return this.leftChild.searchStagiaire(searchedStagiaire);
//		} else {
//			return this.rightChild.searchStagiaire(searchedStagiaire);
//		}
//	}

//	// suppression d'un noeud à partir de sa cle
//	// premiere etape recherche du noeud a supprimer
//	public Node delete(Stagiaire stagiaire) {
//		if (this.key.getName().compareTo(stagiaire.getName()) == 0) {
//
//			if (this.doublon != null) {
//				// Promouvoir le doublon pour remplacer le nœud actuel
//				this.key = this.doublon.getKey();
//				this.doublon = this.doublon.getDoublon();
//
//			} else {
//				// Si pas de doublon, on procède à la suppression normale
//				return this.deleteRoot();
//			}
//		} else if (this.key.getName().compareTo(stagiaire.getName()) > 0) {
//			this.leftChild = this.leftChild.delete(stagiaire);
//		} else {
//			this.rightChild = this.rightChild.delete(stagiaire);
//		}
//		return this;
//	}

//	// methode de suppression
//	// deuxieme etape suppression du noeud une fois trouve
//	// le noeud a supprimer a ete trouve c'est "this"
//	private Node deleteRoot() {
//
//		// si le fils gauche est nul on retourne le fils droit
//		// si le fils droit est aussi null le noeud a supprimer sera remplace par null,
//		// sinon le noeud a supprimer sera remplace par le fils droit
//		if (this.leftChild == null) {
//			return this.rightChild;
//		}
//		// si on arrive ici c'est que le fils gauche n'est pas null.
//		// si le fils droit est null on retourne l'autre fils (le fils gauche)
//		// qui remplacera le noeud a supprimer
//		if (this.rightChild == null) {
//			return this.leftChild;
//		}
//		// si le noeud a deux fils
//		// on cherche son remplacant dans le sous arbre droit
//		Node substitute = searchSubstitute(this.rightChild);
//		this.key = substitute.key;
//		this.rightChild = this.rightChild.delete(substitute.key);
//		return this;
//
//	}
//
//	// methode de recherche du noeud substitute pour le cas noeud avec deux fils
//	private Node searchSubstitute(Node courant) {
//		// on est dans le sous arbre droit et on cherche le noeud le plus a gauche du
//		// sous arbre droit
//		if (courant.leftChild == null) {
//			return courant;
//		}
//		return searchSubstitute(courant.leftChild);
//	}

	// Binary File Writer
	public void newNodeWriter(RandomAccessFile raf, Stagiaire stagiaire) {
		try {

			// se mettre à la fin du fichier = pos
			raf.seek(raf.length());

			raf.writeChars(stagiaire.getNameLong());
			raf.writeChars(stagiaire.getFirstNameLong());
			raf.writeChars(stagiaire.getPostalCodeLong());
			raf.writeChars(stagiaire.getPromoLong());
			raf.writeInt(stagiaire.getYear());
			raf.writeInt(-1);
			raf.writeInt(-1);
			raf.writeInt(-1);
			// pos + 120
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Node nodeReader(RandomAccessFile raf, int position) {
		// notes : remplacer position par un int que l'on multiplie par taille objet
		// pour ne tomber que sur des débuts d'objets
		// attention getFilePointer() retourne un long
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

			// affichage en console
			//System.out.println(name + firstName + postalCode + promo + year + leftChild + rightChild + doublon);

			Stagiaire stagiaire = new Stagiaire(name, firstName, postalCode, promo, year);
			newNode = new Node(stagiaire, leftChild, rightChild, doublon);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return newNode;
	}

}
