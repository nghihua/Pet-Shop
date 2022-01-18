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
    private JButton resetButton;
    private JTextField phoneTextField;
    private JTextField nameTextField;
    private JPanel mainPanel;

    private DefaultListModel listCustomerModel;


    CheckCustomerDialog(JFrame parent) {
        super(parent, "Check Current Customers", true);
        this.setSize((int) (0.8 * parent.getWidth()), (int) (0.8 * parent.getHeight()));
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listCustomerModel = new DefaultListModel();
        listCustomer.setModel(listCustomerModel);
        //load customer id
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
                    new ViewCustomerDialog(CheckCustomerDialog.this, (val));
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
                if(Objects.equals(phone, "") && Objects.equals(name, "")) {
                    refreshCustomerList(loadAllCustomer());
                }
                else if(Objects.equals(name, "")) {
                    refreshCustomerList(loadCustomerByPhone((phone)));
                }
                else if(Objects.equals(phone, "")) {
                    refreshCustomerList(loadCustomerByName(name));
                }
                else {
                    refreshCustomerList(loadCustomerByPhoneAndName(phone, name));
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDisplay();
            }
        });
        this.setVisible(true);
    }

    public void resetDisplay() {
        refreshCustomerList(loadAllCustomer());
        nameTextField.setText("");
        phoneTextField.setText("");
    }

    String[] loadAllCustomer()
    {
        String sql = "SELECT phone FROM customer;";
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] cid = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                cid[idx] = rs.getString("phone");
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

    String[] loadCustomerByPhone(String phone)
    {
        String sql = String.format("SELECT phone FROM customer WHERE phone ~* '%s';", phone);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = rs.getString("phone");
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
        String sql = String.format("SELECT phone FROM customer WHERE cust_name ~* '%s';", name);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = rs.getString("phone");
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

    String[] loadCustomerByPhoneAndName(String phone, String name)
    {
        String sql = String.format("SELECT phone FROM customer WHERE phone = '%s' AND cust_name *~ '%s';", phone, name);
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] customers = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                customers[idx] = rs.getString("phone");
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

    public void refreshCustomerList(String [] customers) {
        listCustomerModel.removeAllElements();
        for (String c : customers) {
            listCustomerModel.addElement(c);
        }
    }

}
