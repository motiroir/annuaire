package isika.cda27.projet1.group4.annuaire.front;

import java.io.File;
import java.io.FileNotFoundException;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.FileExporter;
import isika.cda27.projet1.group4.annuaire.back.FileImporter;
import isika.cda27.projet1.group4.annuaire.back.PdfPrint;
import isika.cda27.projet1.group4.annuaire.back.Role;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Footer extends StackPane {

	private HomePage homePage;
	private App app;
	private Stage stage;
	
	 /**
     * Constructeur de la classe Footer.
     * Initialise les boutons du pied de page selon le rôle de l'utilisateur actuel.
     * 
     * @param app                 L'application principale
     * @param stage               La fenêtre principale
     * @param myObservableArrayList La liste observable des stagiaires
     * @param homePage            La page d'accueil associée
     */

	public Footer(App app, Stage stage, ObservableList<Stagiaire> myObservableArrayList, HomePage homePage) {
		
		// Initialisation des composants graphiques du pied de page
		
		this.app=app;
		this.stage=stage;

		HBox buttonsBox = new HBox();
		buttonsBox.setAlignment(Pos.CENTER_LEFT);
		buttonsBox.setMaxSize(1000, 60);
		
		// Bouton de modification
		Button buttonUpdate = new Button("Modifier ");
		buttonUpdate.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER
				|| app.currentUser.getRole() == Role.STUDENT) {
			buttonUpdate.setVisible(true);
			buttonsBox.setMargin(buttonUpdate, new Insets(20, 0, 20, 40));
			buttonUpdate.setDisable(true);
		}

		// Bouton de suppression
		Button buttonDelete = new Button("Supprimer");
		buttonDelete.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonDelete.setVisible(true);
			buttonsBox.setMargin(buttonDelete, new Insets(20, 0, 20, 40));
			buttonDelete.setDisable(true);
		}

		// Bouton d'ajout
		Button buttonAdd = new Button(" Ajouter ");
		buttonAdd.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonAdd.setVisible(true);
			buttonsBox.setMargin(buttonAdd, new Insets(20, 0, 20, 40));
		}
		
		// Bouton d'importation
		Button importButton = new Button("Remplacer l'annuaire");
		importButton.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN) {
			importButton.setVisible(true);
			buttonsBox.setMargin(importButton, new Insets(20, 0, 20, 40));
		}
		
		// Bouton d'exportation
		Button exportButton = new Button(" Exporter ");
		exportButton.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN) {
			exportButton.setVisible(true);
			buttonsBox.setMargin(exportButton, new Insets(20, 0, 20, 40));
		}
		
		// Bouton de gestion des utilisateurs
		Button manageUsersButton = new Button("Gérer les utilisateurs");
		manageUsersButton.setVisible(false);
		// Configuration des boutons selon le rôle de l'utilisateur
		if (app.currentUser.getRole() == Role.ADMIN) {
			manageUsersButton.setVisible(true);
			buttonsBox.setMargin(manageUsersButton, new Insets(20, 0, 20, 40));
		}

		buttonsBox.getChildren().addAll(buttonUpdate, buttonAdd, buttonDelete, importButton, exportButton, manageUsersButton);
		
		// Bouton d'impression
		HBox impressionBox = new HBox();
		impressionBox.setMaxSize(280, 100);
		impressionBox.setAlignment(Pos.CENTER_RIGHT);
		Button buttonImpression = new Button(" Imprimer ");
		impressionBox.getChildren().add(buttonImpression);
		impressionBox.setMargin(buttonImpression, new Insets(20, 40, 20, 00));
		
		this.getChildren().addAll(buttonsBox, impressionBox);
		StackPane.setAlignment(impressionBox, Pos.CENTER_RIGHT);
		StackPane.setAlignment(buttonsBox, Pos.CENTER_LEFT);

		// Utiliser la méthode pour obtenir TableViewStagiaires
		TableViewStagiaires tableView = homePage.getTableView();

		// Écouter les changements de sélection dans le TableView
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Activer les boutons si un élément est sélectionné
				buttonDelete.setDisable(false);
				buttonUpdate.setDisable(false);
			} else {
				// Désactiver les boutons si aucune sélection
				buttonDelete.setDisable(true);
				buttonUpdate.setDisable(true);
			}
		});

		// Méthode associée à l'ajout d'un stagiaire
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddForm addForm = new AddForm(app, stage);
				stage.setScene(addForm.getScene());
			}
		});

		// Méthode associée à la suppression d'un stagiaire
		buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Utiliser la méthode pour obtenir TableViewStagiaires
		        TableViewStagiaires tableView = homePage.getTableView();
		        // Récupérer le stagiaire sélectionné
		        Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();
		        if (selectedStagiaire != null) {
		            
		            // Créer un dialogue
		            Dialog<String> dialog = new Dialog<>();
		            dialog.setTitle("");
		            dialog.setHeaderText(null);

		            VBox vbox = new VBox(10);
		            vbox.setAlignment(Pos.CENTER);

		            Label messageLabel = new Label("Êtes-vous sûr de vouloir supprimer ce stagiaire ?");
		            messageLabel.getStyleClass().add("alert");
		            VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15)); // Marges haut, droite, bas, gauche
		            
		            Button okButton = new Button("   OK    ");
		            okButton.setOnAction(e -> { app.myDAO.removeStagiaire(selectedStagiaire);
	                myObservableArrayList.setAll(app.myDAO.getStagiaires());
	                dialog.setResult("ok");}
		            ); // Ferme le dialogue en définissant un résultat
		           
		            
		            Button cancelButton = new Button("Annuler");
		            cancelButton.setOnAction(e -> dialog.setResult("annuler")); // Fermer le dialogue en définissant un résultat Cancel
		            
		            
		            HBox buttonHbox = new HBox ();
		            buttonHbox.getChildren().addAll( cancelButton,okButton);
		            HBox.setMargin(okButton, new Insets(0, 18, 10, 100)); // Marges haut, droite, bas, gauche
		            HBox.setMargin(cancelButton, new Insets(0, 40, 10, 18)); // Marges haut, droite, bas, gauche
		            vbox.getChildren().addAll(messageLabel, buttonHbox );

		            DialogPane dialogPane = dialog.getDialogPane();
		            dialogPane.setContent(vbox);
		            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		            dialogPane.getStyleClass().add("alert");

		            dialog.showAndWait(); // Attendre la réponse de l'utilisateur

		        }
		    }
		});

		// Méthode associée à la modification d'un stagiaire
		buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();
				UpdateForm updateForm = new UpdateForm(app, stage, selectedStagiaire);
				stage.setScene(updateForm.getScene());
			}
		});
		
		// Méthode associée à l'importation de l'annuaire
		importButton.setOnAction(e -> {
			FileImporter importer = new FileImporter();
			String fileContent = importer.importer(stage, app);

			
			Dialog<String> dialog = new Dialog<>();
			dialog.setTitle("Importation d'un nouvel annuaire");
			dialog.setHeaderText(null);

			VBox vbox = new VBox(10);
			vbox.setAlignment(Pos.CENTER);
			vbox.setMinSize(350,150);

			Label messageLabel = new Label("   "+fileContent+"   ");
			messageLabel.getStyleClass().add("alert");
			VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15));// Marges haut, droite, bas, gauche

			Button okButton = new Button("OK ");
			okButton.setOnAction(event -> dialog.setResult("OK"));
			VBox.setMargin(okButton, new Insets(5, 10, 20, 10));// Marges haut, droite, bas, gauche

			vbox.getChildren().addAll(messageLabel, okButton);

			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.setContent(vbox);
			dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
			dialogPane.getStyleClass().add("alert");

			dialog.showAndWait(); // Cela attend jusqu'à ce que l'utilisateur clique sur OK
		
		});

		// Méthode associée à l'exportation de l'annuaire
		exportButton.setOnAction(e -> {
			FileExporter exporter = new FileExporter();
			String exportResult = exporter.exporterAnnuaire(stage, app.myObservableArrayList);


		
			Dialog<String> dialog = new Dialog<>();
			dialog.setTitle("Exportation de l'annuaire");
			dialog.setHeaderText(null);

			VBox vbox = new VBox(10);
			vbox.setAlignment(Pos.CENTER);
			vbox.setMinSize(350,150);


			Label messageLabel = new Label("   "+exportResult+"   ");
			messageLabel.getStyleClass().add("alert");
			VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15));// Marges haut, droite, bas, gauche

			Button okButton = new Button("OK ");
			okButton.setOnAction(event -> dialog.setResult("OK"));
			VBox.setMargin(okButton, new Insets(5, 10, 20, 10));// Marges haut, droite, bas, gauche

			vbox.getChildren().addAll(messageLabel, okButton);

			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.setContent(vbox);
			dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
			dialogPane.getStyleClass().add("alert");

			dialog.showAndWait(); // Cela attend jusqu'à ce que l'utilisateur clique sur OK
			
		
		});
		
		// Méthode associée à l'impression du tableau
		buttonImpression.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Créer un FileChooser pour que l'utilisateur puisse choisir l'emplacement où
				// enregistrer le fichier PDF
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Enregistrer le fichier PDF");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));

				// Afficher la boîte de dialogue de sauvegarde
				File file = fileChooser.showSaveDialog(stage);

				// Si l'utilisateur a choisi un fichier
				if (file != null) {
					try {
						// Appeler la méthode pour imprimer le tableau en PDF
						PdfPrint.printTableToPdf(tableView, file.getAbsolutePath());
						showConfirmationDialog(
								"Le fichier PDF a été créé avec succès à l'emplacement :\n" + file.getAbsolutePath());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

			private void showConfirmationDialog(String message) {

				Dialog<String> dialog = new Dialog<>();
				dialog.setTitle("Confirmation");
				dialog.setHeaderText(null);

				VBox vbox = new VBox(10);
				vbox.setAlignment(Pos.CENTER);
				vbox.setMinSize(350, 150);

				Label messageLabel = new Label( message );
				messageLabel.getStyleClass().add("alert");
				VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15));

				Button okButton = new Button("OK ");
				okButton.setOnAction(event -> dialog.setResult("OK"));
				VBox.setMargin(okButton, new Insets(5, 10, 20, 10));

				vbox.getChildren().addAll(messageLabel, okButton);

				DialogPane dialogPane = dialog.getDialogPane();
				dialogPane.setContent(vbox);
				dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
				dialogPane.getStyleClass().add("alert");

				dialog.showAndWait(); // Cela attend jusqu'à ce que l'utilisateur clique sur OK

			}
		});
		
		// Méthode associée à la gestion des utilisateurs
		manageUsersButton.setOnAction(e -> openManageUsersScene());

		

	}
	// Méthode pour ouvrir la scène ManageUsers 
	private void openManageUsersScene() {
		ManageUsers manageUsers = new ManageUsers(app, stage);
		stage.setScene(manageUsers.getScene());
	}

}

