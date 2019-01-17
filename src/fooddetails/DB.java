
package fooddetails;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {
    
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FOODS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table foods(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(30), protein varchar(30), potassium varchar(30)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Food> getAllContacts(){
        String sql = "select * from foods";
        ArrayList<Food> foods = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            foods = new ArrayList<>();
            
            while (rs.next()){
                Food actualFood = new Food(rs.getInt("id"),rs.getString("name"),rs.getString("protein"),rs.getString("potassium"));
                foods.add(actualFood);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a food details kiolvasásakor");
            System.out.println(""+ex);
        }
      return foods;
    }
    
    public void addData(Food foods){
      try {
        String sql = "insert into foods (name, protein, potassium) values (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, foods.getName());
        preparedStatement.setString(2, foods.getProtein());
        preparedStatement.setString(3, foods.getPotassium());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a data hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateData(Food foods){
      try {
            String sql = "update foods set name = ?, protein = ? , potassium = ?, where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, foods.getName());
            preparedStatement.setString(2, foods.getProtein());
            preparedStatement.setString(3, foods.getPotassium());
            preparedStatement.setInt(5, Integer.parseInt(foods.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    

    
}

