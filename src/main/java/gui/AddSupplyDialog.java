package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

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
                int price = parseInt(priceTextField.getText());

                System.out.println("Insert supply: " + name + " " + price);

                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Insert successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        this.setVisible(true);
    }
}
