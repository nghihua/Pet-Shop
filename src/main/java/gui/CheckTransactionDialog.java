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
        this.setSize((int) (0.8 * parent.getWidth()), (int) (0.8 * parent.getHeight()));
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
        loadTableDataAll();

        this.setVisible(true);
    }

    public void loadTableDataAll() {
        //clear selection and current data from table
        transactionTable.clearSelection();
        transactionTableModel.getDataVector().removeAllElements();

        //query from database and add each row to table model here
        String query = "SELECT * FROM pet p;";
        String query1 = "SELECT * FROM supply p;";
        String query2 = "SELECT * FROM transaction p;";
        String query3 = "SELECT * FROM supply_transaction p;";
        String query4 = "SELECT balance FROM balance WHERE no_ = (SELECT max(no_) FROM balance)";
        double total = 0;
        try {
            ResultSet rs = PostgreSQLJDBC.readFromDatabase(query);
            while(rs.next()) {
                //example
                String a = rs.getString("pet_id");
                String b = "Pet";//rs.getString("Pet");
                String c = "N/A";//rs.getString("No");
                String d = "-" + rs.getString("price_in");
                transactionTableModel.addRow(new Object[]{a, b, c, d});
            }
            rs = PostgreSQLJDBC.readFromDatabase(query1);
            while(rs.next()) {
                //example
                String a = rs.getString("supply_id");
                String b = "Supply";//rs.getString("Pet");
                String c = "SELF";//rs.getString("No");
                String d = "-" + rs.getString("price_in");
                transactionTableModel.addRow(new Object[]{a, b, c, d});
                //total-= rs.getDouble("sum");
            }
            rs = PostgreSQLJDBC.readFromDatabase(query2);
            while(rs.next()) {
                //example
                String a = rs.getString("item_id");
                String b = "Pet";//rs.getString("Pet");
                String c = rs.getString("customer_phone");
                String d = "+" + rs.getString("cash_in");
                transactionTableModel.addRow(new Object[]{a, b, c, d});
                //total += rs.getDouble("sum");
            }
            rs = PostgreSQLJDBC.readFromDatabase(query3);
            while(rs.next()) {
                //example
                String a = rs.getString("item_id");
                String b = "Supply";//rs.getString("Pet");
                String c = rs.getString("customer_phone");
                String d = "+" + rs.getString("cash_in");
                transactionTableModel.addRow(new Object[]{a, b, c, d});
                //total += rs.getDouble("sum");
            }
            rs = PostgreSQLJDBC.readFromDatabase(query4);{
                while(rs.next()) {
                    total += rs.getDouble("balance");
                }
            }
            PostgreSQLJDBC.closeStatement();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        //calculate balance
        balanceLabel.setText(Double.toString(total));
        if (total >= 0) {
            //if balance is positive, set color to green
            balanceLabel.setForeground(new Color(4, 125, 10));
        }
        else {
            //if balance is negative, set color to red
            balanceLabel.setForeground(Color.RED);
        }
    }
}
