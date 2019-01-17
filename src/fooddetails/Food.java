
package fooddetails;

import javafx.beans.property.SimpleStringProperty;


public class Food {
    
    private final SimpleStringProperty name;
    private final SimpleStringProperty protein;
    private final SimpleStringProperty potassium;
    private final SimpleStringProperty id;
   
    
    public Food () {
    this.name = new SimpleStringProperty("");
    this.protein = new SimpleStringProperty("");
    this.potassium = new SimpleStringProperty("");
    this.id = new SimpleStringProperty("");
    }
    
    public Food (String name, String protein, String potassium) {
    this.name = new SimpleStringProperty(name);
    this.protein = new SimpleStringProperty(protein);
    this.potassium = new SimpleStringProperty(potassium);
    this.id = new SimpleStringProperty("");
    }
    
    public Food (Integer id, String name, String protein, String potassium) {
    this.name = new SimpleStringProperty(name);
    this.protein = new SimpleStringProperty(protein);
    this.potassium = new SimpleStringProperty(potassium);
    this.id = new SimpleStringProperty(String.valueOf(id));
    }
    
 
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
        
    public String getProtein() {
        return protein.get();
    }
    public void setProtein(String protein) {
        this.protein.set(protein);
    }

    public String getPotassium() {
        return potassium.get();
    }
    public void setPotassium(String potassium) {
        this.potassium.set(potassium);
    }
    
    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }
    
}
