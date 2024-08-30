package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.FileExporter;
import isika.cda27.projet1.group4.annuaire.back.FileImporter;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends BorderPane {

	private Scene scene;
	private App app;
	private Stage stage;
	private TableViewStagiaires tableView;

	public HomePage(App app, Stage stage) {
		this.app = app;
		this.stage = stage;

		// création de la Hbox header
		Header header = new Header(app, stage, "Liste des Stagiaires");
		this.setTop(header);
		// Affichage du champ de recherche
		header.getSearchBox().setVisible(true);

		// marges
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		leftBox.setPrefWidth(150);
		rightBox.setPrefWidth(150);
		this.setLeft(leftBox);
		this.setRight(rightBox);

		// Ajouter le TableView au BorderPane
		this.tableView = new TableViewStagiaires(app.myObservableArrayList);
		this.setCenter(tableView);
		this.setAlignment(tableView, Pos.CENTER);
		tableView.setVisible(false);

		if (app.firstConnexion == true) {
			tableView.setVisible(true);
		} else {
			VBox welcomeMessage = new VBox();
			welcomeMessage.setAlignment(Pos.CENTER);

			Label welcomeLbl = new Label("Bienvenue sur l'annuaire d'Isika");
			Label importerLbl = new Label(
					"Il n'y a pas d'annuaire disponible actuellement. \nPour commencer veuillez en importer un.");
			welcomeLbl.getStyleClass().add("title");
			importerLbl.getStyleClass().add("sub-title");

			welcomeMessage.getChildren().addAll(welcomeLbl, importerLbl);
			this.setCenter(welcomeMessage);
		}

		
		// création de la Hbox Bottom
		HBox hboxBottom = new HBox();
		this.setBottom(hboxBottom);
		
		

		 // >>>>> Ajouter le bouton de gestion des utilisateurs ici <<<<<
        Button manageUsersButton = new Button("Gérer les utilisateurs");
        manageUsersButton.setOnAction(e -> openManageUsersScene());
        leftBox.getChildren().add(manageUsersButton);
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		


		Footer footer = new Footer(app, stage, app.myObservableArrayList, this);
		this.setBottom(footer);

		// création de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

	}

	// Méthode pour obtenir TableViewStagiaires
	public TableViewStagiaires getTableView() {
		return tableView;
	}
	
	// >>>>> Méthode pour ouvrir la scène ManageUsers <<<<<
    private void openManageUsersScene() {
        ManageUsers manageUsers = new ManageUsers(app, stage);
        stage.setScene(manageUsers.getScene());
    }
  
}
