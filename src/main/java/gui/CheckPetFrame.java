package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckPetFrame extends JFrame {

    private JPanel mainPanel;
    private JList listPet;
    private JTextField searchTextField;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton searchButton;

    private DefaultListModel listPetModel;

    //mock data
    String pets[] = {"Toodle-Tuh", "Doggo", "Noob", "Tagalog"};

    CheckPetFrame(JFrame parentFrame) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,400);
        this.setTitle("Check Current Pets");
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

        //list
        listPetModel = new DefaultListModel();
        listPet.setModel(listPetModel);
        refreshPetList();

        this.setVisible(true);


        //action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                refreshPetList();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int petIndex = listPet.getSelectedIndex();
                if (petIndex == -1) {
                    JOptionPane.showMessageDialog(new JFrame(), "You haven't selected a pet!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int choice = JOptionPane.showOptionDialog(null,
                            "Are you sure you want to delete?", //Object message,
                            "Warning", //String title
                            JOptionPane.YES_NO_OPTION, //int optionType
                            JOptionPane.QUESTION_MESSAGE, //int messageType
                            null, //Icon icon,
                            new String[]{"Cancel", "OK"}, //Object[] options,
                            "Cancel");//Object initialValue
                    if (choice == 0 ){
                        //do nothing
                    } else{
                        //delete it
                        System.out.println("Delete " + petIndex);
                    }
                }
            }
        });
    }

    public void refreshPetList() {
        listPetModel.removeAllElements();
        for (String p : pets) {
            listPetModel.addElement(p);
        }
    }

}
