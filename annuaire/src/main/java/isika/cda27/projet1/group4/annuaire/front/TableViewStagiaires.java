package isika.cda27.projet1.group4.annuaire.front;

import isika.cda27.projet1.group4.annuaire.back.Stagiaire;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewStagiaires extends TableView<Stagiaire> {
	
	public ObservableList<Stagiaire> myObservableArrayList;

	public TableViewStagiaires(ObservableList<Stagiaire> myObservableArrayList) {
		super();
		this.myObservableArrayList = myObservableArrayList;
		
		// Créer les colonnes
		TableColumn<Stagiaire, String> nameColumn = new TableColumn<Stagiaire, String>(" Nom");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("name"));
		nameColumn.setPrefWidth(196);

		TableColumn<Stagiaire, String> firstNameColumn = new TableColumn<Stagiaire, String>(" Prénom");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));
		firstNameColumn.setPrefWidth(196);

		TableColumn<Stagiaire, String> postalCodeColumn = new TableColumn<Stagiaire, String>(" Département");
		postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("postalCode"));
		postalCodeColumn.setPrefWidth(196);

		TableColumn<Stagiaire, String> promoColumn = new TableColumn<Stagiaire, String>(" Promotion");
		promoColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));
		promoColumn.setPrefWidth(196);

		TableColumn<Stagiaire, String> yearColumn = new TableColumn<Stagiaire, String>(" Année");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));
		yearColumn.setPrefWidth(194);

		// Ajouter les colonnes au TableView
		this.getColumns().addAll(nameColumn, firstNameColumn, postalCodeColumn, promoColumn, yearColumn);
		
		// Lier la liste observable au TableView
        this.setItems(this.myObservableArrayList);
	}

	
	
	
//	public TableViewStagiaires(ObservableList<Stagiaire> myObservableArrayList){
//		super();
//		
//		
//		
//	}
	
}
