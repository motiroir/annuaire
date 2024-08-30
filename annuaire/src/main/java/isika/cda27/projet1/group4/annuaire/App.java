package isika.cda27.projet1.group4.annuaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;

import isika.cda27.projet1.group4.annuaire.back.Annuaire;
import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.Node;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.back.UserManager;
import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;
import isika.cda27.projet1.group4.annuaire.front.Header;
import isika.cda27.projet1.group4.annuaire.front.HomePage;
import isika.cda27.projet1.group4.annuaire.front.TableViewStagiaires;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	public AnnuaireDAO myDAO;
	public ObservableList<Stagiaire> myObservableArrayList;
	public UserManager userManager;
	public User currentUser;
	public FileChecker fileChecker;
	public boolean firstConnexion;

	// methode dediée a l'initialisation
	@Override
	public void init() {

		// Initialisation des DAO et des UserManager
		fileChecker = new FileChecker();
		firstConnexion = fileChecker.isDataBaseBinPresent();
		myDAO = new AnnuaireDAO();
		userManager = new UserManager();
		currentUser = new User();
	}

	@Override
	public void start(Stage stage) {

		myObservableArrayList = FXCollections.observableArrayList(this.myDAO.getStagiaires());

		// Création de la première scène
		HomePage root = new HomePage(this, stage);

		// Lien avec le css
		root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		stage.setScene(root.getScene());
		stage.setTitle("Annuaire");
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}