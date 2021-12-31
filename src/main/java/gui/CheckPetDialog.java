package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckPetDialog extends JDialog {

    private JPanel mainPanel;
    private JList listPet;
    private JButton viewButton;
    private JButton searchButton;
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
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listPetModel = new DefaultListModel();
        listPet.setModel(listPetModel);
        //load initial data
        refreshPetList(new String[]{"Toodle-Tuh", "Doggo", "Noob", "Tagalog"});

        //combo box
        speciesComboBoxModel = new DefaultComboBoxModel();
        speciesComboBox.setModel(speciesComboBoxModel);
        refreshSpeciesComboBox(new String[]{"Cat", "Dog"});
        breedComboBoxModel = new DefaultComboBoxModel();
        breedComboBox.setModel(breedComboBoxModel);
        refreshBreedComboBox(new String[]{"Main Coon", "Pitbull"});


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
                    new ViewPetDialog(CheckPetDialog.this);
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
