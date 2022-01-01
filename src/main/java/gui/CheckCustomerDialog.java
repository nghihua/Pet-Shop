package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    CheckCustomerDialog(JFrame parent) {
        super(parent, "Check Current Customers", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listCustomerModel = new DefaultListModel();
        listCustomer.setModel(listCustomerModel);
        //load initial data
        refreshCustomerList(new String[]{"Toodle-Tuh", "Doggo", "Noob", "Tagalog"});

        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int customerIndex = listCustomer.getSelectedIndex();
                //if no pet is selected
                if (customerIndex == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a customer!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    new ViewCustomerDialog(CheckCustomerDialog.this);
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
