package isika.cda27.projet1.group4.annuaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.Annuaire;
import isika.cda27.projet1.group4.annuaire.back.BinaryFileManager;
import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Node;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	public AnnuaireDAO myDAO;
	public ObservableList<Stagiaire> myObservableArrayList;

	// methode dediée a l'initialisation
	@Override
	public void init() {
		myDAO = new AnnuaireDAO();
	}

	@Override
	public void start(Stage stage) {

		// Annuaire annuaire = new Annuaire();
		// annuaire.lireFichier("src/main/resources/test.txt");

		myObservableArrayList = FXCollections.observableArrayList(this.myDAO.getStagiaires());
//		System.out.println(myDAO);

		// on créé un nouvel arbre
		// BinarySearchTree searchTree = new BinarySearchTree();

		for (int i = 0; i < myDAO.getStagiaires().size(); i++) {
			System.out.println(myDAO.getStagiaires().get(i));
		}

//		ListView<Stagiaire> listView = new ListView<Stagiaire>();
//		List<Stagiaire> stagiaires = searchTree.affichage();
//
//		searchTree.deleteInTree(stagiaires.get(2));
//
//		searchTree.affichage();
//		
//		for (Stagiaire stag : stagiaires) {
//			listView.getItems().add(stag);
//		}
//
//		// test recherche d'un stagiaire par nom
//		Stagiaire test = new Stagiaire("ROIGNANT", "", "", "", 0);
//		System.out.println("\nTest du stagiaire à trouver sur " + test.getName());
//		List<Stagiaire> stagiairesSearched = searchTree.searchStagiaireInTree(test);
//		for (Stagiaire stag : stagiairesSearched) {
//			System.out.println(stag);
//		}

		// création du borderPane
		BorderPane borderPane = new BorderPane();

		// création de la Hbox header
		HBox hboxHeader = new HBox();
		borderPane.setTop(hboxHeader);

		// création du bouton connexion
		Button buttonConnexion = new Button("Connexion utilisateur");
		hboxHeader.getChildren().add(buttonConnexion);
		hboxHeader.setMargin(buttonConnexion, new Insets(10, 10, 20, 500));// Marges haut, droite, bas, gauche

		// Créer un TableView de stagiaires
		TableView<Stagiaire> tableView = new TableView<Stagiaire>(myObservableArrayList);// mettre la liste en argument

		// Créer les colonnes
		TableColumn<Stagiaire, String> nameColumn = new TableColumn<Stagiaire, String>("Nom");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("name"));
		nameColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> firstNameColumn = new TableColumn<Stagiaire, String>("Prénom");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));
		firstNameColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> postalCodeColumn = new TableColumn<Stagiaire, String>("Département");
		postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("postalCode"));
		postalCodeColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> promoColumn = new TableColumn<Stagiaire, String>("Promotion");
		promoColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));
		promoColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> yearColumn = new TableColumn<Stagiaire, String>("Année");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));
		yearColumn.setPrefWidth(128);

		// Ajouter les colonnes au TableView
		tableView.getColumns().addAll(nameColumn, firstNameColumn, postalCodeColumn, promoColumn, yearColumn);

		// Ajouter les stagiaires au TableView
		// tableView.getItems().addAll();

		// Ajouter le TableView au BorderPane
		borderPane.setCenter(tableView);

		// création de la Hbox header
		HBox hboxBottom = new HBox();
		borderPane.setBottom(hboxBottom);

		// création du bouton connexion
		Button buttonImpression = new Button("Impression (pdf)");
		hboxBottom.getChildren().add(buttonImpression);
		hboxBottom.setMargin(buttonImpression, new Insets(20, 0, 20, 530));// Marges haut, droite, bas, gauche

		Scene scene = new Scene(borderPane, 640, 480);
		stage.setScene(scene);
		stage.show();

		// le bouton connexion permet de basculer vers une nouvelle scène
		buttonConnexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}

		});

	}

	public static void main(String[] args) {
		launch();
	}

}