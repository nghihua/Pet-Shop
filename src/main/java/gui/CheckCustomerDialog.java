package gui;

import database.PostgreSQLJDBC;
import objects.customers.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class CheckCustomerDialog extends JDialog {

    private JButton viewButton;
    private JList listCustomer;
    private JLabel phoneLabel;
    private JLabel nameLabel;
    private JButton searchButton;
    private JButton refreshButton;
    private JTextField phoneTextField;
    private JTextField nameTextField;
    private JPanel mainPanel;

    private DefaultListModel listCustomerModel;


    String[] loadAllCustomer()
    {
        String sql = "SELECT cust_id, cust_name FROM customer;";
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] cid = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                cid[idx] = Integer.toString(rs.getInt("cust_id")) +":" +
                        rs.getString("cust_name");
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return cid;
    }

    String[] loadCustomerByPhone(int phone)
    {
        String sql = String.format("SELECT cust_id, cust_name FROM customer WHERE phone = %d;", phone);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = String.format("%d:%s", rs.getInt("cust_id"), rs.getString("cust_name"));
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    String[] loadCustomerByName(String name)
    {
        String sql = String.format("SELECT cust_id, cust_name FROM customer WHERE cust_name = '%s';", name);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = String.format("%d:%s", rs.getInt("cust_id"), rs.getString("cust_name"));
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    String[] loadCustomerByPhoneAndName(int phone, String name)
    {
        String sql = String.format("SELECT cust_id, cust_name FROM customer WHERE phone = %d AND cust_name = '%s';", phone, name);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = String.format("%d:%s", rs.getInt("cust_id"), rs.getString("cust_name"));
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return customers;

    }

    CheckCustomerDialog(JFrame parent) {
        super(parent, "Check Current Customers", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listCustomerModel = new DefaultListModel();
        listCustomer.setModel(listCustomerModel);
        //load initial data
        //load customer id
        //refreshCustomerList(new String[]{"Toodle-Tuh", "Doggo", "Noob", "Tagalog"});
        refreshCustomerList(loadAllCustomer());

        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int customerIndex = listCustomer.getSelectedIndex();
                //if no customer is selected
                if (customerIndex == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a customer!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String val = listCustomer.getSelectedValue().toString();
                    String[] v = val.split(":");
                    new ViewCustomerDialog(CheckCustomerDialog.this, Integer.parseInt(v[0]));
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                //search by name or by phone
                String phone = phoneTextField.getText();
                String name = nameTextField.getText();
                if(Objects.equals(phone, "") && Objects.equals(name, ""))
                {
                    refreshCustomerList(loadAllCustomer());
                }
                else if(Objects.equals(name, ""))
                {
                    refreshCustomerList(loadCustomerByPhone(Integer.parseInt(phone)));
                }
                else if(Objects.equals(phone, ""))
                {
                    refreshCustomerList(loadCustomerByName(name));
                }
                else
                {
                    refreshCustomerList(loadCustomerByPhoneAndName(Integer.parseInt(phone), name));
                }
                //fetch data from database and call refreshCustomerList here
                //refreshCustomerList(fetchDataBasedOnConditions(phone, name));
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCustomerList(loadAllCustomer());
            }
        });
        this.setVisible(true);
    }

    public void refreshCustomerList(String [] customers) {
        listCustomerModel.removeAllElements();
        for (String c : customers) {
            listCustomerModel.addElement(c);
        }
    }

}
