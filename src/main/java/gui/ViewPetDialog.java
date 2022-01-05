package gui;

import database.PostgreSQLJDBC;
import objects.pets.Pets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
    private JButton deleteButton;
    private JButton sellButton;

    private DefaultComboBoxModel breedComboBoxModel;

    ViewPetDialog(JDialog parent, String id) {
        super(parent, "View Pet Information", true);
        this.setSize(300,500);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //load initial data
        Pets p = new Pets(id);
        this.nameTextField.setText(p.getName());
        this.ageTextField.setText(Integer.toString(p.getAge()));
        this.priceTextField.setText(Double.toString(p.getPrice_in()*1.1));
        //combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        //load initial data
        String[] breeds = loadAllBreeds(p.getSpecies());
        refreshBreedComboBox(breeds);
        breedComboBox.setSelectedIndex(getBreedIndex(p.getBreed(), breeds));

        //action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String name = nameTextField.getText();
                    String breed = breedComboBox.getSelectedItem().toString();
                    int age = parseInt(ageTextField.getText());
                    double price = Double.parseDouble(priceTextField.getText());

                    System.out.println("Update: " + name + breed + age + " " + price);
                    p.updateInfo(name, age, breed, price);//notification in class Pets

                    JOptionPane.showMessageDialog(null, "Update successfully!", "Congrats",
                            JOptionPane.PLAIN_MESSAGE);
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Age and price must be numbers!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //open sell pet dialog
                new SellPetDialog(ViewPetDialog.this, id);
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
                    p.deleteInfo();
                    System.out.println("Delete!");
                }
            }
        });

        this.setVisible(true);
    }

    String[] loadAllSpecies()
    {
        String sql = "SELECT DISTINCT species FROM species;";
        int no_of_spec = PostgreSQLJDBC.countResult(sql);
        String[] specs = new String[no_of_spec];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                specs[idx] = rs.getString("species");
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return specs;
    }
    String[] loadAllBreeds(String species)
    {
        String sql = String.format("SELECT breed FROM species WHERE species = '%s';",species );
        int no_of_breed = PostgreSQLJDBC.countResult(sql);
        String[] breeds = new String[no_of_breed];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                breeds[idx] = rs.getString("breed");
                idx ++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return breeds;
    }
    int getBreedIndex(String breed, String[] breeds)
    {
        int idx = 0;
        for (int i=0; i< breeds.length; i++)
        {
            if(Objects.equals(breeds[i], breed))
            {
                idx = i;
                break;
            }
        }
        return idx;
    }

    public void refreshBreedComboBox(String [] breeds) {
        breedComboBoxModel.removeAllElements();
        for (String p : breeds) {
            breedComboBoxModel.addElement(p);
        }
    }
}
