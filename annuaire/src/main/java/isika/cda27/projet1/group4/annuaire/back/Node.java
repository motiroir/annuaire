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
	
	@Override
	public String toString() {
		return "Node [key=" + key + ", leftChild=" + leftChild + ", rightChild=" + rightChild + ", doublon=" + doublon
				+ "]";
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
					// Naviguer à travers la chaîne des doublons
	                Node currentDoublon = nodeReader(raf, this.doublon * NODE_SIZE_OCTET);
	                while (currentDoublon.doublon != -1) {
	                    currentDoublon = nodeReader(raf, currentDoublon.doublon * NODE_SIZE_OCTET);
	                }
	                // On a trouvé le dernier doublon, ajouter le nouveau stagiaire ici
	                raf.seek(raf.getFilePointer() - DOUBLON_POSITION);
	                raf.writeInt((int) (raf.length() / NODE_SIZE_OCTET));
	                newNodeWriter(raf, stagiaire);
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
		if (this.key.getName().compareToIgnoreCase(searchedStagiaire.getName()) == 0) {
//			System.out.println("trouve");
			stagiaires.add(this.key);
			if (this.doublon != -1) {
//				System.out.println("doublon trouve");
				return nodeReader(raf, this.doublon * NODE_SIZE_OCTET).searchStagiaire(raf, searchedStagiaire,
						stagiaires);
			}
			return stagiaires;
		} else if (this.key.getName().compareToIgnoreCase(searchedStagiaire.getName()) > 0) {
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

	// suppression d'un noeud à partir de sa cle
	// premiere etape recherche du noeud a supprimer
	public int delete(RandomAccessFile raf, Stagiaire stagiaire) {
		if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) == 0) {
			// gestion des doublons
			if (this.doublon != -1) {
				int indexNoeud = this.findNodeIndex(raf);
				if (indexNoeud != -1) {
					try {
						raf.seek((indexNoeud + 1) * NODE_SIZE_OCTET - DOUBLON_POSITION);
						raf.writeInt(-1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				// Si pas de doublon, on procède à la suppression normale
				return this.deleteRoot(raf);
			}
		} else if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
			nodeReader(raf, this.leftChild * NODE_SIZE_OCTET).delete(raf, stagiaire);
		} else {
			nodeReader(raf, this.rightChild * NODE_SIZE_OCTET).delete(raf, stagiaire);
		}
		return this.findNodeIndex(raf);
	}

	private int deleteRoot(RandomAccessFile raf) {

		// si le fils gauche est nul on retourne le fils droit
		// si le fils droit est aussi null le noeud a supprimer sera remplace par null,
		// sinon le noeud a supprimer sera remplace par le fils droit
		if (this.leftChild == -1) {
			int position = this.findNodeIndex(raf);
			int indexParent = this.findParent(raf);
			Node parent = null;
			if (indexParent != -1) {
				parent = nodeReader(raf, indexParent * NODE_SIZE_OCTET);
				if (parent.leftChild == position) {
					try {
						raf.seek((indexParent + 1) * NODE_SIZE_OCTET - LEFT_CHILD_POSITION);
						raf.writeInt(this.rightChild);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						raf.seek((indexParent + 1) * NODE_SIZE_OCTET - RIGHT_CHILD_POSITION);
						raf.writeInt(this.rightChild);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return this.rightChild;
		}
		// si on arrive ici c'est que le fils gauche n'est pas null.
		// si le fils droit est null on retourne l'autre fils (le fils gauche)
		// qui remplacera le noeud a supprimer
		if (this.rightChild == -1) {
			int position = this.findNodeIndex(raf);
			int indexParent = this.findParent(raf);
			Node parent = null;
			if (indexParent != -1) {
				parent = nodeReader(raf, indexParent * NODE_SIZE_OCTET);
				if (parent.leftChild == position) {
					try {
						raf.seek((indexParent + 1) * NODE_SIZE_OCTET - LEFT_CHILD_POSITION);
						raf.writeInt(this.leftChild);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						raf.seek((indexParent + 1) * NODE_SIZE_OCTET - RIGHT_CHILD_POSITION);
						raf.writeInt(this.leftChild);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return this.leftChild;
		}

		// si le noeud a deux fils on cherche son remplacant dans le sous arbre droit
		Node rightNode = nodeReader(raf, this.rightChild * NODE_SIZE_OCTET);
		// on va chercher la position du substitut avec la méthode seachSubstitute qui
		// nous renvoie le long position du substitut
		Node substitute = searchSubstitute(raf, rightNode);

		int indexPositionARemplacer = this.findNodeIndex(raf);
		Stagiaire stagiaireSubstitute = substitute.key;

		// on supprime le noeud substitut original puisqu'il a pris la place du noeud
		// supprimé
		// on se place dans le sous-arbre droit dans le quel on a été cherché le
		// substitut
		// pour lancer la suppression du noeud utilisé en tant que substitut
		// ainsi on ne risque pas de supprimer le noeud qu'on vient de remplacer (et
		// c'est plus court)
		rightNode.delete(raf, stagiaireSubstitute);// TODO : adapter delete
		// on remplace le stagiaire à supprimer par le stagiaire remplaçant

		stagiaireWriter(raf, stagiaireSubstitute, indexPositionARemplacer);
		// on revoie la position du substitut
		return indexPositionARemplacer;
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

	public void stagiaireWriter(RandomAccessFile raf, Stagiaire stagiaire, long index) {
		try {

			raf.seek(index * NODE_SIZE_OCTET);

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

	// méthode pour récupérer la position d'un noeud
	private int findNodeIndex(RandomAccessFile raf) {
		try {
			// Commencer la recherche à partir de la racine de l'arbre (index 0)
			return this.findNodeIndexHelper(raf, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1; // Si le noeud n'est pas trouvé
	}

	private int findNodeIndexHelper(RandomAccessFile raf, int currentNodeIndex) throws IOException {
		// Lire le noeud actuel à la position spécifiée
		Node currentNode = nodeReader(raf, currentNodeIndex * NODE_SIZE_OCTET);
		// Comparer la clé du noeud actuel avec celle du noeud courant (this)
		if (currentNode.getKey().getName().compareToIgnoreCase(this.key.getName()) == 0) {
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

	// méthode pour trouver le noeud parent d'un noeud sur le même principe
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
			if (leftChildNode.getKey().getName().compareToIgnoreCase(this.key.getName()) == 0) {
				return currentNodeIndex;
			}
		}
		// Vérifier si le noeud courant (this) est l'enfant droit du noeud actuel
		if (currentNode.getRightChild() != -1) {
			Node rightChildNode = nodeReader(raf, currentNode.getRightChild() * NODE_SIZE_OCTET);
			if (rightChildNode.getKey().getName().compareToIgnoreCase(this.key.getName()) == 0) {
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

}
