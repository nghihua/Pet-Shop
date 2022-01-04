package gui;

import database.PostgreSQLJDBC;
import objects.customers.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckCustomerDialog extends JDialog {

    private JButton viewButton;
    private JList listCustomer;
    private JLabel phoneLabel;
    private JLabel nameLabel;
    private JButton searchButton;
    private JTextField phoneTextField;
    private JTextField nameTextField;
    private JPanel mainPanel;

    private DefaultListModel listCustomerModel;


    String[] loadAllCustomer()
    {
        String sql = "SELECT cust_id FROM customer;";
        int no_of_cust = PostgreSQLJDBC.countResult(sql);
        String[] cid = new String[no_of_cust];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                cid[idx] = Integer.toString(rs.getInt("cust_id"));
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
                    new ViewCustomerDialog(CheckCustomerDialog.this, Integer.parseInt(val));
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                String phone = phoneTextField.getText();
                String name = nameTextField.getText();

                //fetch data from database and call refreshCustomerList here
                //refreshCustomerList(fetchDataBasedOnConditions(phone, name));
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
