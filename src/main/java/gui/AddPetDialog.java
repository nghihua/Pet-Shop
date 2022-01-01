package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class AddPetDialog extends JDialog {
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

    private DefaultComboBoxModel breedComboBoxModel;

    //either "dog" or "cat"
    private String mode;

    AddPetDialog(JFrame parent) {
        super(parent, "Add New Pet", true);
        this.setSize(300,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);

        selectPetSpecies();

        //handle submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String breed = breedComboBox.getSelectedItem().toString();
                int age = parseInt(ageTextField.getText());
                int price = parseInt(priceTextField.getText());

                if (mode.equals("dog")) {
                    //insert dog here
                    System.out.println("Insert dog: " + name + breed + age + " " + price);
                }
                else if (mode.equals("cat")) {
                    //insert cat here
                    System.out.println("Insert cat: " + name + breed + age + " " + price);
                }
                //catch error and if no error, please do this
                JOptionPane.showMessageDialog(null, "Insert successfully!", "Congrats",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        this.setVisible(true);
    }

    public void refreshBreedComboBox(String [] breeds) {
        breedComboBoxModel.removeAllElements();
        for (String p : breeds) {
            breedComboBoxModel.addElement(p);
        }
    }

    public void selectPetSpecies() {
        int choice = JOptionPane.showOptionDialog(null,
                "Which type of pet do you want to add?", //Object message,
                "Select Species", //String title
                JOptionPane.YES_NO_OPTION, //int optionType
                JOptionPane.QUESTION_MESSAGE, //int messageType
                null, //Icon icon,
                new String[]{"Dog", "Cat"}, //Object[] options,
                "Dog");//Object initialValue
        if (choice == -1) {
            //if user clicked "x", then dispose the frame entirely
            this.dispose();
        }
        else if (choice == 0 ) {
            //load Dog's breeds into combo box
            String breeds[] = {"Golden Retriever", "Poodles", "Pitbull"};
            refreshBreedComboBox(breeds);
            //set mode to "dog"
            mode = "dog";
        }
        else{
            //load Cat's breeds into combo box
            String breeds[] = {"Main Coon", "British Short Hair", "Siamese"};
            refreshBreedComboBox(breeds);
            //set mode to "cat"
            mode = "cat";
        }
    }

}
