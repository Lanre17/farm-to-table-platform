/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
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
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadTable();
    }

    private void buildUI() {

        add(UIFactory.header(
                "Procurement / Purchasing",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        String[] columns = {"Order", "Product", "Qty", "Distributor", "Requested Date", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = UIFactory.styledTable(tableModel);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING,
                0,
                UIConstants.PADDING
        ));

        center.add(UIFactory.tableScrollPane(orderTable), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, UIConstants.BORDER_LIGHT
        ));

        btnNewOrder = UIFactory.primaryButton("+ New Order");
        btnNewOrder.addActionListener(e -> openNewOrderPanel());
        btnBar.add(btnNewOrder);

        add(btnBar, BorderLayout.SOUTH);
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
        NewOrderPanel panel = new NewOrderPanel(profile, cardPanel, this);
        cardPanel.add(panel, "NewOrderPanel");
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.next(cardPanel);
    }
}
