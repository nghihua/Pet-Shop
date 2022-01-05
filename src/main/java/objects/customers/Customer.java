package objects.customers;

import database.PostgreSQLJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    int id;
    String name;
    int phone; //phone can be null
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
        String sql = String.format("SELECT *,coalesce(discount, -1) FROM customer WHERE phone ='%d';", phone);
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            while (rs.next()) {
                this.name = rs.getString("cust_name");
                this.id = rs.getInt("cust_id");
                this.discount = rs.getDouble("coalesce");
            }
            PostgreSQLJDBC.closeStatement();
        } catch (SQLException e) {
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
    public void updateInfo(String name, int phone, double discount)
    {
        String sql = String.format("UPDATE customer SET cust_name = '%s', phone = '%d', discount =" +
                " '%f' WHERE cust_id = '%d';", name, phone, discount, this.id);
        String sql2 = String.format("UPDATE customer SET cust_name = '%s', phone = '%d', discount = NULL" +
                "  WHERE cust_id = '%d';", name, phone, this.id);
        String fsql = (discount >= 0.0) ? sql : sql2;
        PostgreSQLJDBC.updateToDatabase(fsql);
    }
    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public int getPhone()
    {return this.phone;}
    public void deleteInfo()
    {
        String sql = String.format("DELETE FROM customer WHERE cust_id = '%d';",this.id);
        PostgreSQLJDBC.updateToDatabase(sql);
    }
}
