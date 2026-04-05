/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lanre
 */

public class ProcurementOfficerWorkArea extends JPanel{
    private final ProcurementOfficerProfile profile;
    private final JPanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable orderTable;
    private JButton btnNewOrder;

    public ProcurementOfficerWorkArea(ProcurementOfficerProfile profile, JPanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        buildUI();
        loadTable();
    }

    private void buildUI() {

        JLabel title = new JLabel("Procurement Officer Work Area");
        title.setFont(UIConstants.FONT_HEADER_TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Order", "Product", "Qty", "Distributor", "Requested Date", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNewOrder = new JButton("New Order");
        btnNewOrder.addActionListener(e -> openNewOrderPanel());
        buttonPanel.add(btnNewOrder);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Loads all seeded and newly created purchase orders into the table
    public void loadTable() {
        tableModel.setRowCount(0);

        for (PurchaseOrder order : ConfigureABusiness.orderDirectory.getAllOrders()) {
            tableModel.addRow(new Object[]{
                order,
                order.getProductName(),
                order.getQty(),
                order.getDistributor(),
                order.getRequestedDate(),
                order.getStatus()
            });
        }
    }

    // Opens the form panel for creating a new order
    private void openNewOrderPanel() {
        JOptionPane.showMessageDialog(this, "New Order form coming next.");
    }
}
