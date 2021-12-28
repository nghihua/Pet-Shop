package gui;

import javax.swing.*;

public class CheckPetFrame extends JFrame {

    private JPanel mainPanel;
    private JList listPet;
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JButton viewButton;
    private JButton deleteButton;

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
        //pack();
    }

    public void refreshPetList() {
        listPetModel.removeAllElements();
        for (String p : pets) {
            listPetModel.addElement(p);
        }
    }

}
