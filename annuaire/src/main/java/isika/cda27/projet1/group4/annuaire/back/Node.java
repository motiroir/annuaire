
package isika.cda27.projet1.group4.annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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
//			System.out.println("trouve");
			stagiaires.add(this.key);
			if (this.doublon != -1) {
//				System.out.println("doublon trouve");
				return nodeReader(raf, this.doublon * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
			return stagiaires;
		} else if (this.key.getName().compareTo(searchedStagiaire.getName()) > 0) {
			if (this.leftChild == -1) {

//				System.out.println("non trouve");
				return null;
			} else {
//				System.out.println("cherche à gauche de "+this.key.getName());
				return nodeReader(raf, this.leftChild * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
		} else {
			if (this.rightChild == -1) {
//				System.out.println("non trouve");
				return null;
			} else {
//				System.out.println("cherche à droite de "+this.key.getName());
				return nodeReader(raf, this.rightChild * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
		}
	}
	
	//méthode pour récupérer la position d'un noeud 
	private int findNodeIndex(RandomAccessFile raf) {
	    try {
	        // Commencer la recherche à partir de la racine de l'arbre (index 0)
	        return findNodeIndexHelper(raf, 0);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return -1; // Si le noeud n'est pas trouvé
	}


	private int findNodeIndexHelper(RandomAccessFile raf, int currentNodeIndex) throws IOException {
	    // Lire le noeud actuel à la position spécifiée
	    Node currentNode = nodeReader(raf, currentNodeIndex * NODE_SIZE_OCTET);
	    // Comparer la clé du noeud actuel avec celle du noeud courant (this)
	    if (currentNode.getKey().equals(this.key)) {
	        return currentNodeIndex;
	    }
	    // Rechercher récursivement dans l'enfant gauche, si existant
	    if (currentNode.getLeftChild() != -1) {
	        int leftChildIndex = findNodeIndexHelper(raf, currentNode.getLeftChild());
	        if (leftChildIndex != -1) {
	            return leftChildIndex;
	        }
	    }
	    // Rechercher récursivement dans l'enfant droit, si existant
	    if (currentNode.getRightChild() != -1) {
	        int rightChildIndex = findNodeIndexHelper(raf, currentNode.getRightChild());
	        if (rightChildIndex != -1) {
	            return rightChildIndex;
	        }
	    }
	    // Si aucun noeud correspondant n'est trouvé, retourner -1
	    return -1;
	}
	//méthode pour trouver le noeud parent d'un noeud sur le même principe
	private int findParent(RandomAccessFile raf) {
	    try {
	        // Commencer la recherche à partir de la racine (index 0)
	        return findParentHelper(raf, 0);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return -1; // Si le parent n'est pas trouvé
	}

	private int findParentHelper(RandomAccessFile raf, int currentNodeIndex) throws IOException {
	    // Lire le noeud actuel à la position spécifiée
	    Node currentNode = nodeReader(raf, currentNodeIndex * NODE_SIZE_OCTET);
	    // Vérifier si le noeud courant (this) est l'enfant gauche du noeud actuel
	    if (currentNode.getLeftChild() != -1) {
	        Node leftChildNode = nodeReader(raf, currentNode.getLeftChild() * NODE_SIZE_OCTET);
	        if (leftChildNode.getKey().equals(this.key)) {
	            return currentNodeIndex;
	        }
	    }
	    // Vérifier si le noeud courant (this) est l'enfant droit du noeud actuel
	    if (currentNode.getRightChild() != -1) {
	        Node rightChildNode = nodeReader(raf, currentNode.getRightChild() * NODE_SIZE_OCTET);
	        if (rightChildNode.getKey().equals(this.key)) {
	            return currentNodeIndex;
	        }
	    }
	    // Rechercher récursivement dans l'enfant gauche, si existant
	    if (currentNode.getLeftChild() != -1) {
	        int parentIndex = findParentHelper(raf, currentNode.getLeftChild());
	        if (parentIndex != -1) {
	            return parentIndex;
	        }
	    }
	    // Rechercher récursivement dans l'enfant droit, si existant
	    if (currentNode.getRightChild() != -1) {
	        int parentIndex = findParentHelper(raf, currentNode.getRightChild());
	        if (parentIndex != -1) {
	            return parentIndex;
	        }
	    }
	    return -1; // Si aucun parent n'est trouvé
	}
	
	// suppression d'un noeud à partir de sa cle
	// premiere etape recherche du noeud a supprimer
	public Node delete(RandomAccessFile raf, Stagiaire stagiaire, long positionFinParent, boolean isLeftChild) {
		if (this.key.getName().compareTo(stagiaire.getName()) == 0) {
//				if (this.doublon != null) {
//					// Promouvoir le doublon pour remplacer le nœud actuel
//					this.key = this.doublon.getKey();
//					this.doublon = this.doublon.getDoublon();
//
//				} else {
//					// Si pas de doublon, on procède à la suppression normale
			int substituteNodeIndex = this.deleteRoot(raf, positionFinParent);

			// si le noeud à supprimer est la root
			if (positionFinParent == NODE_SIZE_OCTET) {
				Node subtsitute = nodeReader(raf, substituteNodeIndex);
				try {
					raf.seek(substituteNodeIndex);
					raf.writeChars(subtsitute.getKey().getNameLong());
					raf.writeChars(subtsitute.getKey().getFirstNameLong());
					raf.writeChars(subtsitute.getKey().getPostalCodeLong());
					raf.writeChars(subtsitute.getKey().getPromoLong());
					raf.writeInt(subtsitute.getKey().getYear());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// dans tous les cas (root ou autres)
			try {
				if (isLeftChild == true) {
					raf.seek(positionFinParent - LEFT_CHILD_POSITION);
					raf.writeInt(substituteNodeIndex);
				} else {
					raf.seek(positionFinParent - RIGHT_CHILD_POSITION);
					raf.writeInt(substituteNodeIndex);
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
				positionFinParent = raf.getFilePointer();
				System.out.println(this.getKey().getName() + " est à la position (de fin) " + positionFinParent);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// lire le fils droit
			Node leftNode = nodeReader(raf, this.leftChild * NODE_SIZE_OCTET);
			// récursivité de la méthode
			leftNode.delete(raf, stagiaire, positionFinParent, isLeftChild);
		} else {
			// on va chercher l'enfant droit
			isLeftChild = false;
			// on récupère l'adresse du noeud en cours (futur parent)
			try {
				positionFinParent = raf.getFilePointer();
				System.out.println(this.getKey().getName() + " est à la position (de fin) " + positionFinParent);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// lire le fils gauche
			Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
			// récursivité de la méthode
			rightNode.delete(raf, stagiaire, positionFinParent, isLeftChild);
		}
		return this;
	}

	private int deleteRoot(RandomAccessFile raf, long positionFinParent) {

		// si fils gauche nul
		if (this.leftChild == -1) {
			return this.rightChild;
		}
		// si fils droit nul
		if (this.rightChild == -1) {
			return this.leftChild;
		}

		// si le noeud a deux fils on cherche son remplacant dans le sous arbre droit
		Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
		// on va chercher la position du substitut avec la méthode seachSubstitute qui
		// nous renvoie le long position du substitut
		Node substitute = searchSubstitute(raf, rightNode);
		
		this.cle = remplacant.cle;
		rightNode = rightNode.delete(raf, substitute.key, positionFinParent, false);
		// on revoie la position du substitut
		return substituteIndex;

	}

	// methode de recherche du noeud substitute pour le cas noeud avec deux fils
	private Node searchSubstitute(RandomAccessFile raf, Node courant) {

		// on est dans le sous arbre droit et on cherche le noeud le plus a gauche du
		// sous arbre droit
		if (courant.leftChild == -1) {
			return courant;
		}

		// recursivité de la fonction
		Node leftNode = nodeReader(raf, courant.leftChild * NODE_SIZE_OCTET);
		return searchSubstitute(raf, leftNode);

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
	
	public void stagiaireWriter(RandomAccessFile raf, Stagiaire stagiaire, long position) {
		try {
			
			raf.seek(position);
			
			raf.writeChars(stagiaire.getNameLong());
			raf.writeChars(stagiaire.getFirstNameLong());
			raf.writeChars(stagiaire.getPostalCodeLong());
			raf.writeChars(stagiaire.getPromoLong());
			raf.writeInt(stagiaire.getYear());

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
