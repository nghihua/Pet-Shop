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
    private JButton resetButton;
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

    CheckPetDialog(JFrame parent) {
        super(parent, "Check Current Pets", true);
        this.setSize(700,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //pet list
        listPetModel = new DefaultListModel();
        listPet.setModel(listPetModel);
        refreshPetList(loadAllPetId());

        //species combo box
        speciesComboBoxModel = new DefaultComboBoxModel();
        speciesComboBox.setModel(speciesComboBoxModel);
        refreshSpeciesComboBox(loadAllSpecies());
        //set default selected value as blank
        speciesComboBox.setSelectedIndex(-1);

        //breeds combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        //make the breed combo box to refresh each time a species is chosen
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
                    //add the id of the pet selected to the viewpetdialog
                    //System.out.println(listPet.getSelectedValue().toString());
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
                //if minPrice is larger than maxPrice, pops up error dialog
                if (minPrice>maxPrice) {
                    JOptionPane.showMessageDialog(null, "Max price must be larger than min price!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                //if minAge is larger than maxAge, pops up error dialog
                if (minAge>maxAge) {
                    JOptionPane.showMessageDialog(null, "Max age must be larger than min age!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                refreshPetList(loadSearchedPets(selectedBreed, minAge, maxAge,minPrice, maxPrice));
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDisplay();
            }
        });
        this.setVisible(true);
    }

    public void resetDisplay() {
        refreshPetList(loadAllPetId());
        minAgeSpinner.setValue(0);
        maxAgeSpinner.setValue(0);
        minPriceSpinner.setValue(0);
        maxPriceSpinner.setValue(0);
    }


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
    String[] loadSearchedPets(String breed, int min_age, int max_age, int min_price, int max_price)
    {
        String sql = String.format("SELECT pet_id FROM pet WHERE breed = '%s' AND '%d' < age AND age < '%d' AND " +
                "'%d' < price_in * 1.1 AND price_in * 1.1 < '%d';", breed, min_age, max_age, min_price, max_price);
        int no_of_pets = PostgreSQLJDBC.countResult(sql);
        String[] pet_ids = new String[no_of_pets];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(sql);
            int idx = 0;
            while(rs.next())
            {
                pet_ids[idx] = rs.getString("pet_id");
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return pet_ids;
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
