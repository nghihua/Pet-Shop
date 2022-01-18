package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckPetDialog extends JDialog {

    private JPanel mainPanel;
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
    private JTable petTable;
    private JCheckBox speciesCheckBox;
    private JCheckBox breedCheckBox;
    private JCheckBox minAgeCheckBox;
    private JCheckBox minPriceCheckBox;
    private JCheckBox maxAgeCheckBox;
    private JCheckBox maxPriceCheckBox;

    private DefaultComboBoxModel breedComboBoxModel;
    private DefaultComboBoxModel speciesComboBoxModel;

    private DefaultTableModel petTableModel;

    CheckPetDialog(JFrame parent) {
        super(parent, "Check Current Pets", true);
        this.setSize((int) (0.8 * parent.getWidth()), (int) (0.8 * parent.getHeight()));
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //set up table
        petTableModel = new DefaultTableModel(new String[]{"ID", "Pet Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petTable.setModel(petTableModel);
        petTable.setRowHeight(petTable.getRowHeight()+10);
        loadTableDataAll();

        //set up species combo box
        speciesComboBoxModel = new DefaultComboBoxModel();
        speciesComboBox.setModel(speciesComboBoxModel);
        refreshSpeciesComboBox(loadAllSpecies());
            //set default selected value as blank
        speciesComboBox.setSelectedIndex(-1);

        //set up breeds combo box
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
            //make the breed combo box to refresh each time a species is chosen
        speciesComboBox.addActionListener(action ->
                refreshBreedComboBox(loadAllBreeds(speciesComboBox.getSelectedItem().toString()))
        );

        //listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = petTable.getSelectedRow();
                //if no pet is selected
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a pet!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //send the id of the pet selected to the viewpetdialog
                    Object selectedId = petTable.getValueAt(selectedRow, 0);
                    new ViewPetDialog(CheckPetDialog.this, selectedId.toString());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadTableDataSearched();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDisplay();
            }
        });

        speciesCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    speciesComboBox.setEnabled(true);
                    speciesLabel.setEnabled(true);
                }
                else {
                    speciesComboBox.setEnabled(false);
                    speciesLabel.setEnabled(false);
                }
            }
        });
        breedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    breedComboBox.setEnabled(true);
                    breedLabel.setEnabled(true);
                }
                else {
                    breedComboBox.setEnabled(false);
                    breedLabel.setEnabled(false);
                }
            }
        });
        minAgeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    minAgeSpinner.setEnabled(true);
                    minAgeLabel.setEnabled(true);
                }
                else {
                    minAgeSpinner.setEnabled(false);
                    minAgeLabel.setEnabled(false);
                }
            }
        });
        maxAgeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    maxAgeSpinner.setEnabled(true);
                    maxAgeLabel.setEnabled(true);
                }
                else {
                    maxAgeSpinner.setEnabled(false);
                    maxAgeLabel.setEnabled(false);
                }
            }
        });
        minPriceCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    minPriceSpinner.setEnabled(true);
                    minPriceLabel.setEnabled(true);
                }
                else {
                    minPriceSpinner.setEnabled(false);
                    minPriceLabel.setEnabled(false);
                }
            }
        });
        maxPriceCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    maxPriceSpinner.setEnabled(true);
                    maxPriceLabel.setEnabled(true);
                }
                else {
                    maxPriceSpinner.setEnabled(false);
                    maxPriceLabel.setEnabled(false);
                }
            }
        });

        this.setVisible(true);
    }

    public void resetDisplay() {
        loadTableDataAll();
        speciesCheckBox.setSelected(false);
        breedCheckBox.setSelected(false);
        minAgeCheckBox.setSelected(false);
        maxAgeCheckBox.setSelected(false);
        minPriceCheckBox.setSelected(false);
        maxPriceCheckBox.setSelected(false);
        minAgeSpinner.setValue(0);
        maxAgeSpinner.setValue(0);
        minPriceSpinner.setValue(0);
        maxPriceSpinner.setValue(0);
    }


    void loadTableDataAll() {
        //clear selection and current data from table
        petTable.clearSelection();
        petTableModel.getDataVector().removeAllElements();

        String query = "SELECT pet_id, pet_name FROM pet WHERE pet_id NOT IN (SELECT item_id FROM transaction);";

        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                String a = rs.getString("pet_id");
                String b = rs.getString("pet_name");
                petTableModel.addRow(new Object[]{a, b});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    void loadTableDataSearched() {
        //clear selection and current data from table
        petTable.clearSelection();
        petTableModel.getDataVector().removeAllElements();

        String species = String.valueOf(speciesComboBox.getSelectedItem());
        String breed = String.valueOf(breedComboBox.getSelectedItem());
        int min_age = (int) minAgeSpinner.getValue();
        int max_age = (int) maxAgeSpinner.getValue();
        int min_price = (int) minPriceSpinner.getValue();
        int max_price = (int) maxPriceSpinner.getValue();

        ArrayList<String> query_conditions = new ArrayList<String>();
        if(speciesCheckBox.isSelected()) {
            query_conditions.add(String.format("species = '%s'", species));
        }
        if (breedCheckBox.isSelected()) {
            query_conditions.add(String.format("pet.breed = '%s'", breed));
        }
        if (minAgeCheckBox.isSelected()) {
            query_conditions.add(String.format("'%d' < age", min_age));
        }
        if (maxAgeCheckBox.isSelected()) {
            query_conditions.add(String.format("age < '%d'", max_age));
        }
        if (minPriceCheckBox.isSelected()) {
            query_conditions.add(String.format("'%d' < price_in * 1.1", min_price));
        }
        if (maxPriceCheckBox.isSelected()) {
            query_conditions.add(String.format("price_in * 1.1 < '%d'", max_price));
        }

        String query = "SELECT pet_id, pet_name FROM pet JOIN species ON pet.breed = species.breed WHERE pet_id NOT IN (SELECT item_id FROM transaction) ";

        for (String condition : query_conditions) {
            query+= "AND " + condition;
        }

        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                String a = rs.getString("pet_id");
                String b = rs.getString("pet_name");
                petTableModel.addRow(new Object[]{a, b});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    String[] loadAllSpecies() {
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
