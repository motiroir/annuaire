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

/**
 * Classe AddForm - Génère un formulaire permettant d'ajouter un nouveau stagiaire.
 * Cette classe crée une interface utilisateur avec plusieurs champs de texte
 * permettant de renseigner le nom, prénom, département, promotion et année du stagiaire.
 * Des boutons permettent de valider ou annuler l'opération.
 */
public class AddForm extends BorderPane {

    /** Scène actuelle de l'interface utilisateur. */
    private Scene scene;

    /** Référence à l'application principale. */
    private App app;

    /** Fenêtre dans laquelle le formulaire est affiché. */
    private Stage stage;

    /**
     * Constructeur de la classe AddForm.
     * Il configure l'interface utilisateur et initialise les éléments du formulaire.
     *
     * @param app   L'instance de l'application principale.
     * @param stage La fenêtre dans laquelle le formulaire est affiché.
     */
    public AddForm(App app, Stage stage) {
        super();
        this.app = app;
        this.stage = stage;

        // Initialisation du header avec un titre spécifique "Ajouter un stagiaire".
        Header header = new Header(app, stage, "Ajouter un stagiaire");
        this.setTop(header);
        header.getSearchBox().setVisible(false); // Masquer le champ de recherche.

        // Configuration des marges sur les côtés.
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();
        leftBox.setPrefWidth(150);
        rightBox.setPrefWidth(150);
        this.setLeft(leftBox);
        this.setRight(rightBox);

        // Boîte principale contenant le formulaire.
        VBox formBox = new VBox();
        formBox.setMaxSize(600, 400);
        formBox.setStyle("-fx-border-color: #d3d3d3; -fx-border-width: 2px; -fx-border-radius: 5px;");
        this.setCenter(formBox);

        // Consignes pour l'utilisateur.
        HBox consigne = new HBox();
        formBox.getChildren().add(consigne);

        Label lblConsigne = new Label("Veuillez remplir les champs suivants pour ajouter un nouveau stagiaire");
        consigne.getChildren().add(lblConsigne);
        consigne.setPadding(new Insets(40, 0, 0, 0));
        consigne.setAlignment(Pos.CENTER);

        // Création de la grille pour les champs de saisie.
        GridPane gridpane = new GridPane();
        formBox.getChildren().add(gridpane);

        gridpane.setVgap(30); // Espacement vertical entre les champs.
        gridpane.setHgap(60); // Espacement horizontal entre les champs.
        gridpane.setPadding(new Insets(40));
        gridpane.setAlignment(Pos.CENTER);

        // Ajout des labels et des champs de texte au formulaire.
        Label nameLabel = new Label(" Nom ");
        TextField nameTextfield = new TextField();
        gridpane.add(nameLabel, 0, 0);
        gridpane.add(nameTextfield, 1, 0);

        Label firstnameLabel = new Label(" Prénom ");
        TextField firstnameTextfield = new TextField();
        gridpane.add(firstnameLabel, 0, 1);
        gridpane.add(firstnameTextfield, 1, 1);

        Label postalCodeLabel = new Label(" Département ");
        TextField postalCodeTextfield = new TextField();
        gridpane.add(postalCodeLabel, 0, 2);
        gridpane.add(postalCodeTextfield, 1, 2);

        Label promoLabel = new Label(" Promotion ");
        TextField promoTextfield = new TextField();
        gridpane.add(promoLabel, 0, 3);
        gridpane.add(promoTextfield, 1, 3);

        Label yearLabel = new Label(" Année ");
        TextField yearTextfield = new TextField();
        gridpane.add(yearLabel, 0, 4);
        gridpane.add(yearTextfield, 1, 4);

        // Ajout de restrictions pour les champs de saisie.
        // Restriction pour le champ "Nom".
        nameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZÀ-ÿ ]*")) {
                nameTextfield.setText(newValue.replaceAll("[^a-zA-ZÀ-ÿ ]", ""));
            }
            if (newValue.length() > 20) {
                nameTextfield.setText(oldValue);
            }
        });

        // Restriction pour le champ "Prénom".
        firstnameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZÀ-ÿ ]*")) {
                firstnameTextfield.setText(newValue.replaceAll("[^a-zA-ZÀ-ÿ ]", ""));
            }
            if (newValue.length() > 20) {
                firstnameTextfield.setText(oldValue);
            }
        });

        // Restriction pour le champ "Département".
        postalCodeTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                postalCodeTextfield.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
            }
            if (newValue.length() > 8) {
                postalCodeTextfield.setText(oldValue);
            }
        });

        // Restriction pour le champ "Promotion".
        promoTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                promoTextfield.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
            }
            if (newValue.length() > 10) {
                promoTextfield.setText(oldValue);
            }
        });

        // Restriction pour le champ "Année" (n'accepte que les nombres).
        yearTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearTextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 4) {
                yearTextfield.setText(oldValue);
            }
        });

        // Création des boutons "Annuler" et "Valider".
        HBox buttons = new HBox();
        formBox.getChildren().add(buttons);

        Button cancelButton = new Button("Annuler");
        Button validateButton = new Button("Valider");
        buttons.getChildren().addAll(cancelButton, validateButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setMargin(cancelButton, new Insets(0, 100, 40, 0));
        buttons.setMargin(validateButton, new Insets(0, 0, 40, 100));

        // Scène de l'interface utilisateur.
        this.scene = new Scene(this, 1280, 720);
        this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        // Action du bouton "Valider" pour ajouter un stagiaire après validation.
        validateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isValid = true;

                // Vérification des champs de texte.
                if (nameTextfield.getText().isEmpty()) {
                    nameTextfield.getStyleClass().add("text-field-error");
                    isValid = false;
                } else {
                    nameTextfield.getStyleClass().remove("text-field-error");
                }

                if (firstnameTextfield.getText().isEmpty()) {
                    firstnameTextfield.getStyleClass().add("text-field-error");
                    isValid = false;
                } else {
                    firstnameTextfield.getStyleClass().remove("text-field-error");
                }

                if (postalCodeTextfield.getText().isEmpty()) {
                    postalCodeTextfield.getStyleClass().add("text-field-error");
                    isValid = false;
                } else {
                    postalCodeTextfield.getStyleClass().remove("text-field-error");
                }

                if (promoTextfield.getText().isEmpty()) {
                    promoTextfield.getStyleClass().add("text-field-error");
                    isValid = false;
                } else {
                    promoTextfield.getStyleClass().remove("text-field-error");
                }

                if (yearTextfield.getText().isEmpty()) {
                    yearTextfield.getStyleClass().add("text-field-error");
                    isValid = false;
                } else {
                    yearTextfield.getStyleClass().remove("text-field-error");
                }

                // Si tous les champs sont valides, le stagiaire est ajouté.
                if (isValid) {
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

                    // Création d'un nouvel objet Stagiaire.
                    Stagiaire stagiaire = new Stagiaire(name, firstName, postalCode, promo, year);

                    // Ajout du stagiaire via DAO.
                    app.myDAO.addStagiaire(stagiaire);
                    app.myObservableArrayList.setAll(app.myDAO.getStagiaires());

                    // Revenir à la page d'accueil.
                    HomePage homepage = new HomePage(app, stage);
                    stage.setScene(homepage.getScene());
                }
            }
        });

        // Gestion des événements pour le bouton "Annuler".
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Revenir à la page d'accueil.
                HomePage homepage = new HomePage(app, stage);
                stage.setScene(homepage.getScene());
            }
        });
    }

    
}
