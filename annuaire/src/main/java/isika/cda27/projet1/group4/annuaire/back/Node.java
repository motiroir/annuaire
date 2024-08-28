package isika.cda27.projet1.group4.annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe Node représente un nœud dans un arbre binaire qui stocke des objets
 * de type {@link Stagiaire}. Chaque nœud contient un stagiaire, ainsi que des
 * références à ses enfants gauche et droit, et à un doublon.
 * <p>
 * Les nœuds sont stockés dans un fichier binaire, où chaque nœud est de taille
 * fixe. La classe fournit des méthodes pour ajouter, lire, rechercher et
 * supprimer des nœuds dans cet arbre.
 */
public class Node {

    private Stagiaire key;
    private int leftChild;
    private int rightChild;
    private int doublon;
    public static final int NODE_SIZE_OCTET = Stagiaire.STAGIAIRE_SIZE_OCTET + 12;
    public static final int LEFT_CHILD_POSITION = 12;
    public static final int RIGHT_CHILD_POSITION = 8;
    public static final int DOUBLON_POSITION = 4;

    /**
     * Constructeur de la classe Node qui initialise un nœud avec une clé donnée.
     * Les références aux enfants et aux doublons sont initialisées à -1 (null).
     *
     * @param key Le stagiaire à stocker dans ce nœud.
     */
    public Node(Stagiaire key) {
        this.key = key;
        this.rightChild = -1;
        this.leftChild = -1;
        this.doublon = -1;
    }

