package gui;

import database.PostgreSQLJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckTransactionDialog extends JDialog {

    private JPanel mainPanel;
    private JTable transactionTable;
    private JLabel balanceLabel;

    private DefaultTableModel transactionTableModel;

    CheckTransactionDialog(JFrame parent) {
        super(parent, "Check Current Supplies", true);
        this.setSize(700,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //table
        transactionTableModel = new DefaultTableModel(new String[]{"Item", "Category", "Customer", "Cashflow"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable.setModel(transactionTableModel);
        transactionTable.setRowHeight(transactionTable.getRowHeight()+10);
        //loadTableDataAll();

        this.setVisible(true);
    }

    public void loadTableDataAll() {
        //clear selection and current data from table
        transactionTable.clearSelection();
        transactionTableModel.getDataVector().removeAllElements();

        //query from database and add each row to table model here
        String query = "put query here";
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                //example
                String a = rs.getString("item");
                String b = rs.getString("category");
                String c = rs.getString("customer");
                String d = rs.getString("cashflow");
                transactionTableModel.addRow(new Object[]{a, b, c, d});
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        //calculate balance
        balanceLabel.setText("put value here");
        //if balance is positive, set color to green
        balanceLabel.setForeground(new Color(4, 125, 10));
        //if balance is negative, set color to red
        balanceLabel.setForeground(Color.RED);
    }
}
