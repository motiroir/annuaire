package isika.cda27.projet1.group4.annuaire.front;

import java.util.ArrayList;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Header extends StackPane {

	private String title;
	private String subtitle;
	private HBox searchBox;
	private App app;
	private Stage stage;

	public Header(App app, Stage stage, String subtitle) {
		super();
		this.title = "ISIKA";
		this.subtitle = subtitle;
		this.app=app;
		this.stage=stage;

		// Créer le VBox pour les titres
		VBox titles = new VBox();
		titles.setAlignment(Pos.CENTER); // Centre les titres horizontalement
		titles.setSpacing(5); // Espacement entre le titre et le sous-titre
		titles.setPadding(new Insets(10));

		Label lblTitle = new Label(title);
		Label lblSubtitle = new Label(subtitle);

		lblTitle.getStyleClass().add("title");
		lblSubtitle.getStyleClass().add("sub-title"); // Assurez-vous que le sous-titre utilise la bonne classe CSS

		titles.getChildren().addAll(lblTitle, lblSubtitle);
		
		// Créer le champ de recherche et le bouton de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        //Button searchButton = new Button("Rechercher");
        
        //Bouton de connexion avec icones
        // Créer le bouton de recherche avec une icône
        Image searchIcon = new Image(getClass().getResourceAsStream("/icons/__search-icon.png")); 
        ImageView searchImageView = new ImageView(searchIcon);
        Button searchButton = new Button("", searchImageView);

        // Créer le bouton de réinitialisation avec une icône
        Image resetIcon = new Image(getClass().getResourceAsStream("/icons/__reset-icon.png")); 
        ImageView resetImageView = new ImageView(resetIcon);
        Button resetButton = new Button("", searchImageView);
        
        // Appliquer les styles CSS
        searchButton.getStyleClass().add("button-search");
        resetButton.getStyleClass().add("button-reset");

        // Initialement, le bouton de réinitialisation est invisible
        resetButton.setVisible(false);
        
        // Créer un HBox pour contenir le champ et le bouton de recherche
        this.searchBox = new HBox(10, searchField, searchButton, resetButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setMaxWidth(300);

		// Créer le bouton de connexion
		Button buttonConnexion = new Button("Connexion");

		// Créer un separator pour le bas
		Separator bottomSeparator = new Separator();
		bottomSeparator.setPrefHeight(1); // Hauteur du trait

		// Ajouter le VBox des titres et le bouton de connexion au StackPane
		this.getChildren().addAll(titles, buttonConnexion, bottomSeparator, searchBox);
		
		// Aligner des elements du stackpane
		StackPane.setAlignment(titles, Pos.CENTER);
		StackPane.setAlignment(buttonConnexion, Pos.CENTER_RIGHT);
		StackPane.setAlignment(searchBox, Pos.CENTER_LEFT);
		StackPane.setAlignment(bottomSeparator, Pos.BOTTOM_CENTER);

		// Ajouter des marges au bouton pour l'écarter du bord
		StackPane.setMargin(buttonConnexion, new Insets(5, 40, 5, 0)); // Marges autour du bouton
		StackPane.setMargin(searchBox, new Insets(5, 0, 5, 40)); // Marges autour du bouton
		
		//Gestion de l'action du bouton rechercher
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        String query = searchField.getText();
		        List<Stagiaire> results = app.myDAO.searchByName(query);
		        app.myObservableArrayList.setAll(results);
		        
		        // Changer le bouton de recherche en bouton de réinitialisation
		        searchButton.setVisible(false);
		        resetButton.setVisible(true);
		    }
		});
		//Gestion de l'action du bouton rechercher (version reset)
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        // Réinitialiser la liste complète
		        app.myObservableArrayList.setAll(app.myDAO.getStagiaires());
		        
		        // Changer le bouton de réinitialisation en bouton de recherche
		        resetButton.setVisible(false);
		        searchButton.setVisible(true);
		        searchField.clear(); // Effacer le champ de recherche
		    }
		});
		

		buttonConnexion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserConnexion userConnexion = new UserConnexion(app, stage);
				stage.setScene(userConnexion.getScene());
				stage.setTitle("Page de connexion");
			}
		});
	}
	
	public HBox getSearchBox() {
	    return searchBox;
	}
	
	
	
	
}
