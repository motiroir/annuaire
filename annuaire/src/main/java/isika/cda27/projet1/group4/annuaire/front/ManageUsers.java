package isika.cda27.projet1.group4.annuaire.front;
import isika.cda27.projet1.group4.annuaire.App;
import isika.cda27.projet1.group4.annuaire.back.Role;
import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import isika.cda27.projet1.group4.annuaire.back.User;
import isika.cda27.projet1.group4.annuaire.exceptions.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageUsers extends BorderPane{
	
	
	private Scene scene;
    private App app;
    private Stage stage;
    private Scene previousScene; 
    
    TableView<User> tableView;

    public ManageUsers(App app, Stage stage) {
        this.app = app;
        this.stage = stage;
        this.previousScene = previousScene;
        
        
        Header header = new Header(app, stage, "Gestion des utilisateurs");
        this.setTop(header);
        
        
        // Tableau des utilisateurs
        TableView<User> tableView = new TableView<>();
        app.usersDAO = FXCollections.observableArrayList(app.userManager.getUsers());
        tableView.setItems(app.usersDAO);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Colonnes du tableau
        TableColumn<User, String> usernameCol = new TableColumn<>("Nom d'utilisateur");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> roleCol = new TableColumn<>("Rôle");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(usernameCol, roleCol);
        this.setCenter(tableView);
        BorderPane.setMargin(tableView, new Insets(0, 150, 0, 150)); // Marges haut, droite, bas, gauche

        // Boutons d'action
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        Button addButton = new Button(" Ajouter ");
        Button deleteButton = new Button("Supprimer");
        Button returnButton = new Button("  Retour  ");

        HBox.setMargin(returnButton, new Insets(0, 20, 0, 0)); // Marges haut, droite, bas, gauche
        HBox.setMargin(addButton, new Insets(0, 20, 0, 20)); // Marges haut, droite, bas, gauche

        this.setBottom(buttonBox);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Ajout des boutons à gauche et à droite avec le spacer entre eux
        buttonBox.getChildren().addAll(addButton, deleteButton, spacer, returnButton);
	
	
        // Création de la scène
        this.scene = new Scene(this, 1280, 720);
        this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        
        
    
        
 // Méthode du bouton add
 		addButton.setOnAction(new EventHandler<ActionEvent>() {
 			@Override
 			public void handle(ActionEvent event) {
 				CreateUser createUser = new CreateUser (app, stage);
 				stage.setScene(createUser .getScene());
 			}
 			
 		});
 		
 		
 		
 		
 		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
 			@Override
 			public void handle(ActionEvent event) {
 				
 				
		        // Récupérer le stagiaire sélectionné
		        User selectedUser= tableView.getSelectionModel().getSelectedItem();
		        if (selectedUser != null) {
		            
		            // Créer un dialogue
		            Dialog<String> dialog = new Dialog<>();
		            dialog.setTitle("");
		            dialog.setHeaderText(null);

		            VBox vbox = new VBox(10);
		            vbox.setAlignment(Pos.CENTER);

		            Label messageLabel = new Label("Êtes-vous sûr de vouloir supprimer ce stagiaire ?");
		            messageLabel.getStyleClass().add("alert");
		            VBox.setMargin(messageLabel, new Insets(15, 15, 0, 15)); // Marges haut, droite, bas, gauche
		            
		            
		            
		            Button okButton = new Button("   OK    ");
		            okButton.setOnAction(e -> { try {
						app.userManager.deleteUser(selectedUser.getUsername()) ; dialog.setResult(" ok ");
					} catch (UserNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            app.usersDAO.setAll(app.userManager.getUsers());} 
		            ); // Ferme le dialogue en définissant un résultat
		            
		            
		            Button cancelButton = new Button("Annuler");
		            cancelButton.setOnAction(e -> dialog.setResult("annuler")); // Fermer le dialogue en définissant un résultat Cancel
		           
		            
		            HBox buttonHbox = new HBox ();
		            buttonHbox.getChildren().addAll( cancelButton,okButton);
		            HBox.setMargin(okButton, new Insets(0, 18, 10, 100)); // Marges haut, droite, bas, gauche
		            HBox.setMargin(cancelButton, new Insets(0, 30, 10, 18)); // Marges haut, droite, bas, gauche
		            vbox.getChildren().addAll(messageLabel, buttonHbox );

		            DialogPane dialogPane = dialog.getDialogPane();
		            dialogPane.setContent(vbox);
		            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		            dialogPane.getStyleClass().add("alert");

		            dialog.showAndWait();
		        }
		        
		    }
		});
 		   returnButton.setOnAction(e -> {
 			   
 			  HomePage homePage = new HomePage(app, stage); 
 	            stage.setScene(homePage.getScene()); 
         });

    }
    
}




