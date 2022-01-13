package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSupplyDialog extends JDialog {

    private JPanel mainPanel;
    private JButton viewButton;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JSpinner minPriceSpinner;
    private JSpinner maxPriceSpinner;
    private JButton searchButton;
    private JButton resetButton;
    private JTable tableSupply;

    private DefaultTableModel tableSupplyModel;

    CheckSupplyDialog(JFrame parent) {
        super(parent, "Check Current Supplies", true);
        this.setSize(700,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //table
        tableSupplyModel = new DefaultTableModel(new String[]{"ID", "Supply Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                return columnClass[columnIndex];
//            }
        };
        tableSupply.setModel(tableSupplyModel);
        tableSupply.setRowHeight(tableSupply.getRowHeight()+10);
        loadTableDataAll();

        //action listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = tableSupply.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a supply!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Object selectedId = tableSupply.getValueAt(selectedRow, 0);
                    new ViewSupplyDialog(CheckSupplyDialog.this, selectedId.toString());
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
                loadTableDataSearched(minPrice, maxPrice);
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

    public void resetDisplay() {
        loadTableDataAll();
        minPriceSpinner.setValue(0);
        maxPriceSpinner.setValue(0);
    }

    //methods for interacting with database
    public void loadTableDataAll() {
        String query = "SELECT supply_id, supply_name FROM supply WHERE supply_id NOT IN (SELECT item_id FROM supply_transaction);";
        int no_of_supply = PostgreSQLJDBC.countResult(query);
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                String a = rs.getString("supply_id");
                String b = rs.getString("supply_name");
                tableSupplyModel.addRow(new Object[]{a, b});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    void loadTableDataSearched(int min_price, int max_price) {
        String query = String.format("SELECT supply_id, supply_name FROM supply WHERE '%d' < price_in * 1.1 AND price_in * 1.1 < '%d';", min_price, max_price);
        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            int idx = 0;
            while(rs.next()) {
                String a = rs.getString("supply_id");
                String b = rs.getString("supply_name");
                tableSupplyModel.addRow(new Object[]{a, b});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
