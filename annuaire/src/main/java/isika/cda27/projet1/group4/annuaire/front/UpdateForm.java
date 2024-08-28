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

public class UpdateForm extends BorderPane {
	
	private Scene scene;
	private App app;
	private Stage stage;
	private Stagiaire selectedStagiaire;

	public UpdateForm(App app, Stage stage, Stagiaire selectedStagiaire) {
		super();
		this.app = app;
		this.stage = stage;
		this.selectedStagiaire=selectedStagiaire;

		// ajout du header
		this.setTop(new Header(app, stage, "Modifier un stagiaire"));
		// Masquer le champ de recherche
	    this.setSearchVisible(false);
	    
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
		
		Label lblConsigne = new Label("Veuillez modifier les champs souhaités pour modifier le stagiaire");
		consigne.getChildren().add(lblConsigne);
		consigne.setPadding(new Insets (40, 0, 0, 0));
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
		TextField nameTextfield = new TextField(selectedStagiaire.getName());
		gridpane.add(nameLabel, 0, 0); // (colonne/ligne)
		gridpane.add(nameTextfield, 1, 0);

		Label firstnameLabel = new Label(" Prénom ");
		TextField firstnameTextfield = new TextField(selectedStagiaire.getFirstName());
		gridpane.add(firstnameLabel, 0, 1); // (colonne/ligne)
		gridpane.add(firstnameTextfield, 1, 1);

		Label postalCodeLabel = new Label(" Département ");
		TextField postalCodeTextfield = new TextField(selectedStagiaire.getPostalCode());
		gridpane.add(postalCodeLabel, 0, 2); // (colonne/ligne)
		gridpane.add(postalCodeTextfield, 1, 2);

		Label promoLabel = new Label(" Promotion ");
		TextField promoTextfield = new TextField(selectedStagiaire.getPromo());
		gridpane.add(promoLabel, 0, 3); // (colonne/ligne)
		gridpane.add(promoTextfield, 1, 3);

		Label yearLabel = new Label(" Année ");
		TextField yearTextfield = new TextField(String.valueOf(selectedStagiaire.getYear()));
		gridpane.add(yearLabel, 0, 4); // (colonne/ligne)
		gridpane.add(yearTextfield, 1, 4);

		
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
			Stagiaire newStagiaire = new Stagiaire(name, firstName, postalCode, promo, year);
			
			// Appeler la méthode update du DAO
            app.myDAO.updateStagiaire(selectedStagiaire, newStagiaire);
            app.myObservableArrayList.setAll(app.myDAO.getStagiaires());
            
							
	
         // Revenir à la scène précédente
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
	
	public void setSearchVisible(boolean visible) {
	    // Trouver le Header et modifier la visibilité du champ de recherche
	    Header header = (Header) this.getTop();
	    header.getSearchBox().setVisible(visible);
	}
	
	

}
