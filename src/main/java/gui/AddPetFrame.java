package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class AddPetFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JComboBox breedComboBox;
    private JTextField ageTextField;
    private JTextField priceTextField;
    private JLabel nameLabel;
    private JLabel breedLabel;
    private JLabel ageLabel;
    private JLabel priceLabel;
    private JButton submitButton;

    DefaultComboBoxModel breedComboBoxModel;

    //mock data
    String breeds[] = { "Poodles", "Main Coon", "British Short Hair", "Golden Retriever", "Siamese" };

    AddPetFrame(JFrame parentFrame) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300,400);
        this.setTitle("Add New Pet");
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //make user cannot interact with the parent frame until closing this frame
        parentFrame.setFocusableWindowState(false);
        parentFrame.setEnabled(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                parentFrame.setFocusableWindowState(true);
                parentFrame.setEnabled(true);
            }
        });

        //combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        refreshBreedComboBox();

        this.setVisible(true);
        //pack();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String breed = breedComboBox.getSelectedItem().toString();
                int age = parseInt(ageTextField.getText());
                int price = parseInt(priceTextField.getText());

                //insert into database
                System.out.println(name + breed + age + " " + price);
            }
        });
    }

    public void refreshBreedComboBox() {
        breedComboBoxModel.removeAllElements();
        for (String p : breeds) {
            breedComboBoxModel.addElement(p);
        }
    }

}
