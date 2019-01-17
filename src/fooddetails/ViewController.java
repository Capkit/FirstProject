
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
    TextField inputP;
    @FXML 
    TextField inputK;

    
    DB db = new DB();
    private final ObservableList<Food> data = FXCollections.observableArrayList();
    
    //Gombok
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
    
    @FXML
    public void inputNewData(ActionEvent event) {
    Food actualFood = new Food(inputName.getText(), inputP.getText(), inputK.getText());
    data.add(actualFood);
    db.addData(actualFood);
    inputName.clear();
    inputP.clear();
    inputK.clear();
    }
    
    @FXML
    public void backToMainMenu(ActionEvent event) {
    detailPane.setVisible(false);
    basePane.setVisible(true);
    basePane.setOpacity(1);   
    }
    
    
    public void setDetailPane () {
    TableColumn nameCol = new TableColumn("Name");
    nameCol.setMinWidth(200);
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
    
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
    
    
    table.getColumns().addAll(nameCol, proteinCol, potCol);
            
    data.addAll(db.getAllContacts());
        
    table.setItems(data);

    
    
    
    }
    
    

            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setDetailPane();
    }    
    
}
