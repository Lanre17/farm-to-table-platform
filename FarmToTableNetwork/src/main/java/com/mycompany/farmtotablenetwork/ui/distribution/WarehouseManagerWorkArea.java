/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hank_Local
 */
public class WarehouseManagerWorkArea extends JPanel { //HL: added import using AltEnter 
    private final WarehouseManagerProfile profile;
    private final CardSequencePanel cardPanel;

    // Top table for warehoused items
    private DefaultTableModel warehouseTableModel;
    private JTable warehouseTable;

    // Bottom table for retail purchase orders
    private DefaultTableModel ordersTableModel;
    private JTable ordersTable;

    // Action buttons
    private JButton btnReceive;
    private JButton btnCreateDelivery;

    // Column 0 stores the actual domain object for row retrieval
    private static final String[] WAREHOUSE_COLS = {
        "Item", "Product", "Qty", "Location", "Cert Type", "Status"
    };

    private static final String[] ORDER_COLS = {
        "Order", "Product", "Qty", "Distributor", "Requested", "Status"
    };

    public WarehouseManagerWorkArea(WarehouseManagerProfile profile, CardSequencePanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadTables();
    }

    private void buildUI() {
        add(UIFactory.header(
                "Warehouse Operations",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 0, UIConstants.PADDING));
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING
        ));

        // =========================
        // Top table: Warehouse items
        // =========================
        warehouseTableModel = new DefaultTableModel(WAREHOUSE_COLS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        warehouseTable = UIFactory.styledTable(warehouseTableModel);

        // Enable Create Delivery only when both a matching warehouse item and PO are selected
        warehouseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCreateDeliveryButtonState();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIConstants.BG_APP);

        JLabel topLabel = new JLabel(" Warehoused Items");
        topLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        topLabel.setForeground(UIConstants.TEXT_SECONDARY);

        topPanel.add(topLabel, BorderLayout.NORTH);
        topPanel.add(UIFactory.tableScrollPane(warehouseTable), BorderLayout.CENTER);
        center.add(topPanel);

        // =========================
        // Bottom table: Retail purchase orders
        // =========================
        ordersTableModel = new DefaultTableModel(ORDER_COLS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        ordersTable = UIFactory.styledTable(ordersTableModel);

        // Enable Create Delivery only when both a matching PO and warehouse item are selected
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCreateDeliveryButtonState();
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UIConstants.BG_APP);

        JLabel bottomLabel = new JLabel(" Purchase Orders from Retail");
        bottomLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        bottomLabel.setForeground(UIConstants.TEXT_SECONDARY);

        bottomPanel.add(bottomLabel, BorderLayout.NORTH);
        bottomPanel.add(UIFactory.tableScrollPane(ordersTable), BorderLayout.CENTER);
        center.add(bottomPanel);

        add(center, BorderLayout.CENTER);

        // =========================
        // Bottom action bar
        // =========================
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnReceive = UIFactory.primaryButton("+ Receive Batch");
        btnCreateDelivery = UIFactory.primaryButton("Create Delivery");
        btnCreateDelivery.setEnabled(false);

        btnReceive.addActionListener(e -> pushReceiveBatchPanel());
        btnCreateDelivery.addActionListener(e -> pushCreateDeliveryPanel());

        btnBar.add(btnCreateDelivery);
        btnBar.add(btnReceive);
        add(btnBar, BorderLayout.SOUTH);
    }

    // Reload both warehouse inventory and incoming Retail POs
    public void loadTables() {

        // Table 1: Warehouse items
        warehouseTableModel.setRowCount(0);
        for (WarehouseItem item : ConfigureABusiness.warehouseDirectory.getAllItems()) {
            warehouseTableModel.addRow(new Object[]{
                item,
                item.getProductName(),
                item.getQty(),
                item.getLocation(),
                item.getCertification().getCertType(),
                item.getStatus()
            });
        }

        // Table 2: Retail purchase orders
        ordersTableModel.setRowCount(0);
        for (PurchaseOrder po : ConfigureABusiness.orderDirectory.getAllOrders()) {
            ordersTableModel.addRow(new Object[]{
                po,
                po.getProductName(),
                po.getQty(),
                po.getDistributor(),
                po.getRequestedDate(),
                po.getStatus()
            });
        }

        btnCreateDelivery.setEnabled(false);
    }

    // Keep existing public method name for parent refresh compatibility
    public void loadTable() {
        loadTables();
    }

    // Retrieve selected warehouse item from top table
    private WarehouseItem getSelectedWarehouseItem() {
        int row = warehouseTable.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return (WarehouseItem) warehouseTableModel.getValueAt(row, 0);
    }

    // Retrieve selected purchase order from bottom table
    private PurchaseOrder getSelectedPurchaseOrder() {
        int row = ordersTable.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return (PurchaseOrder) ordersTableModel.getValueAt(row, 0);
    }

    // Only allow delivery creation when selected warehouse stock can fulfill the selected Retail PO
    private void updateCreateDeliveryButtonState() {
        WarehouseItem item = getSelectedWarehouseItem();
        PurchaseOrder po = getSelectedPurchaseOrder();

        if (item == null || po == null) {
            btnCreateDelivery.setEnabled(false);
            return;
        }

        boolean sameProduct = item.getProductName().equalsIgnoreCase(po.getProductName());
        boolean enoughQty = item.getQty() >= po.getQty();
        boolean openPo = StatusConstants.SUBMITTED.equals(po.getStatus());

        btnCreateDelivery.setEnabled(sameProduct && enoughQty && openPo);
    }

    private void pushReceiveBatchPanel() {
        cardPanel.pushPanel(new ReceiveBatchPanel(cardPanel, this));
    }

    // Pass both the selected warehouse item and selected PO into delivery creation
    private void pushCreateDeliveryPanel() {
        WarehouseItem item = getSelectedWarehouseItem();
        PurchaseOrder po = getSelectedPurchaseOrder();

        if (item == null || po == null) {
            return;
        }

        cardPanel.pushPanel(new CreateDeliveryPanel(item, po, cardPanel, this));
    }
}
