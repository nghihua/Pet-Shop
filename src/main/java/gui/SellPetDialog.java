package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    SellPetDialog(JDialog parent) {
        super(parent, "SellPet", true);
        this.setSize(600,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //combo box
        customerComboBoxModel = new DefaultComboBoxModel();
        customerComboBox.setModel(customerComboBoxModel);
        //load initial data
        refreshCustomerComboBox(new String[]{"091738174", "09213810"});

        //action listeners
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String customer = customerComboBox.getSelectedItem().toString();

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
