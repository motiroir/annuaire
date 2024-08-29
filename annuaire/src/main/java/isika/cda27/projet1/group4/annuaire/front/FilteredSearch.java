package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FilteredSearch extends HBox {

	private TextField nameField;
	private TextField firstNameField;
	private TextField promotionField;
	private TextField departmentField;
	private TextField emailField;
	private Button toggleButton;
	private Button filterButton;
	private App app;

	public FilteredSearch(App app) {
		this.app = app;

		// Initialisation des champs de texte pour chaque critère
		nameField = new TextField();
		nameField.setPromptText("Nom");

		firstNameField = new TextField();
		firstNameField.setPromptText("Prénom...");

		promotionField = new TextField();
		promotionField.setPromptText("Département...");

		departmentField = new TextField();
		departmentField.setPromptText("Promotion...");

		emailField = new TextField();
		emailField.setPromptText("Année...");

		// Créer le bouton avec l'icône de recherche
		Image searchIcon = new Image(getClass().getResourceAsStream("/icons/__search-icon.png"));
		ImageView buttonImageView = new ImageView(searchIcon);
		buttonImageView.setFitHeight(20);
		buttonImageView.setFitWidth(20);
		toggleButton = new Button("", buttonImageView);

		// Créer le bouton pour accéder à la recherche avancée
		Image filterIcon = new Image(getClass().getResourceAsStream("/icons/__filterOff-icon.png"));
		ImageView filterImageView = new ImageView(filterIcon);
		filterImageView.setFitHeight(20);
		filterImageView.setFitWidth(20);
		filterButton = new Button("", filterImageView);

		// Ajouter les champs au HBox avec les boutons
		this.getChildren().addAll(nameField, firstNameField, promotionField, departmentField, emailField, toggleButton,
				filterButton);
		this.setSpacing(10);

		// Ajouter style css
		toggleButton.getStyleClass().add("button-search");
		filterButton.getStyleClass().add("button-search");

		// Gestion de l'action des boutons
		toggleButton.setOnAction(event -> updateSearchResults());
		
		filterButton.setOnAction(event -> {
		    
		});
		
		
	}

	private void updateSearchResults() {
		String nameQuery = nameField.getText().trim().toLowerCase();
		String firstNameQuery = firstNameField.getText().trim().toLowerCase();
		String promotionQuery = promotionField.getText().trim().toLowerCase();
		String departmentQuery = departmentField.getText().trim().toLowerCase();
		String emailQuery = emailField.getText().trim().toLowerCase();

		// List<Stagiaire> results = app.myDAO.searchByCriteria(nameQuery,
		// firstNameQuery, promotionQuery, departmentQuery, emailQuery);
		// app.myObservableArrayList.setAll(results);
	}

	public Button getToggleButton() {
		return toggleButton;
	}

	public Button getFilterButton() {
		return filterButton;
	}

}
