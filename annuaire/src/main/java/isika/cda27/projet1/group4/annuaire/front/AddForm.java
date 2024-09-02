package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
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
		Header header = new Header(app, stage, "Ajouter un stagiaire");
		this.setTop(header);
		// Masquer le champ de recherche
		header.getSearchBox().setVisible(false);

		// marges sur les côtés
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		leftBox.setPrefWidth(150);
		rightBox.setPrefWidth(150);
		this.setLeft(leftBox);
		this.setRight(rightBox);

		// boite à formulaire
		VBox formBox = new VBox();
		formBox.setMaxSize(600, 400);
		formBox.setStyle("-fx-border-color: #d3d3d3; " + // Couleur de la bordure
				"-fx-border-width: 2px; " + // Épaisseur de la bordure
				"-fx-border-radius: 5px;"); // Rayon pour les coins arrondis
		this.setCenter(formBox);

		// Instruction
		HBox consigne = new HBox();
		formBox.getChildren().add(consigne);

		Label lblConsigne = new Label("Veuillez remplir les champs suivants pour ajouter un nouveau stagiaire");
		consigne.getChildren().add(lblConsigne);
		consigne.setPadding(new Insets(40, 0, 0, 0));
		consigne.setAlignment(Pos.CENTER);

		// gridpane du formulaire sans boutons
		GridPane gridpane = new GridPane();
		formBox.getChildren().add(gridpane);

		// organisation :
		gridpane.setVgap(30); // Espace vertical entre les lignes
		gridpane.setHgap(60); // Espace horizontal entre les colonnes

		// ajouter une marge intérieure sur tous les côtés du GridPane
		gridpane.setPadding(new Insets(40));
		gridpane.setAlignment(Pos.CENTER);

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

		// Ajouter un ChangeListener pour restreindre l'entrée du champ nom
		nameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			// vérifie si newValue ne contient que des lettres alphabétiques, majuscules ou
			// minuscules.
			if (!newValue.matches("[a-zA-ZÀ-ÿ ]*")) {
				nameTextfield.setText(newValue.replaceAll("[^a-zA-ZÀ-ÿ ]", ""));

			}
			// Limite la longueur à 20 caractères
			if (newValue.length() > 20) {
				// Rétablit l'ancienne valeur si la nouvelle dépasse 20 caractères
				nameTextfield.setText(oldValue);
			}
		});

		// Ajouter un ChangeListener pour restreindre l'entrée du champ firstName
		firstnameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-ZÀ-ÿ ]*")) {
				firstnameTextfield.setText(newValue.replaceAll("[^a-zA-ZÀ-ÿ ]", ""));
			}
			// Limite la longueur à 20 caractères
			if (newValue.length() > 20) {
				// Rétablit l'ancienne valeur si la nouvelle dépasse 20 caractères
				firstnameTextfield.setText(oldValue);
			}
		});

		// Ajouter un ChangeListener pour restreindre l'entrée du champ
		// postalCodeTextfield
		postalCodeTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z0-9 ]*")) {
				// Filtre les caractères non alphabétiques et non numériques
				postalCodeTextfield.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));

			}
			// Limiter la longueur
			if (newValue.length() > 8) {
				postalCodeTextfield.setText(oldValue);

			}
		});

		// Ajouter un ChangeListener pour restreindre l'entrée du champ promoTextfield
		promoTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z0-9 ]*")) {
				// Filtre les caractères non alphabétiques et non numériques
				promoTextfield.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
			}
			// Limiter la longueur
			if (newValue.length() > 10) {
				promoTextfield.setText(oldValue);

			}
		});

		// Ajouter un ChangeListener pour restreindre l'entrée du champ à uniquement des
		// nombres
		yearTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			// Vérifie si newValue contient uniquement des chiffres
			if (!newValue.matches("\\d*")) {
				// Filtre les caractères non numériques
				yearTextfield.setText(newValue.replaceAll("[^\\d]", ""));
			}
			// Limite la longueur à 4 chiffres
			if (newValue.length() > 4) {
				yearTextfield.setText(oldValue);
			}
		});

		HBox buttons = new HBox();
		formBox.getChildren().add(buttons);

		// création des boutons
		Button cancelButton = new Button("Annuler");
		Button validateButton = new Button("Valider");
		buttons.getChildren().addAll(cancelButton, validateButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.setMargin(cancelButton, new Insets(0, 100, 40, 0));
		;
		buttons.setMargin(validateButton, new Insets(0, 0, 40, 100));
		;

		// creation de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		validateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean isValid = true;
				// Vérification pour le champ nameTextfield
				if (nameTextfield.getText().isEmpty()) {
					nameTextfield.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					nameTextfield.getStyleClass().remove("text-field-error");
				}
				// Vérification pour le champ firstnameTextfield
				if (firstnameTextfield.getText().isEmpty()) {
					firstnameTextfield.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					firstnameTextfield.getStyleClass().remove("text-field-error");
				}
				// Vérification pour le champ postalCodeTextfield
				if (postalCodeTextfield.getText().isEmpty()) {
					postalCodeTextfield.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					postalCodeTextfield.getStyleClass().remove("text-field-error");
				}
				// Vérification pour le champ promoTextfield
				if (promoTextfield.getText().isEmpty()) {
					promoTextfield.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					promoTextfield.getStyleClass().remove("text-field-error");
				}
				// Vérification pour le champ yearTextfield
				if (yearTextfield.getText().isEmpty()) {
					yearTextfield.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					yearTextfield.getStyleClass().remove("text-field-error");
				}

				// Si tous les champs sont valides
				if (isValid) {

					// Récupérer les valeurs des champs
					String name = nameTextfield.getText();
					String firstName = firstnameTextfield.getText();
					String postalCode = postalCodeTextfield.getText();
					String promo = promoTextfield.getText();
					String sYear = yearTextfield.getText();
					int year = 0;
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
