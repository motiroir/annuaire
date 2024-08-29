package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Footer extends HBox {

	private HomePage homePage;

	public Footer(App app, Stage stage, ObservableList<Stagiaire> myObservableArrayList, HomePage homePage) {

		Button buttonUpdate = new Button("Modifier");
		this.getChildren().add(buttonUpdate);
		this.setMargin(buttonUpdate, new Insets(20, 0, 20, 80));

		Button buttonDelete = new Button("Supprimer");
		this.getChildren().add(buttonDelete);
		this.setMargin(buttonDelete, new Insets(20, 0, 20, 80));

		Button buttonAdd = new Button(" Ajouter");
		this.getChildren().add(buttonAdd);
		this.setMargin(buttonAdd, new Insets(20, 0, 20, 80));

		Button buttonImpression = new Button("Imprimer");
		this.getChildren().add(buttonImpression);
		this.setMargin(buttonImpression, new Insets(20, 0, 20, 530));

		// Desactivation des boutons initial
		buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);

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
				stage.setTitle("Page de connexion");
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
				stage.setTitle("Page de modification");

			}
		});

	}

}