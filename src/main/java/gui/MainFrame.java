package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {

    JMenuBar menuBar;

    JMenu petMenu;
    JMenuItem newPet;
    JMenuItem checkPet;

    JMenu supplyMenu;
    JMenuItem newSupply;
    JMenuItem checkSupply;

    JMenu customerMenu;

    public static void main(String[] args) {
        new MainFrame();
    }

    //constructor to set properties of frame
    MainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setTitle("Pet Shop");
        this.setLayout(new FlowLayout());

        initMenu();
        this.setJMenuBar(menuBar);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //pack();
    }

    //initialize menu bar
    void initMenu() {
        menuBar = new JMenuBar();

        petMenu = new JMenu("Pet");
        //petMenu.setFont(new Font("Arial", Font.BOLD, 25));
        newPet = new JMenuItem("New Pet");
        newPet.addActionListener(this);
        checkPet = new JMenuItem("Check Current Pets");
        checkPet.addActionListener(this);
        petMenu.add(newPet);
        petMenu.add(checkPet);

        supplyMenu = new JMenu("Supply");
        newSupply = new JMenuItem("New Supply");
        newSupply.addActionListener(this);
        checkSupply = new JMenuItem("Check Current Supplies");
        checkSupply.addActionListener(this);
        supplyMenu.add(newSupply);
        supplyMenu.add(checkSupply);

        customerMenu = new JMenu("Customer");

        menuBar.add(petMenu);
        menuBar.add(supplyMenu);
        menuBar.add(customerMenu);
    }

    //initialize fonts for the whole GUI
    void initFont() {
//        UIManager.put("MenuItem.font", new Font("Times New Roman", Font.PLAIN, 20));
//        UIManager.put("Menu.font", new Font("Times New Roman", Font.PLAIN, 20));
        UIManager.getLookAndFeelDefaults()
                .put("defaultFont", new Font("Arial", Font.BOLD, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newPet) {
            JFrame addPetFrame = new AddPetFrame(this);
            addPetFrame.setAlwaysOnTop(true);
        }
        if (e.getSource() == checkPet) {
            JFrame checkPetFrame = new CheckPetFrame(this);
            checkPetFrame.setAlwaysOnTop(true);
        }
    }

}

class AddPetFrame extends JFrame {

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    AddPetFrame(JFrame parentFrame) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300,400);
        this.setTitle("Add New Pet");
        this.setLayout(gb);
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

        JLabel nameLabel = new JLabel("Name:");
        addComponent(this, nameLabel, 0, 0, 20, 30, 2, 30);

        JTextField nameTextField = new JTextField();
        addComponent(this, nameTextField, 0, 1, 2, 30, 2, 30);

        JLabel speciesLabel = new JLabel("Species:");
        addComponent(this, speciesLabel, 0, 2, 2, 30, 2, 30);

        String[] petSpecies = { "Poodles", "Main Coon", "British Short Hair", "Golden Retriever", "Siamese" };
        JComboBox speciesComboBox = new JComboBox(petSpecies);
        speciesComboBox.setSelectedIndex(4);
        addComponent(this, speciesComboBox, 0, 3, 2, 30, 2, 30);


        JLabel ageLabel = new JLabel("Age:");
        addComponent(this, ageLabel, 0, 4, 2, 30, 2, 30);
        JTextField ageTextField = new JTextField();
        addComponent(this, ageTextField, 0, 5, 2, 30, 2, 30);

        JLabel priceLabel = new JLabel("Price in:");
        addComponent(this, priceLabel, 0, 6, 2, 30, 2, 30);
        JTextField priceTextField = new JTextField();
        addComponent(this, priceTextField, 0, 7, 2, 30, 2, 30);

        JButton submitButton = new JButton("Submit");
        addComponent(this, submitButton, 0, 8, 20, 100, 20, 100);

        this.setVisible(true);
        //pack();
    }

    void addComponent(Container container, Component component, int gridx, int gridy, int top, int left, int bottom, int right) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets = new Insets(top,left,bottom,right);

        gb.setConstraints(component, gbc);
        container.add(component);
    }
}