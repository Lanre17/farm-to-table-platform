/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import com.mycompany.farmtotablenetwork.retail.InventoryItem;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lanre
 */

public class InventoryClerkWorkArea extends JPanel {
    private final InventoryClerkProfile profile;
    private final JPanel cardPanel;

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public InventoryClerkWorkArea(InventoryClerkProfile profile, JPanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadTable();
    }

    private void buildUI() {

        // Header (same style as others)
        add(UIFactory.header(
                "Storefront / Inventory",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        String[] columns = {"ID", "Product", "Qty", "Location", "Received Date", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = UIFactory.styledTable(tableModel);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING,
                0,
                UIConstants.PADDING
        ));

        center.add(UIFactory.tableScrollPane(inventoryTable), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Bottom bar (placeholder for next step)
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, UIConstants.BORDER_LIGHT
        ));

        JButton btnRefresh = UIFactory.secondaryButton("Refresh");
        btnRefresh.addActionListener(e -> loadTable());

        btnBar.add(btnRefresh);
        add(btnBar, BorderLayout.SOUTH);
    }

    // Load inventory into table
    public void loadTable() {
        tableModel.setRowCount(0);

        for (InventoryItem item : ConfigureABusiness.inventoryDirectory.getAllItems()) {
            tableModel.addRow(new Object[]{
                item.getInventoryId(),
                item.getProductName(),
                item.getQty(),
                item.getShelfLocation(),
                item.getReceivedDate(),
                item.getStatus()
            });
        }
    }
}
