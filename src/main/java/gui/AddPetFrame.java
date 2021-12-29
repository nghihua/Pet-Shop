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
    private JLabel resultLabel;

    private DefaultComboBoxModel breedComboBoxModel;

    //either "dog" or "cat"
    private String mode;

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

        selectPetSpecies();
        this.setVisible(true);

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
        if (choice == 0 ){
            //load Dog's breeds into combo box
            String breeds[] = {"Golden Retriever", "Poodles", "Pitbull"};
            refreshBreedComboBox(breeds);
            //set mode to "dog"
            mode = "dog";
        } else{
            //load Cat's breeds into combo box
            String breeds[] = {"Main Coon", "British Short Hair", "Siamese"};
            refreshBreedComboBox(breeds);
            //set mode to "cat"
            mode = "cat";
        }
    }

}
