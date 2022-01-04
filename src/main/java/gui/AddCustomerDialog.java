package gui;

import objects.customers.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AddCustomerDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JButton submitButton;
    private JLabel discountLabel;
    private JTextField discountTextField;

    AddCustomerDialog(JFrame parent) {
        super(parent, "Add New Customer", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                int phone = Integer.parseInt(phoneTextField.getText());
                float discount = (Objects.equals(discountTextField.getText(), "")) ? -1 : Float.parseFloat(discountTextField.getText());

                try {
                    Customer c = new Customer(name, phone, discount);
                    c.setInfo();
                    //System.out.println("Insert supply: " + name + " " + phone + " " + discount);
                    JOptionPane.showMessageDialog(null, "Insert successfully!", "Congrats",
                            JOptionPane.PLAIN_MESSAGE);
                    //catch error and if no error, please do this
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert failed!", "Request failed",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        this.setVisible(true);
    }

}
