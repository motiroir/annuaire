package isika.cda27.projet1.group4.annuaire;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.lang.NumberFormatException;

/**
 * JavaFX App
 */
public class AppBis extends Application {

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

//		 on créé un nouvel arbre
//		 BinarySearchTree searchTree = new BinarySearchTree();

//		for (int i = 0; i < myDAO.getStagiaires().size(); i++) {
//			System.out.println(myDAO.getStagiaires().get(i));
//		}

		// création du borderPane
		BorderPane borderPane = new BorderPane();

		// création de la Hbox header
		HBox hboxHeader = new HBox();
		borderPane.setTop(hboxHeader);

		// création du bouton connexion
		Button buttonConnexion = new Button("  Connexion  ");
		hboxHeader.getChildren().add(buttonConnexion);
		hboxHeader.setMargin(buttonConnexion, new Insets(10, 10, 20, 540));// Marges haut, droite, bas, gauche

		// Créer un TableView de stagiaires
		TableView<Stagiaire> tableView = new TableView<Stagiaire>(myObservableArrayList);// mettre la liste en argument

		// Créer les colonnes
		TableColumn<Stagiaire, String> nameColumn = new TableColumn<Stagiaire, String>(" Nom");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("name"));
		nameColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> firstNameColumn = new TableColumn<Stagiaire, String>(" Prénom");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));
		firstNameColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> postalCodeColumn = new TableColumn<Stagiaire, String>(" Département");
		postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("postalCode"));
		postalCodeColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> promoColumn = new TableColumn<Stagiaire, String>(" Promotion");
		promoColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));
		promoColumn.setPrefWidth(128);

		TableColumn<Stagiaire, String> yearColumn = new TableColumn<Stagiaire, String>(" Année");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));
		yearColumn.setPrefWidth(128);

		// Ajouter les colonnes au TableView
		tableView.getColumns().addAll(nameColumn, firstNameColumn, postalCodeColumn, promoColumn, yearColumn);

		// Ajouter les stagiaires au TableView
		// tableView.getItems().addAll();

		// Ajouter le TableView au BorderPane
		borderPane.setCenter(tableView);

		// création de la Hbox Bottom
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

				BorderPane borderPane = new BorderPane();

				// création de la Hbox header
				HBox hboxHeader = new HBox();
				borderPane.setTop(hboxHeader);

				// création du bouton connexion
				Button buttonConnexion = new Button("  Connexion  ");
				hboxHeader.getChildren().add(buttonConnexion);
				hboxHeader.setMargin(buttonConnexion, new Insets(10, 10, 20, 540));// Marges haut, droite, bas, gauche

				// Créer un TableView de stagiaires
				TableView<Stagiaire> tableView = new TableView<Stagiaire>(myObservableArrayList);// mettre la liste en
																									// argument

				// Créer les colonnes
				TableColumn<Stagiaire, String> nameColumn = new TableColumn<Stagiaire, String>(" Nom");
				nameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("name"));
				nameColumn.setPrefWidth(128);

				TableColumn<Stagiaire, String> firstNameColumn = new TableColumn<Stagiaire, String>(" Prénom");
				firstNameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));
				firstNameColumn.setPrefWidth(128);

				TableColumn<Stagiaire, String> postalCodeColumn = new TableColumn<Stagiaire, String>(" Département");
				postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("postalCode"));
				postalCodeColumn.setPrefWidth(128);

				TableColumn<Stagiaire, String> promoColumn = new TableColumn<Stagiaire, String>(" Promotion");
				promoColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));
				promoColumn.setPrefWidth(128);

				TableColumn<Stagiaire, String> yearColumn = new TableColumn<Stagiaire, String>(" Année");
				yearColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));
				yearColumn.setPrefWidth(128);

				// Ajouter les colonnes au TableView
				tableView.getColumns().addAll(nameColumn, firstNameColumn, postalCodeColumn, promoColumn, yearColumn);

				// Ajouter les stagiaires au TableView
				// tableView.getItems().addAll();

				// Ajouter le TableView au BorderPane
				borderPane.setCenter(tableView);

				// création de la Hbox Bottom
				HBox hboxBottom = new HBox();
				borderPane.setBottom(hboxBottom);

				Button buttonUpdate = new Button(" Modifier ");
				hboxBottom.getChildren().add(buttonUpdate);
				hboxBottom.setMargin(buttonUpdate, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

				Button buttonDelete = new Button(" Supprimer ");
				hboxBottom.getChildren().add(buttonDelete);
				hboxBottom.setMargin(buttonDelete, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

				Button buttonAdd = new Button("  Ajouter ");
				hboxBottom.getChildren().add(buttonAdd);
				hboxBottom.setMargin(buttonAdd, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

				// création du bouton connexion
				Button buttonImpression = new Button("Impression (pdf)");
				hboxBottom.getChildren().add(buttonImpression);
				hboxBottom.setMargin(buttonImpression, new Insets(20, 0, 20, 70));// Marges haut, droite, bas, gauche

				Scene secondeScene = new Scene(borderPane, 640, 480);
				stage.setScene(secondeScene);
				stage.show();

				buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						// création de la BorderPane pour 3 ème scène
						BorderPane addBorderPane = new BorderPane();

						// création de la Hbox header
						HBox hboxHeader = new HBox();
						addBorderPane.setTop(hboxHeader);

						// création du bouton connexion
						Button buttonConnexion = new Button("  Connexion  ");
						hboxHeader.getChildren().add(buttonConnexion);
						hboxHeader.setMargin(buttonConnexion, new Insets(10, 10, 20, 540));

						// création de la GridePane
						GridPane gridpane = new GridPane();
						addBorderPane.setCenter(gridpane);

						// organisation :
						gridpane.setVgap(20); // Espace vertical entre les lignes
						gridpane.setHgap(0); // Espace horizontal entre les colonnes

						// ajouter une marge intérieure sur tous les côtés du GridPane
						gridpane.setPadding(new Insets(80));

						// remplir la GridPane avec les labels et les textfields

						Label nameLabel = new Label(" Nom ");
						TextField nameTextfield = new TextField();
						gridpane.add(nameLabel, 0, 0); // (colonne/ligne)
						gridpane.add(nameTextfield, 1, 0);

						Label firstnameLabel = new Label(" Prénom ");
						TextField firstnameTextfield = new TextField();
						gridpane.add(firstnameLabel, 0, 1); // (colonne/ligne)
						gridpane.add(firstnameTextfield, 1, 1);

						Label postalCodeLabel = new Label(" Département ");
						TextField postalCodeTextfield = new TextField();
						gridpane.add(postalCodeLabel, 0, 2); // (colonne/ligne)
						gridpane.add(postalCodeTextfield, 1, 2);

						Label promoLabel = new Label(" Promotion ");
						TextField promoTextfield = new TextField();
						gridpane.add(promoLabel, 0, 3); // (colonne/ligne)
						gridpane.add(promoTextfield, 1, 3);

						Label yearLabel = new Label(" Année ");
						TextField yearTextfield = new TextField();
						gridpane.add(yearLabel, 0, 4); // (colonne/ligne)
						gridpane.add(yearTextfield, 1, 4);

						// création de la Hbox Bottom
						HBox hboxBottom = new HBox();
						addBorderPane.setBottom(hboxBottom);

						// création du bouton Annuler
						Button cancelButton = new Button(" Annuler ");
						hboxBottom.getChildren().add(cancelButton);
						hboxBottom.setMargin(cancelButton, new Insets(10, 0, 30, 10)); // Marges haut, droite, bas,
																						// gauche

						// création du bouton valider
						Button validateButton = new Button(" Valider ");
						hboxBottom.getChildren().add(validateButton);
						hboxBottom.setMargin(validateButton, new Insets(10, 0, 30, 490));

						Scene AddScene = new Scene(addBorderPane, 640, 480);
						stage.setScene(AddScene);
						stage.show();

						validateButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {

								// Récupérer les valeurs des champs
								String name = nameTextfield.getText();
								String firstName = firstnameTextfield.getText();
								String postalCode = postalCodeTextfield.getText();
								String promo = promoTextfield.getText();
								String sYear = yearTextfield.getText();
								int year = 0;

								try {
									year = Integer.parseInt(sYear);
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}

								// Créer un nouveau stagiaire
								Stagiaire stagiaire = new Stagiaire(name, firstName, postalCode, promo, year);

								// Ajouter le stagiaire via le DAO
								myDAO.addStagiaire(stagiaire);
								myObservableArrayList.setAll(myDAO.getStagiaires());

								// Revenir à la scène précédente
								stage.setScene(secondeScene);

							}

						});

						cancelButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// Revenir à la scène 02
								stage.setScene(secondeScene);

							}
						});

					}

				});

				buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// Récupérer le stagiaire sélectionné
						Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();

						if (selectedStagiaire != null) {
							// Supprimer le stagiaire
							myDAO.removeStagiaire(selectedStagiaire);
							System.out.println("stagiaire à supp " + selectedStagiaire);
							myObservableArrayList.setAll(myDAO.getStagiaires());
						}
					}
				});

				buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

