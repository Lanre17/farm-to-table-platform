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
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel; // ps added 4/14/26 to use instead of raw CardLayout
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
public class NewOrderPanel extends JPanel {
    private final ProcurementOfficerProfile profile;
    private final CardSequencePanel cardPanel;
    private final ProcurementOfficerWorkArea parent;

    private JTextField fieldProduct;
    private JTextField fieldQty;
    private JTextField fieldDistributor;
    private JTextField fieldDate;
    private JLabel errorLabel;

    public NewOrderPanel(ProcurementOfficerProfile profile, CardSequencePanel cardPanel, ProcurementOfficerWorkArea parent) {
        this.profile = profile;
        this.cardPanel = cardPanel;
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }

    private void buildUI() {

        add(UIFactory.headerSimple("New Purchase Order"), BorderLayout.NORTH);

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

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Order Details"), gbc);
        gbc.gridwidth = 1;

        fieldProduct = UIFactory.labeledField(card, gbc, "Product Name *", row++);
        fieldQty = UIFactory.labeledField(card, gbc, "Quantity *", row++);
        fieldDistributor = UIFactory.labeledField(card, gbc, "Distributor *", row++);
        fieldDate = UIFactory.labeledField(card, gbc, "Requested Date * (YYYY-MM-DD)", row++);

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        buttonPanel.setBackground(UIConstants.BG_APP);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack = UIFactory.secondaryButton("← Back");
        btnBack.addActionListener(e -> goBack());

        JButton btnSubmit = UIFactory.primaryButton("Place Order");
        btnSubmit.addActionListener(e -> submitOrder());

        buttonPanel.add(btnBack);
        buttonPanel.add(btnSubmit);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validateInput() {

        if (fieldProduct.getText().trim().isEmpty()) {
            errorLabel.setText("Product name is required.");
            return false;
        }

        try {
            int qty = Integer.parseInt(fieldQty.getText().trim());
            if (qty <= 0) {
                errorLabel.setText("Quantity must be a positive whole number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Quantity must be a positive whole number.");
            return false;
        }

        if (fieldDistributor.getText().trim().isEmpty()) {
            errorLabel.setText("Distributor is required.");
            return false;
        }

        try {
            LocalDate requestedDate = LocalDate.parse(fieldDate.getText().trim());

            if (!requestedDate.isAfter(LocalDate.now())) {
                errorLabel.setText("Requested date must be in the future.");
                return false;
            }
        } catch (DateTimeParseException ex) {
            errorLabel.setText("Requested date must be a valid date in YYYY-MM-DD format.");
            return false;
        }

        errorLabel.setText(" ");
        return true;
    }

    private void submitOrder() {

        if (!validateInput()) {
            return;
        }

        PurchaseOrder order = ConfigureABusiness.orderDirectory.newOrder(
                fieldProduct.getText().trim(),
                Integer.parseInt(fieldQty.getText().trim()),
                fieldDistributor.getText().trim(),
                fieldDate.getText().trim(),
                ConfigureABusiness.procurement,
                ConfigureABusiness.warehouseOps
        );

        // Add to shared work request directory so cross-enterprise flow can use it
        ConfigureABusiness.workRequestDirectory.addRequest(order);

        // Refresh parent table
        parent.loadTable();

        // Show success message
        JOptionPane.showMessageDialog(
                this,
                "Purchase order created successfully.",
                "Order Created",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Return to Procurement work area
        goBack();
    }

    private void goBack() {
        cardPanel.popPanel(this); //changed to use existing method ps 4/14/26
    }
}
