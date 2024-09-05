package isika.cda27.projet1.group4.annuaire.front;

import java.util.List;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * La classe FilteredSearch permet de créer une interface de recherche filtrée
 * pour les stagiaires avec différents champs (nom, prénom, département, 
 * promotion, année). Elle contient également des boutons pour déclencher 
 * la recherche et accéder à des filtres avancés.
 */
public class FilteredSearch extends HBox {

    /** Champ de saisie pour le nom du stagiaire. */
    private TextField nameField;

    /** Champ de saisie pour le prénom du stagiaire. */
    private TextField firstNameField;

    /** Champ de saisie pour le département du stagiaire. */
    private TextField departmentField;

    /** Champ de saisie pour la promotion du stagiaire. */
    private TextField promotionField;

    /** Champ de saisie pour l'année du stagiaire. */
    private TextField yearField;

    /** Bouton pour lancer la recherche avec les critères spécifiés. */
    private Button toggleButton;

    /** Bouton pour afficher ou masquer les filtres avancés. */
    private Button filterButton;

    /** Référence à l'application principale. */
    private App app;

    /**
     * Constructeur de la classe FilteredSearch.
     * Initialise les champs de recherche et les boutons, et configure leur action.
     *
     * @param app L'instance de l'application principale.
     */
    public FilteredSearch(App app) {
        this.app = app;

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

        // Ajouter style CSS
        toggleButton.getStyleClass().add("button-search");
        filterButton.getStyleClass().add("button-search");

        // Initialisation des champs de texte pour chaque critère
        nameField = new TextField();
        nameField.setPromptText("Nom");

        firstNameField = new TextField();
        firstNameField.setPromptText("Prénom...");

        departmentField = new TextField();
        departmentField.setPromptText("Département...");

        promotionField = new TextField();
        promotionField.setPromptText("Promotion...");

        yearField = new TextField();
        yearField.setPromptText("Année...");

        // Ajouter les champs au HBox avec les boutons
        this.getChildren().addAll(nameField, firstNameField, departmentField, promotionField, yearField, toggleButton, filterButton);
        this.setSpacing(10);

        toggleButton.setVisible(true);
        filterButton.setVisible(true);

        // Gestion de l'action du bouton de recherche
        toggleButton.setOnAction(event -> {

            // Crée un filtre pour les stagiaires
            StagiaireFilter stagiairefilter = new StagiaireFilter(app.myDAO);

            // Récupère les valeurs des champs
            String name = nameField.getText();
            String firstName = firstNameField.getText();
            String postalCode = departmentField.getText();
            String promo = promotionField.getText();
            String year = yearField.getText();

            // Filtre les stagiaires en fonction des critères
            List<Stagiaire> filteredStagiaires = stagiairefilter.filterStagiaires(name, firstName, postalCode, promo, year);

            // Met à jour la liste observable avec les stagiaires filtrés
            app.myObservableArrayList.setAll(filteredStagiaires);
        });

        // Gestion de l'action du bouton de filtres avancés
        filterButton.setOnAction(event -> {
            // Logique des filtres avancés à implémenter
        });
    }

    /**
     * Retourne le bouton de recherche.
     *
     * @return Le bouton de recherche toggleButton.
     */
    public Button getToggleButton() {
        return toggleButton;
    }

    /**
     * Retourne le bouton des filtres avancés.
     *
     * @return Le bouton de filtres filterButton.
     */
    public Button getFilterButton() {
        return filterButton;
    }
}
