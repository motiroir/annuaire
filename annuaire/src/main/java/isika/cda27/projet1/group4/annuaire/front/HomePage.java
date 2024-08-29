package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
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
		// Afficher le champ de recherche
	    this.setSearchVisible(true); 
	    

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

		// création de la Hbox Bottom
		HBox hboxBottom = new HBox();
		this.setBottom(hboxBottom);
		
		 Button importButton = new Button("Importer un fichier texte");
	        
	        importButton.setOnAction(e -> {
	            FileImporter importer = new FileImporter();
	            String fileContent = importer.importer(stage, app);
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Importation d'un nouvel annuaire");
	            alert.setHeaderText(null);
	            alert.setContentText(fileContent);
	            alert.showAndWait();
	        });
	    leftBox.getChildren().add(importButton);
		
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
	
	public void setSearchVisible(boolean visible) {
	    // Trouver le Header et modifier la visibilité du champ de recherche
	    Header header = (Header) this.getTop();
	    header.getSearchBox().setVisible(visible);
	}

}
