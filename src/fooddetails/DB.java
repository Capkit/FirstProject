
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
    
    //set DB connection
    public DB() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Error occured while cretaing connection.");
            System.out.println(""+ex);
        }
        
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Error occured while creating statement.");
                System.out.println(""+ex);
            }
        }
        
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Error occured while creating DatabaseMetaData");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FOODS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table foods(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(30), protein varchar(30), potassium varchar(30)");
            }
        } catch (SQLException ex) {
            System.out.println("Error occured while creating data tables");
            System.out.println(""+ex);
        }       
    }
    
    // DB functions and methods
    public ArrayList<Food> getAllFood(){
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
            System.out.println("Error occured while reading datas from table of Foods");
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
            System.out.println("Error occured while adding new data");
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
            System.out.println("Error occured updating a food value.");
            System.out.println(""+ex);
        }
    }
    

    
}

