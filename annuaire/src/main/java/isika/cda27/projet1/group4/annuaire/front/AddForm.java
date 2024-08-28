package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		
		//marges sur les côtés
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		leftBox.setPrefWidth(150);
		rightBox.setPrefWidth(150);
		this.setLeft(leftBox);
		this.setRight(rightBox);
		
		// gridpane au centre non rempli
		GridPane gridpane = new GridPane();
		this.setCenter(gridpane);
		
		// organisation :
		gridpane.setVgap(30); // Espace vertical entre les lignes
		gridpane.setHgap(60); // Espace horizontal entre les colonnes

		// ajouter une marge intérieure sur tous les côtés du GridPane
		gridpane.setPadding(new Insets(80));
		gridpane.setAlignment(Pos.CENTER);
		//gridpane.setBorder(1);

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

		// création du footer spécial
		HBox hboxBottom = new HBox();
		this.setBottom(hboxBottom);
		
		// création du bouton Annuler
		Button cancelButton = new Button("Annuler");
		hboxBottom.getChildren().add(cancelButton);
		hboxBottom.setMargin(cancelButton, new Insets(10, 0, 30, 10));

		// création du bouton valider
		Button validateButton = new Button("Valider");
		hboxBottom.getChildren().add(validateButton);
		hboxBottom.setMargin(validateButton, new Insets(10, 0, 30, 490));

		// creation de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		
		
		validateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Récupérer les valeurs des champs
				String name = nameTextfield.getText();
				String firstName = firstnameTextfield.getText();
				String postalCode = postalCodeTextfield.getText();
				String promo = promoTextfield.getText();
				String sYear = yearTextfield.getText();
				int year =0;
				
					try {
						year = Integer.parseInt(sYear);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
			
				// Créer un nouveau stagiaire
				Stagiaire stagiaire = new Stagiaire(name, firstName, postalCode, promo, year);
				// Ajouter le stagiaire via le DAO
				app.myDAO.addStagiaire(stagiaire);
				app.myObservableArrayList.setAll(app.myDAO.getStagiaires());
				// Revenir à la scène précédente (accueil)
				HomePage homepage = new HomePage(app, stage);
				stage.setScene(homepage.getScene());
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Revenir à la scène précédente (accueil)
				HomePage homepage = new HomePage(app, stage);
				stage.setScene(homepage.getScene());

			}
		});

	}

}
