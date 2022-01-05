package gui;

import objects.supply.Supply;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSupplyDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JButton submitButton;

    AddSupplyDialog(JFrame parent) {
        super(parent, "Add New Supply", true);
        this.setSize(300,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                double price;
                try {
                    price = Double.parseDouble(priceTextField.getText());
                    Supply supply = new Supply(name, price);
                    JOptionPane.showMessageDialog(null, "Insert successfully!", "Congrats",
                            JOptionPane.PLAIN_MESSAGE);
                    System.out.println("Insert supply: " + name + " " + price);
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Price must be a number!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unknown error, please check stack trace.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setVisible(true);
    }
}
