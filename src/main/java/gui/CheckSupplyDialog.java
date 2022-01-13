package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSupplyDialog extends JDialog {

    private JPanel mainPanel;
    private JButton viewButton;
    private JList listSupply;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JSpinner minPriceSpinner;
    private JSpinner maxPriceSpinner;
    private JButton searchButton;
    private JButton resetButton;

    private DefaultListModel listSupplyModel;


    CheckSupplyDialog(JFrame parent) {
        super(parent, "Check Current Supplies", true);
        this.setSize(700,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listSupplyModel = new DefaultListModel();
        listSupply.setModel(listSupplyModel);
        //load intial data
        refreshSupplyList(loadAllSupplyId());

        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int supplyIndex = listSupply.getSelectedIndex();
                //if no pet is selected
                if (supplyIndex == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a supply!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    new ViewSupplyDialog(CheckSupplyDialog.this, listSupply.getSelectedValue().toString());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get values from components
                int minPrice = (int) minPriceSpinner.getValue();
                int maxPrice = (int) maxPriceSpinner.getValue();
                //if minPrice is larger than maxPrice, pops up error dialog
                if (minPrice>maxPrice) {
                    JOptionPane.showMessageDialog(null, "Max price must be larger than min price!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                //refresh supply list with new data based on conditions
                refreshSupplyList(loadSearchedSupplies(minPrice, maxPrice));
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDisplay();
            }
        });

        this.setVisible(true);
    }

    //refresh list
    public void refreshSupplyList(String [] supplies) {
        listSupplyModel.removeAllElements();
        for (String p : supplies) {
            listSupplyModel.addElement(p);
        }
    }

    public void resetDisplay() {
        refreshSupplyList(loadAllSupplyId());
        minPriceSpinner.setValue(0);
        maxPriceSpinner.setValue(0);
    }

    //methods for interacting with database
    public String[] loadAllSupplyId()
    {
        String query = "SELECT supply_id FROM supply WHERE supply_id NOT IN (SELECT item_id FROM supply_transaction);";
        int no_of_supply = PostgreSQLJDBC.countResult(query);
        String[] id_list = new String[no_of_supply];
        int idx = 0;
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                id_list[idx] = rs.getString("supply_id");
                idx ++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return id_list;
    }

    String[] loadSearchedSupplies(int min_price, int max_price) {
        String query = String.format("SELECT supply_id FROM supply WHERE '%d' < price_in * 1.1 AND price_in * 1.1 < '%d';", min_price, max_price);
        int no_of_supplies = PostgreSQLJDBC.countResult(query);
        String[] supply_ids = new String[no_of_supplies];
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            int idx = 0;
            while(rs.next())
            {
                supply_ids[idx] = rs.getString("supply_id");
                idx++;
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return supply_ids;
    }

}
