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
	private FilteredSearch filteredSearch;
	private VBox titles;
	// Variable pour suivre l'état des bouton
	boolean isSearchMode = true;

	public Header(App app, Stage stage, String subtitle) {
		super();
		this.title = "ISIKA";
		this.subtitle = subtitle;
		this.app = app;
		this.stage = stage;

		// Créer le VBox pour les titres
		titles = new VBox();
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
		searchField.setPromptText("Rechercher par nom...");

		// Créer le bouton avec l'icône de recherche
		Image searchIcon = new Image(getClass().getResourceAsStream("/icons/__search-icon.png"));
		ImageView buttonImageView = new ImageView(searchIcon);
		buttonImageView.setFitHeight(20);
		buttonImageView.setFitWidth(20);
		Button toggleButton = new Button("", buttonImageView);

		// Créer le bouton pour accéder à la recherche avancée
		Image filterhIcon = new Image(getClass().getResourceAsStream("/icons/__filter-icon.png"));
		ImageView filterImageView = new ImageView(filterhIcon);
		filterImageView.setFitHeight(20);
		filterImageView.setFitWidth(20);
		Button filterButton = new Button("", filterImageView);

		// Appliquer les styles CSS
		toggleButton.getStyleClass().add("button-search");
		filterButton.getStyleClass().add("button-search");

		// Créer un HBox pour contenir le champ et le bouton de recherche
		this.searchBox = new HBox(10, searchField, toggleButton, filterButton);
		searchBox.setAlignment(Pos.CENTER_LEFT);
		searchBox.setMaxWidth(300);

		// Créer la FilteredSearch mais la rendre invisible au départ
		filteredSearch = new FilteredSearch(app);
		filteredSearch.setVisible(false);
		filteredSearch.setAlignment(Pos.CENTER_LEFT);

		// Créer le bouton de connexion
		Button buttonConnexion = new Button("Connexion");

		// Créer un separator pour le bas
		Separator bottomSeparator = new Separator();
		bottomSeparator.setPrefHeight(1); // Hauteur du trait

		// Ajouter le VBox des titres et le bouton de connexion au StackPane
		this.getChildren().addAll(titles, searchBox, filteredSearch, buttonConnexion, bottomSeparator);

		// Aligner des elements du stackpane
		StackPane.setAlignment(titles, Pos.CENTER);
		StackPane.setAlignment(buttonConnexion, Pos.CENTER_RIGHT);
		StackPane.setAlignment(searchBox, Pos.CENTER_LEFT);
		StackPane.setAlignment(filteredSearch, Pos.CENTER_LEFT);
		StackPane.setAlignment(bottomSeparator, Pos.BOTTOM_CENTER);

		// Ajouter des marges
		StackPane.setMargin(buttonConnexion, new Insets(5, 40, 5, 0));
		StackPane.setMargin(searchBox, new Insets(5, 0, 5, 40));
		StackPane.setMargin(filteredSearch, new Insets(5, 0, 5, 40));

		// Gestion de l'action du bouton
		toggleButton.setOnAction(event -> {
			// Action de recherche
			String query = searchField.getText();

			if (searchField.getText().isEmpty()) {

				searchField.getStyleClass().add("text-field-error");

			} else {

				if (isSearchMode) {
					List<Stagiaire> results = app.myDAO.searchByName(query);
					app.myObservableArrayList.setAll(results);

					// Vérification des résultats et basculement du mode
					if (results != null && !results.isEmpty()) {
						// Changer l'image du bouton pour l'état de réinitialisation
						Image resetIcon = new Image(getClass().getResourceAsStream("/icons/__reset-icon.png"));
						buttonImageView.setImage(resetIcon);

						// Passer en mode réinitialisation
						isSearchMode = false;
					}
				} else {
					// Simplification pour vérification
					System.out.println("Entré dans le bloc else");

					// Réinitialiser la liste complète
					app.myObservableArrayList.setAll(app.myDAO.getStagiaires());

					// Changer l'image du bouton pour l'état de recherche
					Image searchIconAgain = new Image(getClass().getResourceAsStream("/icons/__search-icon.png"));
					buttonImageView.setImage(searchIconAgain);

					// Effacer le champ de recherche
					searchField.clear();

					// Passer en mode recherche
					isSearchMode = true;
				}

				searchField.getStyleClass().remove("text-field-error");
			}
		});

		filterButton.setOnAction(event -> {
			boolean isSearchBoxVisible = searchBox.isVisible();
			searchBox.setVisible(!isSearchBoxVisible);
			filteredSearch.setVisible(isSearchBoxVisible);
			titles.setVisible(!isSearchBoxVisible);
		});

//		filteredSearch.getToggleButton().setOnAction(toggleButton.getOnAction());
//		filteredSearch.getFilterButton().setOnAction(filterButton.getOnAction());

		buttonConnexion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserConnexion userConnexion = new UserConnexion(app, stage);
				stage.setScene(userConnexion.getScene());
			}
		});
	}

	public HBox getSearchBox() {
		return searchBox;
	}

}
