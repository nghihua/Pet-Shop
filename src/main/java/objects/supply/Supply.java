package objects.supply;

import database.PostgreSQLJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Supply {
    private String id;
    private String name;
    private double price;

    //constructor for supplies not yet exist in database
    public Supply(String name, double price) {
        this.name = name;
        this.price = price;
        String query = String.format("INSERT INTO supply(supply_name, price_in) VALUES('%s', %f);", this.name, this.price);
        PostgreSQLJDBC.updateToDatabase(query);
    }

    //constructor for supplies already exists in database
    public Supply(String id) {
        String query = String.format("SELECT * FROM supply WHERE supply_id = '%s';", id);
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                this.id = id;
                this.name = rs.getString("supply_name");
                this.price = rs.getDouble("price_in");
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(String name, double price) {
        //update info onto database
        String query = String.format("UPDATE supply SET supply_name = '%s', price_in = '%f' WHERE supply_id = '%s';",
                                        name, price / 1.1, this.id);
        PostgreSQLJDBC.updateToDatabase(query);
        //update properties of object
        this.name = name;
        this.price = price;
    }

    //delete from database
    public void deleteInfo() {
        String query = String.format("DELETE FROM supply WHERE supply_id = '%s';", this.id);
        PostgreSQLJDBC.updateToDatabase(query);
    }

    public String getName()
    {
        return this.name;
    }
    public double getPrice()
    {
        return this.price;
    }

}
