package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Role;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.back.exceptions.UserAlreadyExistsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateUser extends BorderPane {
	
	
	
	private Scene scene;
	private App app;
	private Stage stage;

	public CreateUser(App app, Stage stage) {
		super();
		this.app = app;
		this.stage = stage;

		// ajout du header
		this.setTop(new Header(app, stage, "Ajouter un utilisateur"));
		// Masquer le champ de recherche
		this.setSearchVisible(false);

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

		Label lblConsigne = new Label("Veuillez remplir les champs suivants pour ajouter un nouveau utilisateur ");
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

		Label nameLabel = new Label(" Nom d'utilisateur ");
		TextField nameTextfield = new TextField();
		gridpane.add(nameLabel, 0, 0); // (colonne/ligne)
		gridpane.add(nameTextfield, 1, 0);

		Label passwordLabel = new Label(" Mot de passe");
		PasswordField passwordField = new PasswordField();
		gridpane.add(passwordLabel, 0, 1); // (colonne/ligne)
		gridpane.add(passwordField, 1, 1);
		
		
		Label roleLabel = new Label(" Role ");
		gridpane.add(roleLabel, 0, 2); // (colonne/ligne)
		
		 // Menu déroulant pour le choix du rôle
        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(Role.values());  // Ajouter les rôles à la ComboBox
        roleComboBox.setPromptText("Sélectionner un rôle");
        
        
	    gridpane.add(roleComboBox, 1, 2);

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
				if (passwordField.getText().isEmpty()) {
					passwordField.getStyleClass().add("text-field-error");
					isValid = false;
				} else {
					passwordField.getStyleClass().remove("text-field-error");
				}
				
				
				
				// Si tous les champs sont valides
				if (isValid) {
					
					// Récupérer les valeurs des champs
					String username = nameTextfield.getText();
					String passwordHash = passwordField.getText();
				    Role selectedRole = roleComboBox.getValue();
				 
					
			
					// Créer un nouveau 
					User newUser = new User (username, passwordHash, selectedRole);
					// Ajouter le stagiaire via le DAO
					try {
						app.userManager.createUser(username, passwordHash, selectedRole);
					} catch (UserAlreadyExistsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					app.usersDAO.setAll(app.userManager.getUsers());
					// Revenir à la scène précédente (accueil)
					ManageUsers manageUsers = new ManageUsers(app, stage);
					stage.setScene(manageUsers.getScene());

				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Revenir à la scène précédente (accueil)
				ManageUsers manageUsers = new ManageUsers(app, stage);
				stage.setScene(manageUsers.getScene());

			}
		});

	}
	public void setSearchVisible(boolean visible) {
		// Trouver le Header et modifier la visibilité du champ de recherche
		Header header = (Header) this.getTop();
		header.getSearchBox().setVisible(visible);
	}

}
