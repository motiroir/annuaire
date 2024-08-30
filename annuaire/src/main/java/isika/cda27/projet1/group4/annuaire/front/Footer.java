package isika.cda27.projet1.group4.annuaire.front;

import java.io.File;
import java.io.FileNotFoundException;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.FileExporter;
import isika.cda27.projet1.group4.annuaire.back.FileImporter;
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

	public Footer(App app, Stage stage, ObservableList<Stagiaire> myObservableArrayList, HomePage homePage) {

		HBox buttonsBox = new HBox();
		buttonsBox.setAlignment(Pos.CENTER_LEFT);
		buttonsBox.setMaxSize(1000, 60);
		
		Button buttonUpdate = new Button("Modifier");
		buttonUpdate.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER
				|| app.currentUser.getRole() == Role.STUDENT) {
			buttonUpdate.setVisible(true);
			buttonsBox.setMargin(buttonUpdate, new Insets(20, 0, 20, 40));
			buttonUpdate.setDisable(true);
		}

		Button buttonDelete = new Button("Supprimer");
		buttonDelete.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonDelete.setVisible(true);
			buttonsBox.setMargin(buttonDelete, new Insets(20, 0, 20, 40));
			buttonDelete.setDisable(true);
		}

		Button buttonAdd = new Button(" Ajouter");
		buttonAdd.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonAdd.setVisible(true);
			buttonsBox.setMargin(buttonAdd, new Insets(20, 0, 20, 40));
		}

		Button importButton = new Button("Remplacer l'annuaire");
		importButton.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN) {
			importButton.setVisible(true);
			buttonsBox.setMargin(importButton, new Insets(20, 0, 20, 40));
		}
		
		Button exportButton = new Button("Exporter");
		exportButton.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN) {
			exportButton.setVisible(true);
			buttonsBox.setMargin(exportButton, new Insets(20, 0, 20, 40));
		}
		


		buttonsBox.getChildren().addAll(buttonUpdate, buttonAdd, buttonDelete, importButton, exportButton);
		
		HBox impressionBox = new HBox();
		impressionBox.setMaxSize(280, 100);
		impressionBox.setAlignment(Pos.CENTER_RIGHT);
		Button buttonImpression = new Button("Imprimer");
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

		// Méthode du bouton add
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddForm addForm = new AddForm(app, stage);
				stage.setScene(addForm.getScene());
			}
		});

		// Méthode du bouton delete
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
		            
		            Button okButton = new Button("OK");
		            okButton.setOnAction(e -> { app.myDAO.removeStagiaire(selectedStagiaire);
	                myObservableArrayList.setAll(app.myDAO.getStagiaires());
	                dialog.setResult("ok");}
		            ); // Ferme le dialogue en définissant un résultat
		            VBox.setMargin(okButton, new Insets(5, 10, 20, 10)); // Marges haut, droite, bas, gauche
		            
		            Button cancelButton = new Button("Annuler");
		            cancelButton.setOnAction(e -> dialog.setResult("annuler")); // Fermer le dialogue en définissant un résultat Cancel
		            VBox.setMargin(cancelButton, new Insets(5, 10, 20, 10)); // Marges haut, droite, bas, gauche
		            
		            HBox buttonHbox = new HBox ();
		            buttonHbox.getChildren().addAll( cancelButton,okButton);
		            HBox.setMargin(okButton, new Insets(0, 10, 0, 0)); // Marges haut, droite, bas, gauche
		            HBox.setMargin(cancelButton, new Insets(0, 0, 0, 10)); // Marges haut, droite, bas, gauche
		            vbox.getChildren().addAll(messageLabel, buttonHbox );

		            DialogPane dialogPane = dialog.getDialogPane();
		            dialogPane.setContent(vbox);
		            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		            dialogPane.getStyleClass().add("alert");

		            dialog.showAndWait(); // Attendre la réponse de l'utilisateur

//		            // Vérifier le résultat du dialogue
//		            if ("OK".equals(dialog.getResult())) { 
//		                // Supprimer le stagiaire si l'utilisateur a confirmé
//		                app.myDAO.removeStagiaire(selectedStagiaire);
//		                myObservableArrayList.setAll(app.myDAO.getStagiaires());
//		            }
		        }
		    }
		});

		buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();
				UpdateForm updateForm = new UpdateForm(app, stage, selectedStagiaire);
				stage.setScene(updateForm.getScene());

			}
		});
		
		importButton.setOnAction(e -> {
			FileImporter importer = new FileImporter();
			String fileContent = importer.importer(stage, app);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Importation d'un nouvel annuaire");
			alert.setHeaderText(null);
			alert.setContentText(fileContent);
			alert.showAndWait();
		});

		exportButton.setOnAction(e -> {
			FileExporter exporter = new FileExporter();
			String exportResult = exporter.exporterAnnuaire(stage, app.myObservableArrayList);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Exportation de l'annuaire");
			alert.setHeaderText(null);
			alert.setContentText(exportResult);
			alert.showAndWait();
		});
		
		// Gestion de l'impression en PDF
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
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.showAndWait();

			}
		});


	}

}
