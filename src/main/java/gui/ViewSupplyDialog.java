package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class ViewSupplyDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JButton submitButton;
    private JButton deleteButton;
    private JButton sellButton;

    ViewSupplyDialog(JDialog parent) {
        super(parent, "View Supply Information", true);
        this.setSize(300,300);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                int price = parseInt(priceTextField.getText());

                System.out.println("Update: " + name + " " + price);

                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Update successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //open sell supply window
                new SellSupplyDialog(ViewSupplyDialog.this);
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
                    //do nothing
                } else {
                    //delete it
                    System.out.println("Delete!");
                }
            }
        });

        this.setVisible(true);
    }

}
