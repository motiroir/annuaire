package isika.cda27.projet1.group4.annuaire.front;

import java.io.File;
import java.io.FileNotFoundException;

import isika.cda27.projet1.group4.annuaire.App;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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

		buttonsBox.getChildren().addAll(buttonUpdate, buttonAdd, buttonDelete, importButton);
		
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
					// Supprimer le stagiaire
					app.myDAO.removeStagiaire(selectedStagiaire);
					myObservableArrayList.setAll(app.myDAO.getStagiaires());
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
