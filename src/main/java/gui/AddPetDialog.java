package gui;

import database.PostgreSQLJDBC;
import objects.pets.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
                String breed = Objects.requireNonNull(breedComboBox.getSelectedItem()).toString();
                int age = Integer.parseInt(ageTextField.getText());
                double price = Double.parseDouble(priceTextField.getText());

                if (mode.equals("dog")) {
                    //insert dog here
                    // create dog object with parameters and save to database
                    Dog d = new Dog(name, age, price, breed);
                    d.setInfo();
                    System.out.println("Insert dog: " + name + breed + age + " " + price);
                }
                else if (mode.equals("cat")) {
                    //insert cat here
                    Cat c = new Cat(name, age, price, breed);
                    c.setInfo();
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


    String[] getBreed(String species)
    {
        String sql = String.format("SELECT breed FROM species WHERE species = '%s'", species);
        int no_of_species = PostgreSQLJDBC.countResult(sql);
        String[] breed = new String[no_of_species];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                breed[idx] = rs.getString("breed");
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return breed;
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
            // Select breed where species = dog
            String[] breeds = getBreed("Dog");
            refreshBreedComboBox(breeds);
            //set mode to "dog"
            mode = "dog";
        }
        else{
            //load Cat's breeds into combo box
            String[] breeds = getBreed("Cat");
            refreshBreedComboBox(breeds);
            //set mode to "cat"
            mode = "cat";
        }
    }

}
