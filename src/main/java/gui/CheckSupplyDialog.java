package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckSupplyDialog extends JDialog {

    private JPanel mainPanel;
    private JButton viewButton;
    private JButton deleteButton;
    private JList listSupply;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JSpinner minPriceSpinner;
    private JSpinner maxPriceSpinner;
    private JButton searchButton;

    private DefaultListModel listSupplyModel;


    CheckSupplyDialog(JFrame parent) {
        super(parent, "Check Current Supplies", true);
        this.setSize(600,400);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //list
        listSupplyModel = new DefaultListModel();
        listSupply.setModel(listSupplyModel);
        //load intial data
        refreshSupplyList(new String[]{"Cat Food", "Brush", "Clipper"});

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
                    new ViewSupplyDialog(CheckSupplyDialog.this);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //retrieve data from database, replace current data
                int minPrice = (int) minPriceSpinner.getValue();
                int maxPrice = (int) maxPriceSpinner.getValue();
            }
        });

        this.setVisible(true);
    }

    public void refreshSupplyList(String [] supplies) {
        listSupplyModel.removeAllElements();
        for (String p : supplies) {
            listSupplyModel.addElement(p);
        }
    }

}
