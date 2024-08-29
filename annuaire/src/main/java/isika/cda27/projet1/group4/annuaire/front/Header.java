package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Header extends StackPane {

	private String title;
	private String subtitle;

	public Header(App app, Stage stage, String subtitle) {
		super();
		this.title = "ISIKA";
		this.subtitle = subtitle;

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

		// Créer le bouton de connexion
		Button buttonConnexion = new Button("Connexion");

		// Créer un separator pour le bas
		Separator bottomSeparator = new Separator();
		bottomSeparator.setPrefHeight(1); // Hauteur du trait

		// Ajouter le VBox des titres et le bouton de connexion au StackPane
		this.getChildren().addAll(titles, buttonConnexion, bottomSeparator);
		
		// Aligner des elements du stackpane
		StackPane.setAlignment(titles, Pos.CENTER);
		StackPane.setAlignment(buttonConnexion, Pos.CENTER_RIGHT);
		StackPane.setAlignment(bottomSeparator, Pos.BOTTOM_CENTER);

		// Ajouter des marges au bouton pour l'écarter du bord
		StackPane.setMargin(buttonConnexion, new Insets(5, 40, 5, 0)); // Marges autour du bouton

		buttonConnexion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserConnexion userConnexion = new UserConnexion(app, stage);
				stage.setScene(userConnexion.getScene());
				stage.setTitle("Page de connexion");
			}
		});
	}
}
