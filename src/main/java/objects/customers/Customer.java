package objects.customers;

import database.PostgreSQLJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    int id;
    String name;
    int phone;
    double discount; //discount = -1 means guest
    public Customer(String name, int phone, double discount) throws Exception
    {
        if(discount > 1.0)
        {
            throw new Exception();
        }
        this.name = name;
        this.phone = phone;
        this.discount = discount;
    }
    public Customer(int phone)
    {
        this.phone = phone;
        String sql = String.format("SELECT * FROM customer WHERE phone ='%d';", phone);
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            while(rs.next())
            {
                this.name = rs.getString("cust_name");
                this.id = rs.getInt("cust_id");
                this.discount = rs.getDouble("discount");
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public double getDiscount(){
        return this.discount;
    }

    public void setInfo()
    {
        String sql = String.format("INSERT INTO customer (cust_name, phone, discount)" +
                "VALUES('%s', '%d', '%f');", this.name, this.phone, this.discount);
        String sql2 = String.format("INSERT INTO customer (cust_name, phone, discount)" +
                "VALUES('%s', '%d', NULL);", this.name, this.phone);

        String exec_sql = (this.discount >= 0.0) ? sql : sql2;
        PostgreSQLJDBC.updateToDatabase(exec_sql);
    }

    public void getInfo()
    {
        System.out.println(this.id);
        System.out.println(this.name);
        System.out.println(this.phone);
        System.out.println(this.discount);
    }

}
