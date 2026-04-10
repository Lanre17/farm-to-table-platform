/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.requests.ShipmentReceiptConfirmation;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Lanre
 */
class ConfirmReceiptPanel extends JPanel {
    private final ShipmentReceiptConfirmation confirmation;
    private final InventoryClerkProfile profile;
    private final JPanel cardPanel;
    private final InventoryClerkWorkArea parent;

    private JTextField fieldShelfLocation;
    private JLabel errorLabel;

    public ConfirmReceiptPanel(ShipmentReceiptConfirmation confirmation,
            InventoryClerkProfile profile,
            JPanel cardPanel,
            InventoryClerkWorkArea parent) {
        this.confirmation = confirmation;
        this.profile = profile;
        this.cardPanel = cardPanel;
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }

    private void buildUI() {

        add(UIFactory.headerSimple("Confirm Shipment Receipt"), BorderLayout.NORTH);

        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING, UIConstants.PADDING * 3,
                UIConstants.PADDING, UIConstants.PADDING * 3
        ));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(
                        UIConstants.PADDING, UIConstants.PADDING,
                        UIConstants.PADDING, UIConstants.PADDING
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        DeliveryRequest delivery = confirmation.getDeliveryRequest();

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Receipt Details"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Product", delivery.getWarehouseItem().getProductName(), row++);
        UIFactory.detailRow(card, gbc, "PO ID", String.valueOf(delivery.getPurchaseOrderId()), row++);
        UIFactory.detailRow(card, gbc, "Driver", delivery.getDriver(), row++);
        UIFactory.detailRow(card, gbc, "Status", confirmation.getStatus(), row++);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Stocking Info"), gbc);
        gbc.gridwidth = 1;

        fieldShelfLocation = UIFactory.labeledField(card, gbc, "Shelf Location *", row++);

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
        btnBar.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, UIConstants.BORDER_LIGHT
        ));

        JButton btnBack = UIFactory.secondaryButton("← Back");
        JButton btnSubmit = UIFactory.primaryButton("Confirm Receipt");

        btnBack.addActionListener(e -> goBack());
        btnSubmit.addActionListener(e -> onSubmit());

        btnBar.add(btnBack);
        btnBar.add(btnSubmit);

        add(btnBar, BorderLayout.SOUTH);
    }

    private boolean validateInput() {
        if (fieldShelfLocation.getText().trim().isEmpty()) {
            errorLabel.setText("Shelf location is required.");
            return false;
        }

        errorLabel.setText(" ");
        return true;
    }

    private void onSubmit() {

        if (!validateInput()) {
            return;
        }

        DeliveryRequest delivery = confirmation.getDeliveryRequest();

        // Update receipt status
        confirmation.confirm(profile.getPerson().getFullName());
        confirmation.stock();

        // Create inventory item from delivered goods
        ConfigureABusiness.inventoryDirectory.newItem(
                delivery.getPurchaseOrderId(),
                delivery.getWarehouseItem().getProductName(),
                delivery.getWarehouseItem().getQty(),
                fieldShelfLocation.getText().trim(),
                LocalDate.now().toString()
        );

        // Refresh clerk dashboard tables
        parent.loadReceiptTable();
        parent.loadInventoryTable();

        // Success message
        JOptionPane.showMessageDialog(
                this,
                "Receipt confirmation successful.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
        
        goBack();
    }

    private void goBack() {
        cardPanel.remove(this);
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.previous(cardPanel);
    }
}
