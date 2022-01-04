package gui;

import database.PostgreSQLJDBC;
import objects.customers.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        //Connect to database
        PostgreSQLJDBC.connectDatabase();

        //Disconnect database when the main frame is closed
        WindowAdapter exit_on_close = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                PostgreSQLJDBC.disconnectDatabase();
                System.exit(0);
            }
        };
        this.addWindowListener(exit_on_close);

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
        newCustomer = new JMenuItem("New Customer");
        newCustomer.addActionListener(this);
        checkCustomer = new JMenuItem("Check Current Customers");
        checkCustomer.addActionListener(this);
        customerMenu.add(newCustomer);
        customerMenu.add(checkCustomer);

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
    }

}