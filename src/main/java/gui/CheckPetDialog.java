package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPetDialog extends JDialog {

    private JPanel mainPanel;
    private JList listPet;
    private JButton viewButton;
    private JButton searchButton;
    private JButton refreshButton;
    private JComboBox speciesComboBox;
    private JComboBox breedComboBox;
    private JLabel speciesLabel;
    private JLabel breedLabel;
    private JSpinner minAgeSpinner;
    private JSpinner maxAgeSpinner;
    private JLabel minAgeLabel;
    private JLabel maxAgeLabel;
    private JSpinner minPriceSpinner;
    private JSpinner maxPriceSpinner;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;

    private DefaultListModel listPetModel;
    private DefaultComboBoxModel breedComboBoxModel;
    private DefaultComboBoxModel speciesComboBoxModel;
    

    String[] loadAllPetId()
    {
        String sql = "SELECT pet_id FROM pet WHERE pet_id NOT IN (SELECT item_id FROM transaction);";
        int no_of_pet = PostgreSQLJDBC.countResult(sql);
        String[] id_list = new String[no_of_pet];
        int idx = 0;
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            while(rs.next())
            {
                id_list[idx] = rs.getString("pet_id");
                idx ++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return id_list;
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
    CheckPetDialog(JFrame parent) {
        super(parent, "Check Current Pets", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listPetModel = new DefaultListModel();
        listPet.setModel(listPetModel);
        //load initial data
        //refreshPetList(new String[]{"Toodle-Tuh", "Doggo", "Noob", "Tagalog"});
        //load all pet id into
        refreshPetList(loadAllPetId());
        //combo box
        speciesComboBoxModel = new DefaultComboBoxModel();
        speciesComboBox.setModel(speciesComboBoxModel);
        //refreshSpeciesComboBox(new String[]{"Cat", "Dog"});
        refreshSpeciesComboBox(loadAllSpecies());
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        //refreshBreedComboBox(new String[]{"Main Coon", "Pitbull"});
        //make the breed combo box to refresh each time a species is chosen;
        speciesComboBox.addActionListener(action ->
            refreshBreedComboBox(loadAllBreeds(speciesComboBox.getSelectedItem().toString()))
        );


        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int petIndex = listPet.getSelectedIndex();
                //if no pet is selected
                if (petIndex == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a pet!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //view pet also
                    //add the id of the pet selected to the viewpetdialog
                    System.out.println(listPet.getSelectedValue().toString());
                    new ViewPetDialog(CheckPetDialog.this, listPet.getSelectedValue().toString());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                String selectedSpecies = String.valueOf(speciesComboBox.getSelectedItem());
                String selectedBreed = String.valueOf(breedComboBox.getSelectedItem());
                int minAge = (int) minAgeSpinner.getValue();
                int maxAge = (int) maxAgeSpinner.getValue();
                int minPrice = (int) minPriceSpinner.getValue();
                int maxPrice = (int) maxPriceSpinner.getValue();
            }
        });

        this.setVisible(true);
    }

    public void refreshPetList(String [] pets) {
        listPetModel.removeAllElements();
        for (String p : pets) {
            listPetModel.addElement(p);
        }
    }

    public void refreshBreedComboBox(String [] breeds) {
        breedComboBoxModel.removeAllElements();
        for (String p : breeds) {
            breedComboBoxModel.addElement(p);
        }
    }

    public void refreshSpeciesComboBox(String [] species) {
        speciesComboBoxModel.removeAllElements();
        for (String p : species) {
            speciesComboBoxModel.addElement(p);
        }
    }

}
