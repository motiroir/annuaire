package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.back.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
		this.setTop(new Header(app, stage, "Connexion"));
		
		//marges sur les côtés
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		leftBox.setPrefWidth(150);
		rightBox.setPrefWidth(150);
		this.setLeft(leftBox);
		this.setRight(rightBox);
		
		// boite à formulaire
		VBox formBox = new VBox();
		formBox.setMaxSize(600, 400);
		formBox.setStyle("-fx-border-color: #d3d3d3; " +   // Couleur de la bordure
                "-fx-border-width: 2px; " +   // Épaisseur de la bordure
                "-fx-border-radius: 5px;");   // Rayon pour les coins arrondis
		this.setCenter(formBox);
		
		//Instruction
		HBox consigne = new HBox();
		formBox.getChildren().add(consigne);
		
		Label lblConsigne = new Label("Veuillez remplir les champs suivants pour vous identifier");
		consigne.getChildren().add(lblConsigne);
		consigne.setPadding(new Insets (40, 0, 0, 0));
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
		buttons.setMargin(cancelButton, new Insets(0, 100, 40, 0));;
		buttons.setMargin(validateButton, new Insets(0, 0, 40, 100));;

		// creation de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		
		
		validateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Récupérer les valeurs des champs
				String username = usernameField.getText();
	            String password = passwordField.getText();
	            app.currentUser = app.userManager.authenticate(username, password);
	            if (app.currentUser != null) {
	                System.out.println("utilisateur : "+app.currentUser.getUsername()+" connecté en tant que : "+ app.currentUser.getRole());
	             // Revenir à la scène précédente (accueil)
					HomePage homepage = new HomePage(app, stage);
					stage.setScene(homepage.getScene());
	            } else {
	                Alert alert = new Alert(Alert.AlertType.NONE, "Nom d'utilisateur ou mot de passe invalide", ButtonType.OK);
	                
	                // Appliquer le fichier CSS
	                DialogPane dialogPane = alert.getDialogPane();
	                dialogPane.getStylesheets().add(
	                    getClass().getResource("/css/style.css").toExternalForm()
	                );
	                dialogPane.getStyleClass().add("alert");
	                
	                alert.show();
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