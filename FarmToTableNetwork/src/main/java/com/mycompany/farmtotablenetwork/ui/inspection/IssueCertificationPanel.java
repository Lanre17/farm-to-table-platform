/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.inspection;

import com.mycompany.farmtotablenetwork.personnel.profiles.CertifierProfile;
import com.mycompany.farmtotablenetwork.requests.CertificationApproval;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author emmanuelcroll
 */

// form panel that pushes on top of CertifierWorkArea when certifier clicks Approve
public class IssueCertificationPanel extends JPanel {

    private final CertificationApproval approval;
    private final CertifierProfile      profile;
    private final CardSequencePanel     cardPanel;
    private final CertifierWorkArea     parent;

    private JTextField fieldCertType;
    private JTextField fieldExpiry;
    private JLabel     errorLabel;

    public IssueCertificationPanel(CertificationApproval approval, CertifierProfile profile,
                                    CardSequencePanel cardPanel, CertifierWorkArea parent) {
        this.approval  = approval;
        this.profile   = profile;
        this.cardPanel = cardPanel;
        this.parent    = parent;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }
    
    private void buildUI() {
        // NORTH
        add(UIFactory.headerSimple("Issue Certification"), BorderLayout.NORTH);

        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING * 3,
            UIConstants.PADDING, UIConstants.PADDING * 3));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING,
                                            UIConstants.PADDING, UIConstants.PADDING)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // read-only inspection reference so certifier knows what they're approving
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Inspection Reference"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Crop",
            approval.getInspection().getBatch().getCrop().getType(), row++);
        UIFactory.detailRow(card, gbc, "Inspector",
            approval.getInspection().getInspector(), row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Certification Details"), gbc);
        gbc.gridwidth = 1;

        // certifier fills in cert type and expiry date
        fieldCertType = UIFactory.labeledField(card, gbc, "Cert Type *", row++);
        fieldExpiry   = UIFactory.labeledField(card, gbc, "Expiry Date * (YYYY-MM-DD)", row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(formOuter);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);

        // SOUTH
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack   = UIFactory.secondaryButton("← Back");
        JButton btnSubmit = UIFactory.primaryButton("Issue Certificate");
        btnBack.addActionListener(e   -> popPanel());
        btnSubmit.addActionListener(e -> onSubmit());
        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
    }
    
    private boolean validateInputs() {
        if (fieldCertType.getText().trim().isEmpty()) {
            errorLabel.setText("⚠ Cert Type is required.");
            return false;
        }
        if (!fieldExpiry.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            errorLabel.setText("⚠ Expiry must be YYYY-MM-DD.");
            return false;
        }
        errorLabel.setText(" ");
        return true;
    }

    private void onSubmit() {
        if (!validateInputs()) return;
        // approve() creates the Certification and adds it to certDirectory
        approval.approve(
            profile.getPerson().getFullName(),
            fieldCertType.getText().trim(),
            fieldExpiry.getText().trim()
        );
        parent.loadTable();
        popPanel();
    }

    private void popPanel() {
        cardPanel.popPanel(this);
    }
}