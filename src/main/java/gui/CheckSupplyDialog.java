package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JTable supplyTable;
    private JCheckBox minPriceCheckBox;
    private JCheckBox maxPriceCheckBox;

    private DefaultTableModel supplyTableModel;

    CheckSupplyDialog(JFrame parent) {
        super(parent, "Check Current Supplies", true);
        this.setSize((int) (0.8 * parent.getWidth()), (int) (0.8 * parent.getHeight()));
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //table
        supplyTableModel = new DefaultTableModel(new String[]{"ID", "Supply Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                return columnClass[columnIndex];
//            }
        };
        supplyTable.setModel(supplyTableModel);
        supplyTable.setRowHeight(supplyTable.getRowHeight()+10);
        loadTableDataAll();

        //listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = supplyTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "You haven't selected a supply!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Object selectedId = supplyTable.getValueAt(selectedRow, 0);
                    new ViewSupplyDialog(CheckSupplyDialog.this, selectedId.toString());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadTableDataSearched();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDisplay();
            }
        });

        minPriceCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    minPriceSpinner.setEnabled(true);
                    minPriceLabel.setEnabled(true);
                }
                else {
                    minPriceSpinner.setEnabled(false);
                    minPriceLabel.setEnabled(false);
                }
            }
        });

        maxPriceCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1) {
                    maxPriceSpinner.setEnabled(true);
                    maxPriceLabel.setEnabled(true);
                }
                else {
                    maxPriceSpinner.setEnabled(false);
                    maxPriceLabel.setEnabled(false);
                }
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
        //clear selection and current data from table
        supplyTable.clearSelection();
        supplyTableModel.getDataVector().removeAllElements();

        String query = "SELECT supply_id, supply_name FROM supply WHERE supply_id NOT IN (SELECT item_id FROM supply_transaction);";
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                String a = rs.getString("supply_id");
                String b = rs.getString("supply_name");
                supplyTableModel.addRow(new Object[]{a, b});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    void loadTableDataSearched() {
        //clear selection and current data from table
        supplyTable.clearSelection();
        supplyTableModel.getDataVector().removeAllElements();

        //get values from components
        int min_price = (int) minPriceSpinner.getValue();
        int max_price = (int) maxPriceSpinner.getValue();

        String query_conditions =
                (minPriceCheckBox.isSelected())?
                        ((maxPriceCheckBox.isSelected())?
                                String.format("AND %d < price_in * 1.1 AND price_in * 1.1 < %d;", min_price, max_price)
                                : String.format("AND %d < price_in * 1.1;", min_price))
                        : (maxPriceCheckBox.isSelected())?
                            String.format("WHERE price_in * 1.1 < %d;", max_price)
                            : ";";

        String query = "SELECT supply_id, supply_name FROM supply WHERE supply_id NOT IN (SELECT item_id FROM supply_transaction)  " + query_conditions;

        try{
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            int idx = 0;
            if (rs == null)
            {
                //do nothing;
            }
            else {
                while (rs.next()) {
                    String a = rs.getString("supply_id");
                    String b = rs.getString("supply_name");
                    supplyTableModel.addRow(new Object[]{a, b});
                }
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
