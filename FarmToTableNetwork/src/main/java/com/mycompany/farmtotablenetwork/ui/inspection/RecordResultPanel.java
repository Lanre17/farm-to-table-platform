/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.inspection;

import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author emmanuelcroll
 */

// form panel that pushes on top of InspectorWorkArea when inspector clicks Record Result
public class RecordResultPanel extends JPanel {

    private final InspectionRequest request;
    private final CardSequencePanel cardPanel;
    private final InspectorWorkArea parent;

    private JComboBox<String> comboResult;
    private JLabel errorLabel;

    public RecordResultPanel(InspectionRequest request,
                              CardSequencePanel cardPanel,
                              InspectorWorkArea parent) {
        this.request   = request;
        this.cardPanel = cardPanel;
        this.parent    = parent;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }
    
    private void buildUI() {
        // NORTH
        add(UIFactory.headerSimple("Record Inspection Result"), BorderLayout.NORTH);

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

        // read-only batch info so inspector can confirm what they're recording
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Batch Info"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Crop",    request.getBatch().getCrop().getType(), row++);
        UIFactory.detailRow(card, gbc, "Grade",   request.getBatch().getGrade(), row++);
        UIFactory.detailRow(card, gbc, "Qty (kg)",
            String.valueOf(request.getBatch().getQuantityKg()), row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Result"), gbc);
        gbc.gridwidth = 1;

        // combo lets inspector pick Passed or Failed
        comboResult = UIFactory.labeledCombo(card, gbc, "Result *",
            new String[]{StatusConstants.PASSED, StatusConstants.FAILED}, row++);

        // vertical glue pushes error label to bottom
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
        JButton btnSubmit = UIFactory.primaryButton("Submit Result");
        btnBack.addActionListener(e   -> popPanel());
        btnSubmit.addActionListener(e -> onSubmit());
        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
    }
    
    private void onSubmit() {
        String result = (String) comboResult.getSelectedItem();
        // recordResult handles auto-creating CertificationApproval on Pass
        // and terminal status on Fail — no extra logic needed here
        request.recordResult(result);
        parent.loadTable();
        popPanel();
    }

    private void popPanel() {
        cardPanel.popPanel(this);
    }


}
