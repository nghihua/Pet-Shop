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
        this.setSize((int) (0.5 * parent.getWidth()), (int) (0.6 * parent.getHeight()));
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String phone = phoneTextField.getText();
                double discount = (Objects.equals(discountTextField.getText(), "")) ? -1 : Float.parseFloat(discountTextField.getText());

                if (discount > 0.305) {
                    JOptionPane.showMessageDialog(null, "Discount cannot be larger than 30%", "Invalid Discount",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
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
            }
        });

        this.setVisible(true);
    }

}