/////           /////////////////////////////Récupérer le stagiaire sélectionné
						Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();
						// on vérifie que le stagiaire selectionné n'est pas null
						if (selectedStagiaire != null) {

							BorderPane UpdateBorderPane = new BorderPane();

							// création de la Hbox header
							HBox hboxHeader = new HBox();
							UpdateBorderPane.setTop(hboxHeader);

							// création du bouton connexion
							Button buttonConnexion = new Button("  Connexion  ");
							hboxHeader.getChildren().add(buttonConnexion);
							hboxHeader.setMargin(buttonConnexion, new Insets(10, 10, 20, 540));

							// création de la GridePane
							GridPane gridpane = new GridPane();
							UpdateBorderPane.setCenter(gridpane);

							// organisation :
							gridpane.setVgap(20); // Espace vertical entre les lignes
							gridpane.setHgap(0); // Espace horizontal entre les colonnes

							// ajouter une marge intérieure sur tous les côtés du GridPane
							gridpane.setPadding(new Insets(80));

							// remplir la GridPane avec les labels et les textfields

							Label nameLabel = new Label(" Nom ");
							TextField nameTextfield = new TextField(selectedStagiaire.getName());
							gridpane.add(nameLabel, 0, 0); // (colonne/ligne)
							gridpane.add(nameTextfield, 1, 0);

							Label firstnameLabel = new Label(" Prénom ");
							TextField firstnameTextfield = new TextField(selectedStagiaire.getFirstName());
							gridpane.add(firstnameLabel, 0, 1); // (colonne/ligne)
							gridpane.add(firstnameTextfield, 1, 1);

							Label postalCodeLabel = new Label(" Département ");
							TextField postalCodeTextfield = new TextField(selectedStagiaire.getPostalCode());
							gridpane.add(postalCodeLabel, 0, 2); // (colonne/ligne)
							gridpane.add(postalCodeTextfield, 1, 2);

							Label promoLabel = new Label(" Promotion ");
							TextField promoTextfield = new TextField(selectedStagiaire.getPromo());
							gridpane.add(promoLabel, 0, 3); // (colonne/ligne)
							gridpane.add(promoTextfield, 1, 3);

							Label yearLabel = new Label(" Année ");
							TextField yearTextfield = new TextField(String.valueOf(selectedStagiaire.getYear()));
							gridpane.add(yearLabel, 0, 4); // (colonne/ligne)
							gridpane.add(yearTextfield, 1, 4);

							// création de la Hbox Bottom
							HBox hboxBottom = new HBox();
							UpdateBorderPane.setBottom(hboxBottom);

							// création du bouton Annuler
							Button cancelButton = new Button(" Annuler ");
							hboxBottom.getChildren().add(cancelButton);
							hboxBottom.setMargin(cancelButton, new Insets(10, 0, 30, 10));

							// création du bouton valider
							Button validateButton = new Button(" Valider ");
							hboxBottom.getChildren().add(validateButton);
							hboxBottom.setMargin(validateButton, new Insets(10, 0, 30, 490));

							Scene UpdateScene = new Scene(UpdateBorderPane, 640, 480);
							stage.setScene(UpdateScene);
							stage.show();

							validateButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {

									// Récupérer les valeurs des champs
									String name = nameTextfield.getText();
									String firstName = firstnameTextfield.getText();
									String postalCode = postalCodeTextfield.getText();
									String promo = promoTextfield.getText();
									String sYear = yearTextfield.getText();
									int year = 0;

									try {
										year = Integer.parseInt(sYear);
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}

									// Créer un nouveau stagiaire
									Stagiaire newStagiaire = new Stagiaire(name, firstName, postalCode, promo, year);

									// Appeler la méthode update du DAO
									myDAO.updateStagiaire(selectedStagiaire, newStagiaire);
									myObservableArrayList.setAll(myDAO.getStagiaires());

									// Revenir à la scène précédente
									stage.setScene(secondeScene);

								}
							});

							cancelButton.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									// Revenir à la scène 02
									stage.setScene(secondeScene);

								}

							});
						}
					}
				});

			}
		});

	}

	public static void main(String[] args) {
		launch();
	}

}