    /**
     * Constructeur de la classe Node qui initialise un nœud avec une clé, ainsi
     * que des références explicites aux enfants gauche et droit, et aux doublons.
     *
     * @param key        Le stagiaire à stocker dans ce nœud.
     * @param leftChild  L'index du nœud enfant gauche.
     * @param rightChild L'index du nœud enfant droit.
     * @param doublon    L'index du nœud doublon.
     */
    public Node(Stagiaire key, int leftChild, int rightChild, int doublon) {
        super();
        this.key = key;
        this.rightChild = rightChild;
        this.leftChild = leftChild;
        this.doublon = doublon;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de ce nœud.
     *
     * @return Une chaîne de caractères représentant ce nœud.
     */
    @Override
    public String toString() {
        return "Node [key=" + key + ", leftChild=" + leftChild + ", rightChild=" + rightChild + ", doublon=" + doublon
                + "]";
    }

    // Getters et setters pour les attributs du nœud

    /**
     * Retourne la clé (stagiaire) de ce nœud.
     *
     * @return Le stagiaire stocké dans ce nœud.
     */
    public Stagiaire getKey() {
        return key;
    }

    /**
     * Définit la clé (stagiaire) de ce nœud.
     *
     * @param key Le stagiaire à stocker dans ce nœud.
     */
    public void setKey(Stagiaire key) {
        this.key = key;
    }

    /**
     * Retourne l'index de l'enfant droit de ce nœud.
     *
     * @return L'index de l'enfant droit.
     */
    public int getRightChild() {
        return rightChild;
    }

    /**
     * Définit l'index de l'enfant droit de ce nœud.
     *
     * @param rightChild L'index de l'enfant droit.
     */
    public void setRightChild(int rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Retourne l'index de l'enfant gauche de ce nœud.
     *
     * @return L'index de l'enfant gauche.
     */
    public int getLeftChild() {
        return leftChild;
    }

    /**
     * Définit l'index de l'enfant gauche de ce nœud.
     *
     * @param leftChild L'index de l'enfant gauche.
     */
    public void setLeftChild(int leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Retourne l'index du nœud doublon de ce nœud.
     *
     * @return L'index du nœud doublon.
     */
    public int getDoublon() {
        return doublon;
    }

    /**
     * Définit l'index du nœud doublon de ce nœud.
     *
     * @param doublon L'index du nœud doublon.
     */
    public void setDoublon(int doublon) {
        this.doublon = doublon;
    }

    /**
     * Ajoute un nœud à l'arbre binaire en fonction de la clé du stagiaire donné.
     *
     * @param raf       Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param stagiaire Le stagiaire à ajouter dans l'arbre.
     */
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

    /**
     * Lit et récupère les nœuds de l'arbre dans un ordre infixe (Gauche, Nœud,
     * Droit).
     *
     * @param raf        Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param stagiaires La liste où les stagiaires seront ajoutés.
     */
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

	 /**
     * Recherche un stagiaire dans l'arbre binaire par sa clé.
     *
     * @param raf               Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param searchedStagiaire Le stagiaire recherché.
     * @param stagiaires        La liste des stagiaires trouvés.
     * @return Une liste des stagiaires correspondant à la clé recherchée.
     */
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

	  /**
     * Supprime un nœud de l'arbre en fonction de la clé du stagiaire donné.
     *
     * @param raf       Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param stagiaire Le stagiaire à supprimer de l'arbre.
     * @return L'index du nœud supprimé.
     */
	public int delete(RandomAccessFile raf, Stagiaire stagiaire) {
		if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) == 0) {
			// gestion des doublons
			if (this.doublon != -1) {
				Node currentDoublon = nodeReader(raf, this.doublon * NODE_SIZE_OCTET);

				if (this.key.equals(stagiaire)) {
					// Si le noeud à supprimer est le noeud actuel (this) et qu'il a des doublons
					int thisIndex = this.findNodeIndex(raf);
					try {
						// Copier les données du premier doublon dans ce noeud
						stagiaireWriter(raf, currentDoublon.key, thisIndex);

						// Mettre à jour le pointeur de doublon pour qu'il pointe vers le doublon
						// suivant
						raf.seek(thisIndex * NODE_SIZE_OCTET + NODE_SIZE_OCTET - DOUBLON_POSITION);
						raf.writeInt(currentDoublon.doublon);

						// Supprimer le doublon qui vient d'être déplacé en mettant à jour le pointeur
						// de l'ancien doublon
						raf.seek(this.doublon * NODE_SIZE_OCTET + NODE_SIZE_OCTET - DOUBLON_POSITION);
						raf.writeInt(-1); // Effacer le pointeur de doublon pour ce noeud déplacé

					} catch (IOException e) {
						e.printStackTrace();
					}
					return thisIndex;
				}

				// Parcourir la chaîne des doublons
				Node previousNode = this;
				while (currentDoublon != null) {
					if (currentDoublon.key.equals(stagiaire)) {
						// Supprimer le doublon en rétablissant les liens
						previousNode.doublon = currentDoublon.doublon;

						// Mettre à jour le fichier pour supprimer le doublon
						try {
							raf.seek((previousNode.findNodeIndex(raf) + 1) * NODE_SIZE_OCTET - DOUBLON_POSITION);
							raf.writeInt(previousNode.doublon);
						} catch (IOException e) {
							e.printStackTrace();
						}

						return previousNode.findNodeIndex(raf);
					}

					// Passer au prochain doublon
					previousNode = currentDoublon;
					if (currentDoublon.doublon != -1) {
						currentDoublon = nodeReader(raf, currentDoublon.doublon * NODE_SIZE_OCTET);
					} else {
						currentDoublon = null;
					}
				}
			} else {
				// Si pas de doublon, on procède à la suppression normale
				return this.deleteRoot(raf);
			}
		} else if (this.key.getName().compareToIgnoreCase(stagiaire.getName()) > 0) {
			nodeReader(raf, this.leftChild * NODE_SIZE_OCTET).delete(raf, stagiaire);
		} else {
			if (this.rightChild != -1)
				nodeReader(raf, this.rightChild * NODE_SIZE_OCTET).delete(raf, stagiaire);
		}
		return this.findNodeIndex(raf);
	}

    /**
     * Méthode privée pour supprimer la racine de l'arbre.
     *
     * @param raf Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @return L'index du nœud remplacé.
     */
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

	   /**
     * Méthode privée pour rechercher le nœud substitut lors de la suppression d'un
     * nœud avec deux enfants.
     *
     * @param raf     Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param courant Le nœud courant dans la recherche du substitut.
     * @return Le nœud substitut trouvé.
     */
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

	 /**
     * Écrit un nouveau nœud à la fin du fichier binaire.
     *
     * @param raf       Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param stagiaire Le stagiaire à ajouter comme nouveau nœud.
     */
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

	 /**
     * Écrit les informations d'un stagiaire dans un nœud existant dans le fichier
     * binaire.
     *
     * @param raf       Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param stagiaire Le stagiaire à écrire.
     * @param index     L'index où écrire les informations du stagiaire.
     */
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

    /**
     * Lit un nœud à partir d'une position spécifiée dans le fichier binaire.
     * <p>
     * Cette méthode lit les données binaires à la position donnée dans le fichier
     * et les convertit en un objet {@link Node}. Elle extrait les informations
     * concernant le stagiaire ainsi que les indices des enfants gauche et droit,
     * et le doublon éventuel.
     *
     * @param raf      Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param position La position à laquelle lire le nœud dans le fichier.
     * @return Le nœud lu à la position spécifiée, ou {@code null} en cas d'erreur.
     */
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

	 /**
     * Trouve l'index d'un nœud dans le fichier en cherchant un nœud correspondant
     * à la clé du nœud courant.
     * <p>
     * Cette méthode commence la recherche à partir de la racine de l'arbre et
     * utilise une recherche récursive pour trouver l'index du nœud dont la clé
     * correspond à celle du nœud courant.
     *
     * @param raf Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @return L'index du nœud correspondant, ou {@code -1} si le nœud n'est pas trouvé.
     */
	private int findNodeIndex(RandomAccessFile raf) {
		try {
			// Commencer la recherche à partir de la racine de l'arbre (index 0)
			return this.findNodeIndexHelper(raf, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1; // Si le noeud n'est pas trouvé
	}

	 /**
     * Recherche récursivement l'index d'un nœud correspondant à la clé du nœud
     * courant en utilisant un index de nœud spécifié.
     * <p>
     * Cette méthode lit chaque nœud à partir du fichier et compare sa clé avec celle
     * du nœud courant. Elle recherche également les doublons et les enfants gauche et droit.
     *
     * @param raf               Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param currentNodeIndex  L'index du nœud actuel dans la recherche.
     * @return L'index du nœud correspondant, ou {@code -1} si le nœud n'est pas trouvé.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
	private int findNodeIndexHelper(RandomAccessFile raf, int currentNodeIndex) throws IOException {
		// Lire le noeud actuel à la position spécifiée
		Node currentNode = nodeReader(raf, currentNodeIndex * NODE_SIZE_OCTET);
		// Comparer la clé du noeud actuel avec celle du noeud courant (this)
		if (currentNode.getKey().getName().compareToIgnoreCase(this.key.getName()) == 0) {
			// Si les noms correspondent, vérifier si les objets sont égaux avec equals()
			if (currentNode.getKey().equals(this.key)) {
				return currentNodeIndex;
			} else {
				// Parcourir les doublons pour trouver l'exacte correspondance avec equals()
				int doublonIndex = currentNode.getDoublon();
				while (doublonIndex != -1) {
					Node doublonNode = nodeReader(raf, doublonIndex * NODE_SIZE_OCTET);
					if (doublonNode.getKey().equals(this.key)) {
						return doublonIndex;
					}
					doublonIndex = doublonNode.getDoublon();
				}
			}
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

	 /**
     * Trouve l'index du parent du nœud courant dans le fichier.
     * <p>
     * Cette méthode commence la recherche à partir de la racine de l'arbre et
     * utilise une recherche récursive pour déterminer l'index du parent du nœud
     * courant.
     *
     * @param raf Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @return L'index du parent du nœud courant, ou {@code -1} si le parent n'est pas trouvé.
     */
	private int findParent(RandomAccessFile raf) {
		try {
			// Commencer la recherche à partir de la racine (index 0)
			return findParentHelper(raf, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1; // Si le parent n'est pas trouvé
	}

	/**
     * Recherche récursivement l'index du parent du nœud courant dans le fichier.
     * <p>
     * Cette méthode lit chaque nœud à partir du fichier et vérifie si le nœud courant
     * est un enfant gauche ou droit d'un nœud donné. Elle continue la recherche dans
     * les enfants gauche et droit.
     *
     * @param raf               Le fichier d'accès aléatoire où les nœuds sont stockés.
     * @param currentNodeIndex  L'index du nœud actuel dans la recherche.
     * @return L'index du parent du nœud courant, ou {@code -1} si le parent n'est pas trouvé.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
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
