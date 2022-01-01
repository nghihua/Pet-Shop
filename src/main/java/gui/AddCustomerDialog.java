package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.setSize(300,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String phone = phoneTextField.getText();
                float discount = Float.parseFloat(discountTextField.getText());

                System.out.println("Insert supply: " + name + " " + phone + " " + discount);

                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Insert successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        this.setVisible(true);
    }

}
