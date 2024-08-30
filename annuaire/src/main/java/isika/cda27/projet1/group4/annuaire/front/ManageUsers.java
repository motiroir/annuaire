package isika.cda27.projet1.group4.annuaire.front;
import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageUsers extends VBox {
	
	
	private Scene scene;
    private App app;
    private Stage stage;
    private ObservableList<User> usersList;

    public ManageUsers(App app, Stage stage) {
        this.app = app;
        this.stage = stage;

        // Tableau des utilisateurs
        TableView<User> tableView = new TableView<>();
        usersList = FXCollections.observableArrayList(app.userManager.getAllUsers());
        tableView.setItems(usersList);

        // Colonnes du tableau
        TableColumn<User, String> usernameCol = new TableColumn<>("Nom d'utilisateur");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> roleCol = new TableColumn<>("Rôle");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(usernameCol, roleCol);

        // Boutons d'action
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button addButton = new Button("Ajouter");
        Button updateButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        this.getChildren().addAll(tableView, buttonBox);
	
	
        // Création de la scène
        this.scene = new Scene(this, 1280, 720);
        this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    }

}

