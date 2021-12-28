package gui;

import javax.swing.*;

public class CheckPetFrame extends JFrame {

    private JPanel mainPanel;
    private JList list1;
    private JButton viewButton;
    private JButton deleteButton;
    private JTextField searchTextField;
    private JLabel searchLabel;

    CheckPetFrame(JFrame parentFrame) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300,400);
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

        this.setVisible(true);
        //pack();
    }
}
