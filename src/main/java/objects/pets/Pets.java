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
    protected Pets(String name, int age, double price_in)
    {
        this.name = name;
        this.age = age;
        this.price_in = price_in;
    }
    public Pets(String id)
    {
        String sql = String.format("SELECT * FROM pet p JOIN species s on (p.breed = s.breed) " +
                "WHERE p.pet_id = '%s';", id);
        try{
        ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
        while(rs.next())
        {
            this.id = id;
            this.name = rs.getString("pet_name");
            this.age = rs.getInt("age");
            this.breed = rs.getString("breed");
            this.price_in = rs.getDouble("price_in");
            this.species = rs.getString("species");
        }
        PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setInfo() {
        String sql = String.format("INSERT INTO pet(pet_name, age, breed, price_in) " +
                "VALUES('%s', %d, '%s', %f);", this.name, this.age, this.breed, this.price_in);
        PostgreSQLJDBC.updateToDatabase(sql);
    }
    public void updateInfo(String name, int age, String breed, double price)
    {
        String sql = String.format("UPDATE pet SET pet_name = '%s', age = %d, breed = '%s', price_in = %f " +
                "WHERE pet_id = '%s';", name, age, breed, price, this.id);
        PostgreSQLJDBC.updateToDatabase(sql);
    }
    public void deleteInfo()
    {
        String sql = String.format("DELETE FROM pet WHERE pet_id = '%s';", this.id);
        PostgreSQLJDBC.updateToDatabase(sql);
    }
    public String getName()
    {
        return this.name;
    }
    public int getAge()
    {
        return this.age;
    }
    public String getSpecies()
    {
        return this.species;
    }
    public String getBreed()
    {
        return this.breed;
    }
    public double getPrice_in()
    {
        return this.price_in;
    }

}
