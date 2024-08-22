package isika.cda27.projet1.group4.annuaire;

import isika.cda27.projet1.group4.annuaire.back.Annuaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	
    	
        Annuaire annuaire = new Annuaire();
        annuaire.lireFichier("src/main/resources/test.txt");
        annuaire.afficherStagiaires();
        
        // on créé une listView pour afficher la liste de stagiaire présent dans l'annuaire
        ListView<String> listView = new ListView<>();
        for (int i=0;i<annuaire.getStagiaires().size();i++) {
        	listView.getItems().add(annuaire.getStagiaires().get(i).toString());
        }
        
        Scene scene = new Scene(new StackPane(listView), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}