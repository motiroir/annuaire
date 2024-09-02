package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.FileExporter;
import isika.cda27.projet1.group4.annuaire.back.FileImporter;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends BorderPane {

	private Scene scene;
	private App app;
	private Stage stage;
	private TableViewStagiaires tableView;

	public HomePage(App app, Stage stage) {
		this.app = app;
		this.stage = stage;

		// création de la Hbox header
		Header header = new Header(app, stage, "Liste des Stagiaires");
		this.setTop(header);
		// Affichage du champ de recherche
		header.getSearchBox().setVisible(true);

		// marges
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		leftBox.setPrefWidth(150);
		rightBox.setPrefWidth(150);
		this.setLeft(leftBox);
		this.setRight(rightBox);

		// Ajouter le TableView au BorderPane
		this.tableView = new TableViewStagiaires(app.myObservableArrayList);
		this.setCenter(tableView);
		this.setAlignment(tableView, Pos.CENTER);
		tableView.setVisible(false);

		if (app.firstConnexion == true) {
			header.getSearchBox().setVisible(false);
			
			VBox welcomeMessage = new VBox();
			welcomeMessage.setAlignment(Pos.CENTER);
			
			Label welcomeLbl = new Label("Bienvenue sur l'annuaire d'Isika");
			Label importerLbl = new Label(
					"Il n'y a pas d'annuaire disponible actuellement. \nPour commencer veuillez en importer un.");
			welcomeLbl.getStyleClass().add("title");
			importerLbl.getStyleClass().add("sub-title");
			
			welcomeMessage.getChildren().addAll(welcomeLbl, importerLbl);
			this.setCenter(welcomeMessage);
		} else {
			tableView.setVisible(true);
		}

		// création de la Hbox Bottom
		HBox hboxBottom = new HBox();
		this.setBottom(hboxBottom);


		Footer footer = new Footer(app, stage, app.myObservableArrayList, this);
		Button importFirst = new Button("Importer un annuaire");

		if (app.firstConnexion == true) {
			HBox footerFirstConnexion = new HBox();
			footerFirstConnexion.setAlignment(Pos.CENTER);
			footerFirstConnexion.setMargin(importFirst, new Insets(20));
			footerFirstConnexion.getChildren().add(importFirst);
			this.setBottom(footerFirstConnexion);
			this.setAlignment(footerFirstConnexion, Pos.CENTER);
		} else {
			this.setBottom(footer);
		}

		importFirst.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileImporter importer = new FileImporter();
				String fileContent = importer.importer(stage, app);
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Importation d'un nouvel annuaire");
//			alert.setHeaderText(null);
//			alert.setContentText(fileContent);

				// Créer un dialogue
				Dialog<String> dialog = new Dialog<>();
				dialog.setTitle("");
				dialog.setHeaderText(null);

				VBox vbox = new VBox(10);
				vbox.setAlignment(Pos.CENTER);

				Label messageLabel = new Label(fileContent);
				messageLabel.getStyleClass().add("alert");
				VBox.setMargin(messageLabel, new Insets(20)); // Marges haut, droite, bas, gauche

				Button okButton = new Button("OK");

				okButton.setOnAction(e -> {
					dialog.setResult("ok");
					dialog.close();
					// Recharger la scène
                    app.firstConnexion = false;
                    HomePage newHomePage = new HomePage(app, stage);
                    stage.setScene(newHomePage.getScene());
				});
				VBox.setMargin(okButton, new Insets(20));

				HBox buttonHbox = new HBox();
				buttonHbox.setAlignment(Pos.CENTER_RIGHT);
				buttonHbox.getChildren().addAll(okButton);
				HBox.setMargin(okButton, new Insets(20));
				vbox.getChildren().addAll(messageLabel, buttonHbox);

				DialogPane dialogPane = dialog.getDialogPane();
				dialogPane.setContent(vbox);
				dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
				dialogPane.getStyleClass().add("alert");

				
				dialog.showAndWait(); // Attendre la réponse de l'utilisateur
				
				
			}
		});

		// création de la scene
		this.scene = new Scene(this, 1280, 720);
		// Lien avec le css
		this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

	}

	// Méthode pour obtenir TableViewStagiaires
	public TableViewStagiaires getTableView() {
		return tableView;
	}



}
