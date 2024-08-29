package isika.cda27.projet1.group4.annuaire.front;

import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;

/**
 * La classe AnnuaireDAO est responsable de la gestion des opérations CRUD
 * (Create, Read, Update, Delete) sur les stagiaires dans un annuaire,
 * en utilisant une structure de données de type BinarySearchTree.
 */
public class AnnuaireDAO {
    /**
     * Arbre binaire de recherche utilisé pour stocker et gérer les stagiaires.
     */
    public BinarySearchTree searchTree;

    /**
     * Constructeur par défaut de la classe AnnuaireDAO.
     * Initialise un nouvel arbre binaire de recherche.
     */
    public AnnuaireDAO() {
        super();
        this.searchTree = new BinarySearchTree();
    }

    /**
     * Recherche les stagiaires par nom dans l'annuaire.
     *
     * @param name Le nom du stagiaire à rechercher.
     * @return Une liste de stagiaires correspondant au nom donné.
     */
    public List<Stagiaire> searchByName(String name) {
        return searchTree.searchStagiaireInTree(new Stagiaire(name));
    }

    
    /**
     * Récupère la liste complète des stagiaires dans l'annuaire.
     *
     * @return Une liste de tous les stagiaires présents dans l'annuaire.
     */
    public List<Stagiaire> getStagiaires() {
        return searchTree.affichage();
    }

    /**
     * Ajoute un stagiaire dans l'annuaire.
     *
     * @param stagiaire Le stagiaire à ajouter.
     */
    public void addStagiaire(Stagiaire stagiaire) {
        searchTree.ajouter(stagiaire);
    }

    /**
     * Supprime un stagiaire de l'annuaire.
     *
     * @param stagiaire Le stagiaire à supprimer.
     */
    public void removeStagiaire(Stagiaire stagiaire) {
        searchTree.deleteInTree(stagiaire);
    }

    /**
     * Met à jour les informations d'un stagiaire dans l'annuaire.
     *
     * @param oldStagiaire L'ancien stagiaire à mettre à jour.
     * @param newStagiaire Le nouveau stagiaire avec les informations mises à jour.
     */
    public void updateStagiaire(Stagiaire oldStagiaire, Stagiaire newStagiaire) {
        searchTree.updateInTree(oldStagiaire, newStagiaire);
    }
}