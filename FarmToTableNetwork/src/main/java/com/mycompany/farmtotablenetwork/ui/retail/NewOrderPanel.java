/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Lanre
 */
public class NewOrderPanel extends JPanel {
    private final ProcurementOfficerProfile profile;
    private final JPanel cardPanel;
    private final ProcurementOfficerWorkArea parent;

    private JTextField fieldProduct;
    private JTextField fieldQty;
    private JTextField fieldDistributor;
    private JTextField fieldDate;
    private JLabel errorLabel;

    public NewOrderPanel(ProcurementOfficerProfile profile, JPanel cardPanel, ProcurementOfficerWorkArea parent) {
        this.profile = profile;
        this.cardPanel = cardPanel;
        this.parent = parent;

        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {

        JLabel title = new JLabel("New Purchase Order");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Product Name:"), gbc);

        fieldProduct = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fieldProduct, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Quantity:"), gbc);

        fieldQty = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fieldQty, gbc);

        // Distributor
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Distributor:"), gbc);

        fieldDistributor = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fieldDistributor, gbc);

        // Requested Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Requested Date (YYYY-MM-DD):"), gbc);

        fieldDate = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fieldDate, gbc);

        // Error Label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(errorLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> goBack());

        JButton btnSubmit = new JButton("Place Order");
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

        if (!fieldDate.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            errorLabel.setText("Requested date must be in YYYY-MM-DD format.");
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

        // Return to Procurement work area
        goBack();
    }

    private void goBack() {
        cardPanel.remove(this);
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.previous(cardPanel);
    }
}
