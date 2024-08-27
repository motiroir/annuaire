package isika.cda27.projet1.group4.annuaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

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

		// on créé un nouvel arbre
		BinarySearchTree searchTree = new BinarySearchTree();

//		for (int i = 0; i < annuaire.getStagiaires().size(); i++) {
//			searchTree.ajouter(annuaire.getStagiaires().get(i));
//		}

		ListView<Stagiaire> listView = new ListView<Stagiaire>();
		List<Stagiaire> stagiaires = searchTree.affichage();

		searchTree.deleteInTree(stagiaires.get(2));

		searchTree.affichage();
		
		for (Stagiaire stag : stagiaires) {
			listView.getItems().add(stag);
		}

//		// test recherche d'un stagiaire par nom
//		Stagiaire test = new Stagiaire("ROIGNANT", "", "", "", 0);
//		System.out.println("\nTest du stagiaire à trouver sur " + test.getName());
//		List<Stagiaire> stagiairesSearched = searchTree.searchStagiaireInTree(test);
//		for (Stagiaire stag : stagiairesSearched) {
//			System.out.println(stag);
//		}

		Scene scene = new Scene(new StackPane(listView), 640, 480);
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch();
	}

}