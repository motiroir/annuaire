package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Header extends HBox {
	
	private String title;
	private String subtitle;
	
	public Header(App app, Stage stage, String subtitle) {
		super();
		this.title = "ISIKA";
		this.subtitle = subtitle;
		
		VBox titles = new VBox();
		
		Label lblTitle = new Label(title);
		Label lblSubtitle = new Label(subtitle);
		
		lblTitle.getStyleClass().add("title");
		
		titles.getChildren().addAll(lblTitle, lblSubtitle);
		titles.setAlignment(Pos.CENTER);
		
		this.getChildren().add(titles);
		this.setAlignment(Pos.CENTER);
		
		
		//bouton de connexion
		Button buttonConnexion = new Button("Connexion");
		this.getChildren().add(buttonConnexion);
		this.setMargin(buttonConnexion, new Insets(10, 10, 20, 540));
		
		
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
