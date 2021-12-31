package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckPetDialog extends JDialog {

    private JPanel mainPanel;
    private JList listPet;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JComboBox speciesComboBox;
    private JComboBox breedComboBox;
    private JLabel speciesLabel;
    private JLabel breedLabel;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JLabel minAgeLabel;
    private JLabel maxAgeLabel;
    private JSpinner spinner3;
    private JSpinner spinner4;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;

    private DefaultListModel listPetModel;
    private DefaultComboBoxModel breedComboBoxModel;
    private DefaultComboBoxModel speciesComboBoxModel;

    //mock data
    String pets[] = {"Toodle-Tuh", "Doggo", "Noob", "Tagalog"};

    CheckPetDialog(JFrame parent) {
        super(parent, "Check Current Pets", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listPetModel = new DefaultListModel();
        listPet.setModel(listPetModel);
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
                //int minAge = parseInt(min);
                System.out.println(selectedSpecies);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int petIndex = listPet.getSelectedIndex();
                //if no pet is selected
                if (petIndex == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a pet!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
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
                        System.out.println("Delete " + petIndex);
                    }
                }
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
