/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lanre
 */

public class ProcurementOfficerWorkArea extends JPanel{
    private final ProcurementOfficerProfile profile;
    private final CardSequencePanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable orderTable;
    private JButton btnNewOrder;
    private JButton btnRefresh;
    private JButton btnCancel;
    private JButton btnShowActive;
    private JButton btnShowClosed;
    
    private String currentFilter = "ACTIVE";

    private static final String[] COLUMNS = {
        "Order", "Product", "Qty", "Distributor", "Receiver", "Requested Date", "Status"
    };

    public ProcurementOfficerWorkArea(ProcurementOfficerProfile profile, CardSequencePanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadTable();
    }

    private void buildUI() {

        // Standard work area header.
        add(UIFactory.header(
                "Procurement / Purchasing",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        // Read-only purchase order table.
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = UIFactory.styledTable(tableModel);

        // Enable cancel only for submitted orders.
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                PurchaseOrder selected = getSelectedOrder();
                btnCancel.setEnabled(
                        selected != null
                        && StatusConstants.SUBMITTED.equals(selected.getStatus())
                );
            }
        });

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

        // Bottom action bar.
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, UIConstants.BORDER_LIGHT
        ));

        btnRefresh = UIFactory.secondaryButton("Refresh");
        btnRefresh.addActionListener(e -> loadTable());

        btnCancel = UIFactory.dangerButton("Cancel Order");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(e -> onCancelOrder());

        btnNewOrder = UIFactory.primaryButton("+ New Order");
        btnNewOrder.addActionListener(e -> openNewOrderPanel());
        
        btnShowActive = UIFactory.secondaryButton("Show Active");
        btnShowClosed = UIFactory.secondaryButton("Show Closed");

        btnShowActive.addActionListener(e -> {
            currentFilter = "ACTIVE";
            loadTable();
        });

        btnShowClosed.addActionListener(e -> {
            currentFilter = "CLOSED";
            loadTable();
        });

        btnBar.add(btnShowActive);
        btnBar.add(btnShowClosed);
        btnBar.add(btnRefresh);
        btnBar.add(btnCancel);
        btnBar.add(btnNewOrder);

        add(btnBar, BorderLayout.SOUTH);
    }

    // Loads all seeded and newly created purchase orders into the table.
    public void loadTable() {
        tableModel.setRowCount(0);

        for (PurchaseOrder order : ConfigureABusiness.orderDirectory.getAllOrders()) {

            String status = getDisplayStatus(order);

            boolean isClosed
                    = StatusConstants.RECEIVED.equals(status)
                    || StatusConstants.CANCELLED.equals(status)
                    || StatusConstants.ARCHIVE.equals(status);

            if ("ACTIVE".equals(currentFilter) && isClosed) {
                continue;
            }

            if ("CLOSED".equals(currentFilter) && !isClosed) {
                continue;
            }

            tableModel.addRow(new Object[]{
                order,
                order.getProductName(),
                order.getQty(),
                order.getDistributor(),
                order.getReceiverOrg().getName(),
                order.getRequestedDate(),
                status
            });
        }

        btnCancel.setEnabled(false);
    }

    // Returns the currently selected purchase order.
    private PurchaseOrder getSelectedOrder() {
        int row = orderTable.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return (PurchaseOrder) tableModel.getValueAt(row, 0);
    }
    
    private String getDisplayStatus(PurchaseOrder order) {
        String poStatus = order.getStatus();

        // Keep closed/final procurement-owned states as source of truth.
        if (StatusConstants.RECEIVED.equals(poStatus)
                || StatusConstants.CANCELLED.equals(poStatus)) {
            return poStatus;
        }

        for (DeliveryRequest dr : ConfigureABusiness.deliveryDirectory.getAllDeliveries()) {
            if (dr.getPurchaseOrderId() == order.getRequestId()) {
                if (StatusConstants.DELIVERED.equals(dr.getStatus())) {
                    return StatusConstants.DELIVERED;
                }
                if (StatusConstants.IN_TRANSIT.equals(dr.getStatus())) {
                    return StatusConstants.IN_TRANSIT;
                }
            }
        }

        return poStatus;
    }

    // Cancels only orders that are still in Submitted status.
    private void onCancelOrder() {
        PurchaseOrder selected = getSelectedOrder();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an order first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!StatusConstants.SUBMITTED.equals(selected.getStatus())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only submitted orders can be cancelled.",
                    "Cancel Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Cancel purchase order #" + selected.getRequestId() + "?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            selected.cancel();
            loadTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase order cancelled successfully.",
                    "Order Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Opens the form panel for creating a new order.
    //ps updated to use CardSequence method instead of raw card layout
    private void openNewOrderPanel() {
            cardPanel.pushPanel(new NewOrderPanel(profile, cardPanel, this));
    }
}
