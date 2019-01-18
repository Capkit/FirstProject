
package fooddetails;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;



public class ViewController implements Initializable {
    // FXML Annotations
    @FXML
    Pane basePane;
    @FXML 
    TextField searchInput;
    @FXML
    Button searchButton;
    @FXML
    Pane detailPane; 
    @FXML 
    TableView table;
    @FXML
    Button tableBackButton;
    @FXML 
    TextField inputName;
    @FXML 
    TextField inputProtein;
    @FXML 
    TextField inputPot;

    //connect to the database
    DB db = new DB();
    private final ObservableList<Food> data = FXCollections.observableArrayList();
    
    //Action handler for "Search" button basePane
    @FXML
    public void searchData(ActionEvent event) {
    String input = searchInput.getText();
    String alertText = "Type the name of the food!";
    if(!input.equals("") && !input.equals(alertText)) {
    basePane.setVisible(false);
    detailPane.setVisible(true);
    searchInput.clear();
    } else {
    searchInput.setText(alertText);
    }
    }
    
    //Action handler for "Add" Button on detailPane
    @FXML
    public void inputNewData(ActionEvent event) {
    Food actualFood = new Food(inputName.getText(), inputProtein.getText(), inputPot.getText());
    data.add(actualFood);
    db.addData(actualFood);
    inputName.clear();
    inputProtein.clear();
    inputPot.clear();
    }
    
    //Action handler for "Back" button on detailPane
    @FXML
    public void backToMainMenu(ActionEvent event) {
    detailPane.setVisible(false);
    basePane.setVisible(true);
    basePane.setOpacity(1);   
    }
    
   
    public void setDetailPane () {
    //set the first column in the table    
    TableColumn nameCol = new TableColumn("Name");
    nameCol.setMinWidth(200);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
    
    //set eventhandler if the user want to edit datas in the table
    nameCol.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<Food, String>> () {
    @Override 
    public void handle (TableColumn.CellEditEvent<Food, String> t) {
    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
    actualFood.setName(t.getNewValue());
    db.updateData(actualFood);
    }
    }
    );
    
     //set second column in the table   
    TableColumn proteinCol = new TableColumn("Protein (mg)");
    nameCol.setMinWidth(300);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("protein"));   
    
    nameCol.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<Food, String>> () {
    @Override 
    public void handle (TableColumn.CellEditEvent<Food, String> t) {
    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
    actualFood.setProtein(t.getNewValue());
    db.updateData(actualFood);
    }
    }
    );
    
    //set third column in the table
    TableColumn potCol = new TableColumn("Potassium (mg)");
    nameCol.setMinWidth(300);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("potassium")); 
    
    nameCol.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<Food, String>> () {
    @Override 
    public void handle (TableColumn.CellEditEvent<Food, String> t) {
    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
    actualFood.setPotassium(t.getNewValue());
    db.updateData(actualFood);
    }
    }
    );
    
    //Add the column to the table
    table.getColumns().addAll(nameCol, proteinCol, potCol);
    //set the data with values in the database        
    data.addAll(db.getAllFood());
    //set the table with the values    
    table.setItems(data);    
    }
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setDetailPane();
    }    
    
}
