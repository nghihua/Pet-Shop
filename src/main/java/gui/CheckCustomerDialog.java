package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckCustomerDialog extends JDialog {

    private JButton viewButton;
    private JList listPet;
    private JLabel phoneLabel;
    private JLabel nameLabel;
    private JButton searchButton;
    private JTextField phoneTextField;
    private JTextField nameTextField;
    private JPanel mainPanel;

    CheckCustomerDialog(JFrame parent) {
        super(parent, "Check Current Customers", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                int petIndex = listSupply.getSelectedIndex();
//                //if no pet is selected
//                if (petIndex == -1) {
//                    JOptionPane.showMessageDialog(null, "You haven't selected a pet!", "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//                else {
//                    new ViewPetDialog(CheckSupplyDialog.this);
//                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                String phone = phoneTextField.getText();
                String name = nameTextField.getText();
            }
        });

        this.setVisible(true);
    }

}
