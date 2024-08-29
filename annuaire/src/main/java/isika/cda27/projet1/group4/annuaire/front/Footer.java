package isika.cda27.projet1.group4.annuaire.front;

import java.io.File;
import java.io.FileNotFoundException;

import isika.cda27.projet1.group4.annuaire.App;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Footer extends HBox {

	private HomePage homePage;

	public Footer(App app, Stage stage, ObservableList<Stagiaire> myObservableArrayList, HomePage homePage) {

		Button buttonUpdate = new Button("Modifier");
		this.getChildren().add(buttonUpdate);
		buttonUpdate.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER || app.currentUser.getRole() == Role.STUDENT) {
			buttonUpdate.setVisible(true);
			this.setMargin(buttonUpdate, new Insets(20, 0, 20, 80));
			buttonUpdate.setDisable(true);
		}

		Button buttonDelete = new Button("Supprimer");
		this.getChildren().add(buttonDelete);
		buttonDelete.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonDelete.setVisible(true);
			this.setMargin(buttonDelete, new Insets(20, 0, 20, 80));
			buttonDelete.setDisable(true);
		}

		Button buttonAdd = new Button(" Ajouter");
		this.getChildren().add(buttonAdd);
		buttonAdd.setVisible(false);
		if (app.currentUser.getRole() == Role.ADMIN || app.currentUser.getRole() == Role.TEACHER) {
			buttonAdd.setVisible(true);
			this.setMargin(buttonAdd, new Insets(20, 0, 20, 80));
		}

		Button buttonImpression = new Button("Imprimer");
		this.getChildren().add(buttonImpression);
		this.setMargin(buttonImpression, new Insets(20, 60, 20, 530));
		this.setAlignment(Pos.CENTER_RIGHT);

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
