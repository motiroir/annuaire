package isika.cda27.projet1.group4.annuaire.back;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe BinarySearchTree représente un arbre binaire de recherche qui stocke des objets de type Stagiaire.
 * Cet arbre est persistant, avec les données stockées dans un fichier binaire. La classe fournit des méthodes
 * pour ajouter, supprimer, rechercher, et équilibrer les éléments de l'arbre.
 */
public class BinarySearchTree {
    public Node root;
    public RandomAccessFile raf;

    /**
     * Constructeur par défaut de la classe BinarySearchTree.
     * Initialise la racine de l'arbre avec un nœud vide et crée un fichier binaire pour stocker les données des stagiaires.
     */
    public BinarySearchTree() {
        root = new Node(null);
        try {
            raf = new RandomAccessFile("src/main/resources/save/stagiairesDataBase.bin", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur de la classe BinarySearchTree avec une racine spécifiée.
     *
     * @param root La racine initiale de l'arbre.
     */
    public BinarySearchTree(Node root) {
        super();
        this.root = root;
    }

    /**
     * Retourne la racine de l'arbre binaire de recherche.
     *
     * @return Le nœud racine de l'arbre.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Définit la racine de l'arbre binaire de recherche.
     *
     * @param root Le nouveau nœud racine de l'arbre.
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Vérifie si l'arbre binaire de recherche est vide.
     *
     * @return true si l'arbre est vide, false sinon.
     */
    public boolean isEmpty() {
        return (this.root == null);
    }

    /**
     * Ajoute un stagiaire à l'arbre binaire de recherche.
     * Si l'arbre est vide, le stagiaire devient la racine. Sinon, il est ajouté à l'emplacement approprié.
     *
     * @param stagiaire Le stagiaire à ajouter à l'arbre.
     */
    public void ajouter(Stagiaire stagiaire) {
        try {
            if (raf.length() == 0) {
                this.root = new Node(stagiaire);
                this.root.newNodeWriter(raf, stagiaire);
            } else {
                this.root = this.root.nodeReader(raf, 0);
                this.root.addNode(raf, stagiaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche les stagiaires de l'arbre binaire de recherche dans l'ordre alphabétique.
     *
     * @return Une liste de stagiaires triée par ordre alphabétique.
     */
    public List<Stagiaire> affichage() {
        List<Stagiaire> stagiaires = new ArrayList<>();
        try {
            if (raf.length() == 0) {
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

    /**
     * Recherche un stagiaire dans l'arbre binaire de recherche.
     *
     * @param searchedStagiaire Le stagiaire à rechercher.
     * @return Une liste de stagiaires correspondant au stagiaire recherché.
     */
    public List<Stagiaire> searchStagiaireInTree(Stagiaire searchedStagiaire) {
        List<Stagiaire> stagiairesFound = new ArrayList<>();
        try {
            if (raf.length() == 0) {
                System.out.println("L'arbre est vide");
            } else {
                this.root = this.root.nodeReader(raf, 0);
                this.root.searchStagiaire(raf, searchedStagiaire, stagiairesFound);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stagiairesFound;
    }

    /**
     * Supprime un stagiaire de l'arbre binaire de recherche.
     *
     * @param stagiaire Le stagiaire à supprimer.
     */
    public void deleteInTree(Stagiaire stagiaire) {
        try {
            if (raf.length() == 0) {
                System.out.println("l'arbre est vide");
            } else {
                this.root = this.root.nodeReader(raf, 0);
                this.root.delete(raf, stagiaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un stagiaire dans l'arbre binaire de recherche.
     * L'ancien stagiaire est supprimé et le nouveau est ajouté à sa place.
     *
     * @param oldStagiaire L'ancien stagiaire à remplacer.
     * @param newStagiaire Le nouveau stagiaire à ajouter.
     */
    public void updateInTree(Stagiaire oldStagiaire, Stagiaire newStagiaire) {
        try {
            if (raf.length() == 0) {
                System.out.println("l'arbre est vide");
            } else {
                this.root.delete(raf, oldStagiaire);
                this.root.addNode(raf, newStagiaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Équilibre l'arbre binaire de recherche pour optimiser les performances.
     * Les stagiaires sont réorganisés dans un ordre d'insertion équilibré.
     */
    public void balanceTree() {
        List<Stagiaire> balancedList = this.balancedList();
        try {
            new FileOutputStream("src/main/resources/save/stagiairesDataBase.bin").close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Stagiaire stag : balancedList) {
            ajouter(stag);
        }
    }

    /**
     * Crée une liste équilibrée de stagiaires pour optimiser l'ordre d'insertion dans l'arbre.
     *
     * @return Une liste équilibrée de stagiaires.
     */
    private List<Stagiaire> balancedList() {
        List<Stagiaire> originalList = this.affichage();
        List<Stagiaire> balancedList = new ArrayList<>();
        addBalanced(originalList, balancedList, 0, originalList.size() - 1);
        return balancedList;
    }

    /**
     * Ajoute des éléments à une liste équilibrée en utilisant une approche récursive.
     *
     * @param originalList  La liste originale de stagiaires triée.
     * @param balancedList  La liste équilibrée en cours de création.
     * @param start         L'index de début pour l'ajout.
     * @param end           L'index de fin pour l'ajout.
     */
    private void addBalanced(List<Stagiaire> originalList, List<Stagiaire> balancedList, int start, int end) {
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;
        balancedList.add(originalList.get(mid));

        addBalanced(originalList, balancedList, start, mid - 1);
        addBalanced(originalList, balancedList, mid + 1, end);
    }
}
