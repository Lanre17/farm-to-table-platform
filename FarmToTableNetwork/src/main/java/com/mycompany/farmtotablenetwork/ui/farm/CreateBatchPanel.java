/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.farm;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.requests.HarvestSubmission;
import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.ui.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author p.starobinets
 */
public class CreateBatchPanel extends JPanel {
    private final HarvestSubmission    submission;
    private final JPanel               cardPanel;
    private final HarvestWorkerWorkArea parent;

    private JTextField        fieldQty;
    private JComboBox<String> comboGrade;
    private JTextField        fieldPackaging;
    private JLabel            errorLabel;

    public CreateBatchPanel(HarvestSubmission submission,
                             JPanel cardPanel, HarvestWorkerWorkArea parent) {
        this.submission = submission;
        this.cardPanel  = cardPanel;
        this.parent     = parent;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }

    private void buildUI() {
        add(UIFactory.headerSimple("Create Harvest Batch"), BorderLayout.NORTH);

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
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Crop Reference"), gbc);
        gbc.gridwidth = 1;

        // Pre-filled from the submission — read only
        UIFactory.detailRow(card, gbc, "Crop Type", submission.getCrop().getType(), row++);
        UIFactory.detailRow(card, gbc, "Submission #",
            String.valueOf(submission.getRequestId()), row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Batch Details"), gbc);
        gbc.gridwidth = 1;

        fieldQty      = UIFactory.labeledField(card, gbc, "Quantity (kg) *", row++);
        comboGrade    = UIFactory.labeledCombo(card, gbc, "Grade *",
                            new String[]{"A", "B", "C"}, row++);
        fieldPackaging = UIFactory.labeledField(card, gbc, "Packaging Type *", row++);

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

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack   = UIFactory.secondaryButton("← Back");
        JButton btnSubmit = UIFactory.primaryButton("Create & Send for Inspection");
        btnBack.addActionListener(e   -> popPanel());
        btnSubmit.addActionListener(e -> onSubmit());

        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
    }

    private boolean validateInputs() {
        try {
            float qty = Float.parseFloat(fieldQty.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("⚠ Quantity must be a positive number.");
            return false;
        }
        if (fieldPackaging.getText().trim().isEmpty()) {
            errorLabel.setText("⚠ Packaging type is required.");
            return false;
        }
        errorLabel.setText(" ");
        return true;
    }

    private void onSubmit() {
        if (!validateInputs()) return;

        float  qty   = Float.parseFloat(fieldQty.getText().trim());
        String grade = (String) comboGrade.getSelectedItem();
        String pkg   = fieldPackaging.getText().trim();

        // Decision 11: batch creation is a separate form from submission approval
        HarvestBatch batch = ConfigureABusiness.batchDirectory.newBatch(
            submission.getCrop(), qty, grade, pkg
        );

        // Trigger InspectionRequest — cross-enterprise handoff to Emmanuel's Inspector
        InspectionRequest ir = new InspectionRequest(
            batch,
            ConfigureABusiness.harvestAndPackaging,
            ConfigureABusiness.inspectionDept
        );
       ConfigureABusiness.inspectionDirectory.addInspectionRequest(ir);
        ConfigureABusiness.workRequestDirectory.addRequest(ir);

        parent.loadTable();
            
        // Show success, then reset fields for another entry
        JOptionPane.showMessageDialog(
            this,
            "Batch sent for inspection successfully.",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        //popPanel();
    }

    private void popPanel() {
        cardPanel.remove(this);
        ((CardLayout) cardPanel.getLayout()).show(cardPanel,
            cardPanel.getComponent(cardPanel.getComponentCount() - 1).getName());
    }
}
