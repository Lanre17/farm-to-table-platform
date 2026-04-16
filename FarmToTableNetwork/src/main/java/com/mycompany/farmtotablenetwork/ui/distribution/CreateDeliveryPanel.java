/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.Shipment;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Hank_Local
 */
public class CreateDeliveryPanel extends JPanel { //HL: added import using AltEnter 
    private final WarehouseItem item;
    private final PurchaseOrder po;
    private final CardSequencePanel cardPanel;
    private final WarehouseManagerWorkArea parent;

    private JTextField fieldNotes;
    private JLabel errorLabel;

    public CreateDeliveryPanel(WarehouseItem item,
            PurchaseOrder po,
            CardSequencePanel cardPanel,
            WarehouseManagerWorkArea parent) {
        this.item = item;
        this.po = po;
        this.cardPanel = cardPanel;
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }

    private void buildUI() {
        add(UIFactory.headerSimple("Create Delivery Request"), BorderLayout.NORTH);

        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING * 3,
                UIConstants.PADDING,
                UIConstants.PADDING * 3
        ));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(
                        UIConstants.PADDING,
                        UIConstants.PADDING,
                        UIConstants.PADDING,
                        UIConstants.PADDING
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Read-only warehouse item reference
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Selected Warehouse Item"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Product", item.getProductName(), row++);
        UIFactory.detailRow(card, gbc, "Available Qty", String.valueOf(item.getQty()), row++);
        UIFactory.detailRow(card, gbc, "Location", item.getLocation(), row++);
        UIFactory.detailRow(card, gbc, "Cert Type", item.getCertification().getCertType(), row++);

        // Read-only purchase order reference
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Selected Purchase Order"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "PO", String.valueOf(po), row++);
        UIFactory.detailRow(card, gbc, "Requested Product", po.getProductName(), row++);
        UIFactory.detailRow(card, gbc, "Requested Qty", String.valueOf(po.getQty()), row++);
        UIFactory.detailRow(card, gbc, "Current Status", po.getStatus(), row++);

        // Optional shipment notes
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Delivery Details"), gbc);
        gbc.gridwidth = 1;

        fieldNotes = UIFactory.labeledField(card, gbc, "Tracking Notes", row++);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(formOuter);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack = UIFactory.secondaryButton("Back");
        JButton btnSubmit = UIFactory.primaryButton("Create Delivery");

        btnBack.addActionListener(e -> popPanel());
        btnSubmit.addActionListener(e -> onSubmit());

        btnBar.add(btnBack);
        btnBar.add(btnSubmit);

        add(btnBar, BorderLayout.SOUTH);
    }

    // Validate that the selected warehouse stock can fulfill the selected PO
    private boolean validateInputs() {
        if (!StatusConstants.SUBMITTED.equals(po.getStatus())) {
            errorLabel.setText("Only submitted purchase orders can be fulfilled.");
            return false;
        }

        if (!item.getProductName().equalsIgnoreCase(po.getProductName())) {
            errorLabel.setText("Selected warehouse item does not match the purchase order product.");
            return false;
        }

        if (item.getQty() < po.getQty()) {
            errorLabel.setText("Warehouse quantity is insufficient to fulfill this purchase order.");
            return false;
        }

        errorLabel.setText(" ");
        return true;
    }

    private void onSubmit() {
        if (!validateInputs()) {
            return;
        }

        // Create a delivery-sized warehouse item so Fleet sees the PO quantity, not the full warehouse stock
        WarehouseItem deliveryItem = new WarehouseItem(
                item.getCertification(),
                item.getProductName(),
                po.getQty(),
                item.getLocation()
        );

        // Reduce the original warehouse stock by the PO quantity
        item.deplete(po.getQty());

        // Delivery creation means warehouse has started fulfilling the PO
        po.fulfill();

        // Create the cross-organization delivery request
        DeliveryRequest dr = ConfigureABusiness.deliveryDirectory.newDelivery(
                deliveryItem,
                po.getRequestId(),
                ConfigureABusiness.warehouseOps,
                ConfigureABusiness.fleetMgmt
        );

        // Add the delivery request to the shared system flow
        ConfigureABusiness.workRequestDirectory.addRequest(dr);

        // Create shipment record with optional notes
        new Shipment(dr, fieldNotes.getText().trim());

        parent.loadTable();
        popPanel();
    }

    private void popPanel() {
        cardPanel.popPanel(this);
    }
}
