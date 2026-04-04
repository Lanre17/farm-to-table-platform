/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.farmtotablenetwork.ui.main;

/**
 *
 * @author emmanuelcroll
 */


import com.mycompany.farmtotablenetwork.personnel.Profile;
import com.mycompany.farmtotablenetwork.personnel.UserAccount;
import com.mycompany.farmtotablenetwork.personnel.UserAccountDirectory;

// ── Import ALL profile types ─────────────────────────────────────────────────

import com.mycompany.farmtotablenetwork.personnel.profiles.*;

// ── Import ALL work area panels ───────────────────────────────────────────────
import com.mycompany.farmtotablenetwork.ui.farm.*;
import com.mycompany.farmtotablenetwork.ui.inspection.*;
import com.mycompany.farmtotablenetwork.ui.distribution.*;
import com.mycompany.farmtotablenetwork.ui.retail.*;
import com.mycompany.farmtotablenetwork.ui.admin.*;
import com.mycompany.farmtotablenetwork.ui.reports.*;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final UserAccountDirectory accountDirectory; 
    private final CardSequencePanel    cardPanel;
    
    /*final on a field means the reference can only be assigned once when the object is constructed
    accountDirectory and cardPanel are passed in through the constructor and should never be swapped out 
    mid-session. Marking them final makes that intent explicit and lets the compiler catch it if someone 
    accidentally tries to reassign them.*/

    // Login components

    private JTextField  usernameField;
    private JPasswordField passwordField;
    private JLabel      messageLabel; // make the message label reusable 

    public MainFrame(UserAccountDirectory accountDirectory) {
        this.accountDirectory = accountDirectory;
        this.cardPanel        = new CardSequencePanel();
        initComponents();
    }

    private void initComponents() {
        setTitle("Regional Farm-to-Table Food Network");
        
        //upload template for design
        setLayout(new BorderLayout());

        // ── Login panel (LEFT side of JSplitPane) ─────────────────────────
        JPanel loginPanel = buildLoginPanel();

        // ── JSplitPane ────────────────────────────────────────────────────
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, loginPanel, cardPanel
        );

        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(1);
        splitPane.setResizeWeight(0.0);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildLoginPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIConstants.BG_HEADER);
        panel.setPreferredSize(new Dimension(280, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;

        // Title
        gbc.gridy = 0;
        JLabel title = new JLabel("Farm-to-Table");
        title.setFont(UIConstants.FONT_HEADER_TITLE);
        title.setForeground(UIConstants.TEXT_ON_DARK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, gbc);
        gbc.gridy = 1;
        JLabel subtitle = new JLabel("Network Login");
        subtitle.setFont(UIConstants.FONT_HEADER_SUB);
        subtitle.setForeground(new java.awt.Color(0xAACC88));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitle, gbc);

        // Spacer
        gbc.gridy = 2; gbc.weighty = 0.3;
        panel.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        // Username
        gbc.gridy = 3;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        userLabel.setForeground(UIConstants.TEXT_ON_DARK);
        panel.add(userLabel, gbc);
        gbc.gridy = 4;
        usernameField = new JTextField();
        usernameField.setFont(UIConstants.FONT_BODY);
        usernameField.setPreferredSize(new Dimension(0, UIConstants.FIELD_HEIGHT));
        panel.add(usernameField, gbc);

        // Password
        gbc.gridy = 5;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        passLabel.setForeground(UIConstants.TEXT_ON_DARK);
        panel.add(passLabel, gbc);
        gbc.gridy = 6;
        passwordField = new JPasswordField();
        passwordField.setFont(UIConstants.FONT_BODY);
        passwordField.setPreferredSize(new Dimension(0, UIConstants.FIELD_HEIGHT));

        // Allow Enter key to submit
        passwordField.addActionListener(e -> handleLogin());
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridy = 7; gbc.insets = new Insets(16, 20, 8, 20);
        JButton loginBtn = new JButton("Log In");
        loginBtn.setFont(UIConstants.FONT_BTN);
        loginBtn.setBackground(new java.awt.Color(0x4A7C2F));
        loginBtn.setForeground(java.awt.Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(true);
        loginBtn.setPreferredSize(new Dimension(0, UIConstants.BTN_HEIGHT));
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn, gbc);
        gbc.insets = new Insets(8, 20, 8, 20);

        // Message label
        gbc.gridy = 8;
        messageLabel = new JLabel(" ");
        messageLabel.setFont(UIConstants.FONT_ERROR);
        messageLabel.setForeground(new java.awt.Color(0xFF6B6B));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(messageLabel, gbc);

        // Bottom spacer
        gbc.gridy = 9; gbc.weighty = 1;
        panel.add(Box.createVerticalGlue(), gbc);
        return panel;
    }

    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        //validation message if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter username and password.");
            return;
        }

        UserAccount account = accountDirectory.authenticate(username, password);
        //validation methods if crdentials are wrong
        if (account == null) {
            messageLabel.setText("Invalid credentials.");
            passwordField.setText("");
            return;
        }

        messageLabel.setText(" ");
        passwordField.setText("");

        //loadWorkArea(account.getProfile());

    }

    /**

     * Routes the authenticated profile to the correct work area panel.

     *

     * ── HOW TO ADD YOUR PANELS ────────────────────────────────────────────

     * Each member adds two else-if blocks here, one per role they own.
     * Pattern:
     *   } else if (profile instanceof YourProfile) {
     *       cardPanel.pushPanel(new YourWorkArea((YourProfile) profile, cardPanel));
     *   }

     * ─────────────────────────────────────────────────────────────────────

     */

    /* private void loadWorkArea(Profile profile) {
        // ── Farm (Polina) ─────────────────────────────────────────────────
        if (profile instanceof FarmerProfile) {
            cardPanel.pushPanel(new FarmerWorkArea((FarmerProfile) profile, cardPanel));
        }else if (profile instanceof HarvestWorkerProfile) {
            cardPanel.pushPanel(new HarvestWorkerWorkArea((HarvestWorkerProfile) profile, cardPanel));

        // ── Inspection (Emmanuel) ─────────────────────────────────────────

        } else if (profile instanceof InspectorProfile) {
            cardPanel.pushPanel(new InspectorWorkArea((InspectorProfile) profile, cardPanel));
        } else if (profile instanceof CertifierProfile) {
            cardPanel.pushPanel(new CertifierWorkArea((CertifierProfile) profile, cardPanel));

        // ── Distribution (Henry) ──────────────────────────────────────────

        } else if (profile instanceof WarehouseManagerProfile) {
            cardPanel.pushPanel(new WarehouseManagerWorkArea((WarehouseManagerProfile) profile, cardPanel));
        } else if (profile instanceof DeliveryDriverProfile) {
            cardPanel.pushPanel(new DeliveryDriverWorkArea((DeliveryDriverProfile) profile, cardPanel));

        // ── Retail (Lanre) ────────────────────────────────────────────────

        } else if (profile instanceof ProcurementOfficerProfile) {
            cardPanel.pushPanel(new ProcurementOfficerWorkArea((ProcurementOfficerProfile) profile, cardPanel));
        } else if (profile instanceof InventoryClerkProfile) {
            cardPanel.pushPanel(new InventoryClerkWorkArea((InventoryClerkProfile) profile, cardPanel));

        // ── Network / Shared roles ────────────────────────────────────────
        } else if (profile instanceof NetworkCoordinatorProfile) {
            cardPanel.pushPanel(new NetworkCoordinatorWorkArea((NetworkCoordinatorProfile) profile, cardPanel));
        } else if (profile instanceof QualityAnalystProfile) {
            cardPanel.pushPanel(new QualityAnalystWorkArea((QualityAnalystProfile) profile, cardPanel));

        // ── Fallback ──────────────────────────────────────────────────────
        } else {
            JLabel fallback = new JLabel("No work area for role: " + profile.getRole());
            fallback.setFont(UIConstants.FONT_BODY);
            fallback.setHorizontalAlignment(SwingConstants.CENTER);
            cardPanel.pushPanel(new JPanel() {{ add(fallback); }});
        }

    } */

}

