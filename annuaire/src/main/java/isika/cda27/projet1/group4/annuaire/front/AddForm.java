package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddForm extends BorderPane {

	private Scene scene;
	private App app;
	private Stage stage;

	public AddForm(App app, Stage stage) {
		super();
		this.app = app;
		this.stage = stage;

		// ajout du header
		this.setTop(new Header(app, stage, "Ajouter un stagiaire"));

		// gridpane au centre non rempli
		GridPane gridpane = new GridPane();
		this.setCenter(gridpane);

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
		
		// création du bouton Annuler
		Button cancelButton = new Button("Annuler");
		this.getChildren().add(cancelButton);
		this.setMargin(cancelButton, new Insets(10, 0, 30, 10));

		// création du bouton valider
		Button validateButton = new Button("Valider");
		this.getChildren().add(validateButton);
		this.setMargin(validateButton, new Insets(10, 0, 30, 490));

		// creation de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		
		

	}

}
