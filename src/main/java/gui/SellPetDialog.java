package gui;

import database.PostgreSQLJDBC;
import objects.customers.Customer;
import objects.pets.Pets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class SellPetDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel customerLabel;
    private JComboBox customerComboBox;
    private JLabel discountLabel;
    private JLabel priceLabel;
    private JButton confirmButton;
    private JLabel discountValueLabel;
    private JLabel priceValueLabel;

    private DefaultComboBoxModel customerComboBoxModel;

    String[] loadCustomerPhone()
    {
        String sql = "SELECT phone FROM customer;";
        int no_of_customer = PostgreSQLJDBC.countResult(sql);
        String[] phones = new String[no_of_customer];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                phones[idx] = Integer.toString(rs.getInt("phone"));
                idx ++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return phones;
    }
    SellPetDialog(JDialog parent, String id) {
        super(parent, "Sell Pet", true);
        this.setSize(600,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //load pet
        Pets p = new Pets(id);
        //Store customer's id
        final int[] cust_id = new int[1];
        //combo box
        customerComboBoxModel = new DefaultComboBoxModel();
        customerComboBox.setModel(customerComboBoxModel);

        //load initial data
        //refreshCustomerComboBox(new String[]{"091738174", "09213810"});
        refreshCustomerComboBox(loadCustomerPhone());
        //load customer info to an object and bla bla
        //by default, the first customer in the list is selected
        //load that customer's discount value and calculate the price here
        //String customer = customerComboBox.getSelectedItem().toString();
        //discountValueLabel.setText("0.2");
        //priceValueLabel.setText("100");

        //action listeners

        //everytime reselect customer, this method is fired
        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get currently selected customer, load the discount value and calculate the price
                Customer c = new Customer(Integer.parseInt(customerComboBox.getSelectedItem().toString()), false);
                discountValueLabel.setText(Double.toString(c.getDiscount()));
                double cost = p.getPrice_in() * 1.1 * (1 - c.getDiscount());
                priceValueLabel.setText(Double.toString(cost));
                cust_id[0] = c.getId();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String customer = customerComboBox.getSelectedItem().toString();

                //insert into transaction table here
                String sql = String.format("INSERT INTO transaction VALUES('%s', '%d', '%f');", id, cust_id[0], Double.parseDouble(priceValueLabel.getText()));
                PostgreSQLJDBC.updateToDatabase(sql);
                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Sell successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);

                //close sell pet and view pet dialogs after sold
                SellPetDialog.this.dispose();
                parent.dispose();
            }
        });

        this.setVisible(true);
    }

    public void refreshCustomerComboBox(String [] customers) {
        customerComboBoxModel.removeAllElements();
        for (String c : customers) {
            customerComboBoxModel.addElement(c);
        }
    }

}
