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

		Button buttonUpdate = new Button(" Modifier ");
		this.getChildren().add(buttonUpdate);
		this.setMargin(buttonUpdate, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

		Button buttonDelete = new Button(" Supprimer ");
		this.getChildren().add(buttonDelete);
		this.setMargin(buttonDelete, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

		Button buttonAdd = new Button("  Ajouter ");
		this.getChildren().add(buttonAdd);
		this.setMargin(buttonAdd, new Insets(20, 0, 20, 80));// Marges haut, droite, bas, gauche

		Button buttonImpression = new Button("Impression (pdf)");
		this.getChildren().add(buttonImpression);
		this.setMargin(buttonImpression, new Insets(20, 0, 20, 530));// Marges haut, droite, bas, gauche

		// méthode du bouton add
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddForm addForm = new AddForm(app, stage);
				stage.setScene(addForm.getScene());
				stage.setTitle("Page de connexion");
			}
		});

		// méthode du bouton delete
		buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	        public void handle(ActionEvent event) {
				// Utiliser la méthode pour obtenir TableViewStagiaires
                TableViewStagiaires tableView = homePage.getTableView();
	            // Récupérer le stagiaire sélectionné
	            Stagiaire selectedStagiaire = tableView.getSelectionModel().getSelectedItem();
	            if (selectedStagiaire != null) {
	                // Supprimer le stagiaire
	                //app.myDAO.removeStagiaire(selectedStagiaire);
	                myObservableArrayList.setAll(app.myDAO.getStagiaires());
	            }
	        }
			
		});

	}

}
