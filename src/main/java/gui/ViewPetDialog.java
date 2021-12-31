package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class ViewPetDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel breedLabel;
    private JComboBox breedComboBox;
    private JLabel ageLabel;
    private JTextField ageTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JButton submitButton;

    private DefaultComboBoxModel breedComboBoxModel;

    //mock data
    String breeds[] = {"Main Coon", "Jinx"};

    ViewPetDialog(JDialog parent) {
        super(parent, "View Pet Information", true);
        this.setSize(300,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        refreshBreedComboBox(breeds);

        this.setVisible(true);

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String breed = breedComboBox.getSelectedItem().toString();
                int age = parseInt(ageTextField.getText());
                int price = parseInt(priceTextField.getText());

                System.out.println("Update: " + name + breed + age + " " + price);

                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Update successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    public void refreshBreedComboBox(String [] breeds) {
        breedComboBoxModel.removeAllElements();
        for (String p : breeds) {
            breedComboBoxModel.addElement(p);
        }
    }
}
