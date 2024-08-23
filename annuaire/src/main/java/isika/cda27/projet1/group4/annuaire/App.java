package isika.cda27.projet1.group4.annuaire;

import isika.cda27.projet1.group4.annuaire.back.Annuaire;
import isika.cda27.projet1.group4.annuaire.back.BinaryFileManager;
import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Node;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {

		Annuaire annuaire = new Annuaire();
		annuaire.lireFichier("src/main/resources/test.txt");
		annuaire.afficherStagiaires();

		// on créé une listView pour afficher la liste de stagiaire présent dans
		// l'annuaire
		ListView<String> listView = new ListView<>();
		for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
			listView.getItems().add(annuaire.getStagiaires().get(i).toString());
		}
		// on créé un nouvel arbre
		BinarySearchTree searchTree = new BinarySearchTree();

		for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
			searchTree.ajouter(annuaire.getStagiaires().get(i));
		}

		searchTree.affichage();

		// ecriture du fichier binaire pour sauvegarde
 //       BinaryFileManager bfm = new BinaryFileManager();
 //       bfm.nodeReader("src/main/resources/save/stagiairesDataBase.bin", 0);
		// bfm.binaryFileWriter(annuaire);

//		// test écriture noeud
//		Stagiaire stagiaire = new Stagiaire("Potin", "Hervé", "44", "CDA27", 2017);
//		Node noeudTest = new Node(stagiaire);
//
//		noeudTest.newNodeWriter();

		Scene scene = new Scene(new StackPane(listView), 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}