package objects.pets;

import database.PostgreSQLJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pets {
    protected String id;
    protected String name;
    protected int age;
    protected String species;
    protected String breed;
    protected double price_in;
    Pets(String name, int age, double price_in)
    {
        this.name = name;
        this.age = age;
        this.price_in = price_in;
    }
    Pets(String id)
    {
        String sql = String.format("SELECT * FROM pet WHERE pet_id = '%s';", id);
        try{
        ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
        while(rs.next())
        {
            this.id = id;
            this.name = rs.getString("pet_name");
            this.age = rs.getInt("age");
            this.species = rs.getString("breed");
            this.price_in = rs.getDouble("price_in");
        }}
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setInfo() {
        String sql = String.format("INSERT INTO pet(pet_name, age, breed, price_in) " +
                "VALUES('%s', %d, '%s', %f);", this.name, this.age, this.breed, this.price_in);
        PostgreSQLJDBC.insertToDatabase(sql);
    }
}