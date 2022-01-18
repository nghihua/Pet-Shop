package gui;

import database.PostgreSQLJDBC;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.Timer;

public class MainFrame extends JFrame implements ActionListener {

    private JMenuBar menuBar;

    private JMenu petMenu;
    private JMenuItem newPet;
    private JMenuItem checkPet;

    private JMenu supplyMenu;
    private JMenuItem newSupply;
    private JMenuItem checkSupply;

    private JMenu customerMenu;
    private JMenuItem newCustomer;
    private JMenuItem checkCustomer;

    private JMenu transactionMenu;
    private JMenuItem checkTransaction;

    public static void main(String[] args) {
        new MainFrame();
    }

    //constructor to set properties of frame
    MainFrame() {
        setUIFont(new javax.swing.plaf.FontUIResource("JetBrains Mono",Font.PLAIN,20));
        //try {
         //   UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        //}
        //catch (Exception e) {
        //    e.printStackTrace();
        //}
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Pet Shop");
        this.setLayout(new FlowLayout());

        //Connect to database
        PostgreSQLJDBC.connectDatabase();

        //Disconnect database when the main frame is closed
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                PostgreSQLJDBC.disconnectDatabase();
                System.exit(0);
            }
        });

        //Setup Menubar
        initMenu();
        this.setJMenuBar(menuBar);
        menuBar.setPreferredSize(new Dimension(100,60));

        //Setup banner image
        JLabel imageLabel = new JLabel();
        this.getContentPane().add(BorderLayout.CENTER, imageLabel);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./img/banner.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Image dimg = img.getScaledInstance(this.getWidth(), (int) (this.getHeight() - 0.25*this.getHeight()),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        imageLabel.setIcon(imageIcon);

        BufferedImage finalImg = img;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Image dimg = finalImg.getScaledInstance(MainFrame.this.getWidth(), (int) (MainFrame.this.getHeight() - 0.25*MainFrame.this.getHeight()),
                        Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                imageLabel.setIcon(imageIcon);
            }
        });
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
        newCustomer = new JMenuItem("New Customer");
        newCustomer.addActionListener(this);
        checkCustomer = new JMenuItem("Check Current Customers");
        checkCustomer.addActionListener(this);
        customerMenu.add(newCustomer);
        customerMenu.add(checkCustomer);

        transactionMenu = new JMenu("Transaction");
        checkTransaction = new JMenuItem("Check Transactions");
        checkTransaction.addActionListener(this);
        transactionMenu.add(checkTransaction);

        menuBar.add(petMenu);
        menuBar.add(supplyMenu);
        menuBar.add(customerMenu);
        menuBar.add(transactionMenu);

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
            JDialog addPetDialog = new AddPetDialog(this);
        }
        if (e.getSource() == checkPet) {
            JDialog checkPetDialog = new CheckPetDialog(this);
        }
        if (e.getSource() == newSupply) {
            JDialog addSupplyDialog = new AddSupplyDialog(this);
        }
        if (e.getSource() == checkSupply) {
            JDialog checkSupplyDialog = new CheckSupplyDialog(this);
        }
        if (e.getSource() == newCustomer) {
            JDialog addCustomerDialog = new AddCustomerDialog(this);
        }
        if (e.getSource() == checkCustomer) {
            JDialog checkCustomerDialog = new CheckCustomerDialog(this);
        }
        if (e.getSource() == checkTransaction) {
            JDialog checkTransactionDialog = new CheckTransactionDialog(this);
        }
    }

}