/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.farmtotablenetwork.ui.inspection;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.InspectorProfile;
import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author emmanuelcroll
 */


public class InspectorWorkArea extends JPanel {

    private final InspectorProfile profile;
    private final CardSequencePanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnClaim;
    private JButton btnRecordResult;

    // col 0 stores the InspectionRequest object for retrieval on row click
    private static final String[] COLUMNS = {
        "Request #", "Crop", "Grade", "Qty (kg)", "Submitted", "Status"
    };

    public InspectorWorkArea(InspectorProfile profile, CardSequencePanel cardPanel) {
        this.profile   = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadTable();
    }

    private void buildUI() {
        // NORTH — header with panel title and logged-in user info
        add(UIFactory.header("Inspection Work Area",
                profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);

        // CENTER — table of all inspection requests
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UIFactory.styledTable(tableModel);

        // enable/disable buttons based on what row is selected and its status
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                InspectionRequest selected = getSelected();
                // Claim only available on SUBMITTED requests
                btnClaim.setEnabled(selected != null
                    && StatusConstants.SUBMITTED.equals(selected.getStatus()));
                // Record Result only available on ASSIGNED requests
                btnRecordResult.setEnabled(selected != null
                    && StatusConstants.ASSIGNED.equals(selected.getStatus()));
            }
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // SOUTH — action buttons
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnClaim        = UIFactory.primaryButton("Claim");
        btnRecordResult = UIFactory.primaryButton("Record Result");
        btnClaim.setEnabled(false);
        btnRecordResult.setEnabled(false);

        btnClaim.addActionListener(e        -> onClaim());
        btnRecordResult.addActionListener(e -> onRecordResult());

        btnBar.add(btnRecordResult);
        btnBar.add(btnClaim);
        add(btnBar, BorderLayout.SOUTH);
    }
    

    public void loadTable() {
        tableModel.setRowCount(0);
        for (InspectionRequest r : ConfigureABusiness.inspectionDirectory.getAllRequests()) {
            tableModel.addRow(new Object[]{
                r,                              // col 0 — object stored for retrieval
                r.getBatch().getCrop().getType(),
                r.getBatch().getGrade(),
                r.getBatch().getQuantityKg(),
                r.getCreatedAt(),
                r.getStatus()
            });
        }
    }

    private InspectionRequest getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return (InspectionRequest) tableModel.getValueAt(row, 0);
    }

    // inspector self-assigns — no admin needed
    private void onClaim() {
        InspectionRequest r = getSelected();
        if (r == null) return;
        r.assign(profile.getPerson().getFullName());
        loadTable();
    }

    // pushes RecordResultPanel on top of this panel
    private void onRecordResult() {
        InspectionRequest r = getSelected();
        if (r == null) return;
        cardPanel.pushPanel(new RecordResultPanel(r, cardPanel, this));
    }
}


