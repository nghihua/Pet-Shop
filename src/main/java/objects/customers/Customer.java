package objects.customers;

import database.PostgreSQLJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements CustomerInterface {
    
    private String name;
    private String phone;
    private double discount; //discount = -1 means guest
    
    public Customer(String name, String phone, double discount) throws Exception {
        if(discount > 1.0) {
            throw new Exception();
        }
        this.name = name;
        this.phone = phone;
        this.discount = discount;
    }
    public Customer(String phone)
    {
        this.phone = phone;
        String sql = String.format("SELECT *,coalesce(discount, -1) FROM customer WHERE phone ='%s';", phone);
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            while (rs.next()) {
                this.name = rs.getString("cust_name");
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
                "VALUES('%s', '%s', %f);", this.name, this.phone, this.discount);
        String sql2 = String.format("INSERT INTO customer (cust_name, phone, discount)" +
                "VALUES('%s', '%s', NULL);", this.name, this.phone);

        String exec_sql = (this.discount >= 0.0) ? sql : sql2;
        PostgreSQLJDBC.updateToDatabase(exec_sql);
    }
    public void updateInfo(String name, String new_phone, double discount)
    {
        String sql = String.format("UPDATE customer SET cust_name = '%s', phone = '%s', discount =" +
                " %f WHERE phone = '%s';", name, new_phone, discount, this.phone);
        String sql2 = String.format("UPDATE customer SET cust_name = '%s', phone = '%s', discount = NULL" +
                "  WHERE phone = '%s';", name, new_phone, this.phone);
        String fsql = (discount >= 0.0) ? sql : sql2;
        PostgreSQLJDBC.updateToDatabase(fsql);
    }
    public String getName() {
        return this.name;
    }
    public void deleteInfo()
    {
        String sql = String.format("DELETE FROM customer WHERE phone = '%s';",this.phone);
        PostgreSQLJDBC.updateToDatabase(sql);
    }
}
