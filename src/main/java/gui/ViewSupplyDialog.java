package gui;

import objects.supply.Supply;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSupplyDialog extends JDialog {

    CheckSupplyDialog parent;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JButton submitButton;
    private JButton deleteButton;
    private JButton sellButton;

    ViewSupplyDialog(CheckSupplyDialog parent, String id) {
        super(parent, "View Supply Information", true);
        this.setSize((int) (0.4 * parent.getWidth()), (int) (0.8 * parent.getHeight()));
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //load initial data
        Supply supply = new Supply(id);
        this.nameTextField.setText(supply.getName());
        this.priceTextField.setText(Double.toString(supply.getPrice()));

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String name = nameTextField.getText();
                    double price = Double.parseDouble(priceTextField.getText());

                    System.out.println("Update: " + name + " " + price);
                    supply.updateInfo(name, price);//notification in class Pets

                    //catch error and if no error, please do this
                    JOptionPane.showMessageDialog(null, "Update successfully!", "Congrats",
                            JOptionPane.PLAIN_MESSAGE);
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Price must be a number!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //handle sell
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //open sell supply window
                new SellSupplyDialog(ViewSupplyDialog.this, id);
            }
        });

        //handle delete
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
                    supply.deleteInfo();
                    System.out.println("Delete!");
                    parent.resetDisplay();
                    ViewSupplyDialog.this.dispose();
                }
            }
        });

        this.setVisible(true);
    }

}
