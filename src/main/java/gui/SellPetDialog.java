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
                phones[idx] = (rs.getString("phone"));
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
    SellPetDialog(ViewPetDialog parent, String id) {
        super(parent, "Sell Pet", true);
        this.setSize(600,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //load pet
        Pets p = new Pets(id);
        //Store customer's id
        //combo box
        customerComboBoxModel = new DefaultComboBoxModel();
        customerComboBox.setModel(customerComboBoxModel);

        //load initial data
        //refreshCustomerComboBox(new String[]{"091738174", "09213810"});
        refreshCustomerComboBox(loadCustomerPhone());
        customerComboBox.setSelectedIndex(-1);

        //everytime reselect customer, this method is fired
        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get currently selected customer, load the discount value and calculate the price
                Customer c = new Customer(customerComboBox.getSelectedItem().toString());
                double discount_val = Math.max(c.getDiscount(), 0.0);
                discountValueLabel.setText(Double.toString(discount_val));
                double cost = p.getPrice_in() * 1.1 * (1 - discount_val);
                priceValueLabel.setText(Double.toString(cost));
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String customer = customerComboBox.getSelectedItem().toString();

                //insert into transaction table here
                String phone_no = customerComboBox.getSelectedItem().toString();
                double price_val = Double.parseDouble(priceValueLabel.getText());
                String sql = String.format("INSERT INTO transaction(item_id, customer_phone, cash_in) " +
                        "VALUES('%s','%s',%f);", id,phone_no,price_val);
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
