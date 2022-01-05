package gui;

import objects.customers.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class ViewCustomerDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel discountLabel;
    private JTextField discountTextField;
    private JButton submitButton;
    private JButton deleteButton;

    private void disposeDialog() {
        this.dispose();
    }
    ViewCustomerDialog(JDialog parent, int id) {
        super(parent, "View Customer Information", true);
        this.setSize(300,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //set init info
        Customer c = new Customer(id, true);
        nameTextField.setText(c.getName());
        phoneTextField.setText(Integer.toString(c.getPhone()));
        discountTextField.setText((c.getDiscount() >= 0.0 )? Double.toString(c.getDiscount()) : "");
        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                int phone = Integer.parseInt(phoneTextField.getText());
                double discount = (Objects.equals(discountTextField.getText(), "") ? -1 :Double.parseDouble(discountTextField.getText()));

                c.updateInfo(name, phone, discount);
                //System.out.println("Update: " + name + " " + phone + " " + discount);

                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Update successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //warning before delete
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to delete?", //Object message,
                        "Warning", //String title
                        JOptionPane.OK_CANCEL_OPTION, //int optionType
                        JOptionPane.QUESTION_MESSAGE, //int messageType
                        null, //Icon icon,
                        new String[]{"Cancel", "OK"}, //Object[] options,
                        "Cancel");//Object initialValue
                if (choice == 0 ){
                    disposeDialog();
                    //do nothing
                } else {
                    //delete it
                    c.deleteInfo();
                    disposeDialog();
                    System.out.println("Delete!");
                }
            }
        });
        this.setVisible(true);
    }

}
