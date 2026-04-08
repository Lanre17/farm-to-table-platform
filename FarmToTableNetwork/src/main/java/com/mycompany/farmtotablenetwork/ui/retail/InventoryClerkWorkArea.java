/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.requests.ShipmentReceiptConfirmation;
import com.mycompany.farmtotablenetwork.requests.WorkRequest;
import com.mycompany.farmtotablenetwork.retail.InventoryItem;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    // Table for incoming shipment receipts awaiting clerk confirmation
    private JTable receiptTable;
    private DefaultTableModel receiptTableModel;

    // Table for existing stocked inventory
    private JTable inventoryTable;
    private DefaultTableModel inventoryTableModel;

    public InventoryClerkWorkArea(InventoryClerkProfile profile, JPanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadReceiptTable();
        loadInventoryTable();
    }

    private void buildUI() {

        add(UIFactory.header(
                "Storefront / Inventory",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridLayout(2, 1, 0, UIConstants.PADDING));
        centerWrapper.setBackground(UIConstants.BG_APP);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING,
                0,
                UIConstants.PADDING
        ));

        // =========================
        // Receipt Confirmations
        // =========================
        JPanel receiptPanel = new JPanel(new BorderLayout(0, 8));
        receiptPanel.setBackground(UIConstants.BG_APP);

        JLabel receiptLabel = new JLabel("Pending Shipment Receipt Confirmations");
        receiptLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        receiptLabel.setForeground(UIConstants.TEXT_SECONDARY);

        String[] receiptColumns = {
            "Receipt", "Product", "PO ID", "Driver", "Sender", "Status"
        };

        receiptTableModel = new DefaultTableModel(receiptColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        receiptTable = UIFactory.styledTable(receiptTableModel);

        receiptPanel.add(receiptLabel, BorderLayout.NORTH);
        receiptPanel.add(UIFactory.tableScrollPane(receiptTable), BorderLayout.CENTER);

        // =========================
        // Inventory Items
        // =========================
        JPanel inventoryPanel = new JPanel(new BorderLayout(0, 8));
        inventoryPanel.setBackground(UIConstants.BG_APP);

        JLabel inventoryLabel = new JLabel("Current Inventory");
        inventoryLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        inventoryLabel.setForeground(UIConstants.TEXT_SECONDARY);

        String[] inventoryColumns = {
            "ID", "Product", "Qty", "Location", "Received Date", "Status"
        };

        inventoryTableModel = new DefaultTableModel(inventoryColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = UIFactory.styledTable(inventoryTableModel);

        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);
        inventoryPanel.add(UIFactory.tableScrollPane(inventoryTable), BorderLayout.CENTER);

        centerWrapper.add(receiptPanel);
        centerWrapper.add(inventoryPanel);

        add(centerWrapper, BorderLayout.CENTER);

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, UIConstants.BORDER_LIGHT
        ));

        // 🔹 NEW BUTTON
        JButton btnConfirm = UIFactory.primaryButton("Confirm Receipt");

        // 🔹 EXISTING BUTTON
        JButton btnRefresh = UIFactory.secondaryButton("Refresh");

        // 🔹 ACTION (we will implement panel next step)
        btnConfirm.addActionListener(e -> openConfirmReceiptPanel());

        // 🔹 EXISTING ACTION
        btnRefresh.addActionListener(e -> {
            loadReceiptTable();
            loadInventoryTable();
        });

        // 🔹 ORDER MATTERS (Confirm first, then Refresh)
        btnBar.add(btnConfirm);
        btnBar.add(btnRefresh);

        add(btnBar, BorderLayout.SOUTH);
    }

    // Load pending shipment receipts created after delivery and routed to storefront inventory
    public void loadReceiptTable() {
        receiptTableModel.setRowCount(0);

        // Pull receipt confirmations from the shared work request directory
        for (WorkRequest request : ConfigureABusiness.workRequestDirectory.getAllRequests()) {

            // Only process shipment receipt confirmations
            if (request instanceof ShipmentReceiptConfirmation) {
                ShipmentReceiptConfirmation confirmation = (ShipmentReceiptConfirmation) request;

                // Show only receipts routed to storefront inventory and not yet stocked
                if (confirmation.getReceiverOrg() == ConfigureABusiness.storefrontInventory
                        && !StatusConstants.STOCKED.equals(confirmation.getStatus())) {

                    DeliveryRequest delivery = confirmation.getDeliveryRequest();

                    receiptTableModel.addRow(new Object[]{
                        confirmation,
                        delivery.getWarehouseItem().getProductName(),
                        delivery.getPurchaseOrderId(),
                        delivery.getDriver(),
                        confirmation.getSenderOrg(),
                        confirmation.getStatus()
                    });
                }
            }
        }
    }

    // Existing stocked inventory displayed for clerk reference
    public void loadInventoryTable() {
        inventoryTableModel.setRowCount(0);

        for (InventoryItem item : ConfigureABusiness.inventoryDirectory.getAllItems()) {
            inventoryTableModel.addRow(new Object[]{
                item.getInventoryId(),
                item.getProductName(),
                item.getQty(),
                item.getShelfLocation(),
                item.getReceivedDate(),
                item.getStatus()
            });
        }
    }

    // Opens panel to allow Inventory Clerk to confirm selected shipment receipt
    private void openConfirmReceiptPanel() {
        int selectedRow = receiptTable.getSelectedRow();

        // Validate selection
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a receipt to confirm.");
            return;
        }

        // Get selected receipt
        ShipmentReceiptConfirmation selectedReceipt
                = (ShipmentReceiptConfirmation) receiptTableModel.getValueAt(selectedRow, 0);

        // Open next panel
        ConfirmReceiptPanel panel = new ConfirmReceiptPanel(
                selectedReceipt,
                profile,
                cardPanel,
                this
        );

        cardPanel.add(panel);
        ((CardLayout) cardPanel.getLayout()).next(cardPanel);
    }
}
