package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.back.UserManager;
import isika.cda27.projet1.group4.annuaire.back.exceptions.AuthenticationFailedException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		Header header = new Header(app, stage, "Connexion");
		this.setTop(header);
		// Affichage du champ de recherche
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

		Label lblConsigne = new Label("Veuillez remplir les champs suivants pour vous identifier");
		consigne.getChildren().add(lblConsigne);
		consigne.setPadding(new Insets(40, 0, 0, 0));
		consigne.setAlignment(Pos.CENTER);

		// gridpane au centre non rempli
		GridPane gridpane = new GridPane();
		formBox.getChildren().add(gridpane);

		// organisation :
		gridpane.setVgap(30); // Espace vertical entre les lignes
		gridpane.setHgap(60); // Espace horizontal entre les colonnes

		// ajouter une marge intérieure sur tous les côtés du GridPane
		gridpane.setPadding(new Insets(40));
		gridpane.setAlignment(Pos.CENTER);

		// remplir la GridPane avec les labels et les textfields

		Label nameLabel = new Label(" Nom d'utilisateur ");
		TextField usernameField = new TextField();
		gridpane.add(nameLabel, 0, 0); // (colonne/ligne)
		gridpane.add(usernameField, 1, 0);

		Label firstnameLabel = new Label(" Mot de passe");
		PasswordField passwordField = new PasswordField();
		gridpane.add(firstnameLabel, 0, 1); // (colonne/ligne)
		gridpane.add(passwordField, 1, 1);

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

		        // Vérification pour le champ usernameField
		        if (usernameField.getText().isEmpty()) {
		            usernameField.getStyleClass().add("text-field-error");
		            isValid = false;
		        } else {
		            usernameField.getStyleClass().remove("text-field-error");
		        }

		        // Vérification pour le champ passwordField
		        if (passwordField.getText().isEmpty()) {
		            passwordField.getStyleClass().add("text-field-error");
		            isValid = false;
		        } else {
		            passwordField.getStyleClass().remove("text-field-error");
		        }

		        // Si tous les champs sont valides
		        if (isValid) {
		            // Récupérer les valeurs des champs
		            String username = usernameField.getText();
		            String password = passwordField.getText();
		            try {
		                // Tentative de connexion
		                app.currentUser = app.userManager.login(username, password);

		                // Connexion réussie
		                //System.out.println("Utilisateur : " + app.currentUser.getUsername() + " connecté en tant que : " + app.currentUser.getRole());
		                
		                // Revenir à la scène précédente (accueil)
		                HomePage homepage = new HomePage(app, stage);
		                stage.setScene(homepage.getScene());

		            } catch (AuthenticationFailedException e) {
		                // Afficher un message d'erreur en cas d'exception
		                Dialog<String> dialog = new Dialog<>();
		                dialog.setTitle("Erreur de connexion");
		                dialog.setHeaderText(null);

		                VBox vbox = new VBox(10);
		                vbox.setAlignment(Pos.CENTER);
		                vbox.setMinSize(350,150);

		                Label messageLabel = new Label("Nom d'utilisateur ou mot de passe invalide");
		                messageLabel.getStyleClass().add("alert");
		                VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15));

		                Button okButton = new Button("OK");
		                okButton.setOnAction(event1 -> dialog.setResult("ok"));
		                
		                VBox.setMargin(okButton, new Insets(5, 10, 20, 10));

		                vbox.getChildren().addAll(messageLabel, okButton);

		                DialogPane dialogPane = dialog.getDialogPane();
		                dialogPane.setContent(vbox);
		                dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		                dialogPane.getStyleClass().add("alert");

		                dialog.showAndWait(); // Attendre que l'utilisateur clique sur OK
		            }
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