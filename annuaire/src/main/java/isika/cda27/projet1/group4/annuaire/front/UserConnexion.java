package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserConnexion extends BorderPane {
	private Scene scene;
	private App app;
	private Stage stage;

	public UserConnexion(App app, Stage stage) {
		super();
		this.app = app;
		this.stage = stage;

		// ajout du header
		this.setTop(new Header(app, stage, "Connexion"));

		// gridpane au centre non rempli
		GridPane gridpane = new GridPane();
		this.setCenter(gridpane);
		
		
		
		
		
		

		// creation de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

	}

}
