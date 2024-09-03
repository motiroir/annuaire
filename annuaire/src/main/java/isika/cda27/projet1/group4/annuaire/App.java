package isika.cda27.projet1.group4.annuaire;


import isika.cda27.projet1.group4.annuaire.back.FileChecker;
import isika.cda27.projet1.group4.annuaire.back.Role;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.back.UserManager;
import isika.cda27.projet1.group4.annuaire.back.exceptions.UserAlreadyExistsException;
import isika.cda27.projet1.group4.annuaire.front.AnnuaireDAO;
import isika.cda27.projet1.group4.annuaire.front.HomePage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class App extends Application {

	public AnnuaireDAO myDAO;
	public ObservableList<Stagiaire> myObservableArrayList;
	public UserManager userManager;
	public User currentUser;

    public ObservableList<User> usersDAO;
    

	public FileChecker fileChecker;
	public boolean firstConnexion;


	// methode dediée a l'initialisation
	@Override
	public void init() {

		// Initialisation des DAO et des UserManager
		fileChecker = new FileChecker();
		firstConnexion = !fileChecker.isDataBaseBinPresent();
		myDAO = new AnnuaireDAO();
		userManager = new UserManager();
		currentUser = new User();
		usersDAO = FXCollections.observableArrayList(userManager.getUsers());

	}

	@Override
	public void start(Stage stage) {
		
		stage.getIcons().setAll(new Image(getClass().getResource("/icons/annuaire.png").toExternalForm()));
		myObservableArrayList = FXCollections.observableArrayList(this.myDAO.getStagiaires());

		// Création de la première scène
		HomePage root = new HomePage(this, stage);

		// Lien avec le css
		root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		stage.setScene(root.getScene());
		stage.setTitle("Annuaire");
		stage.setOnCloseRequest(event -> handleCloseEvent(event));
		stage.show();
	}
	
    private void handleCloseEvent(WindowEvent event) {
        myDAO.searchTree.balanceTree();
    }

	public static void main(String[] args) {
		launch();
	}


}