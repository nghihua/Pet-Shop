package gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
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
        setUIFont(new javax.swing.plaf.FontUIResource("JetBrains Mono",Font.PLAIN,24));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setTitle("Pet Shop");
        this.setLayout(new FlowLayout());

        initMenu();
        this.setJMenuBar(menuBar);
        menuBar.setPreferredSize(new Dimension(100,60));

        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon();

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

    //method for setting the font for the whole GUI
    void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
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