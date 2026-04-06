/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.farm;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.HarvestWorkerProfile;
import com.mycompany.farmtotablenetwork.requests.HarvestSubmission;
import com.mycompany.farmtotablenetwork.requests.WorkRequest;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author p.starobinets
 */
public class HarvestWorkerWorkArea extends JPanel {
    private final HarvestWorkerProfile profile;
    private final CardSequencePanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable  table;
    private JButton btnApprove;
    private JButton btnCreateBatch;
    private JButton btnReject;

    private static final String[] COLUMNS = {
        "Submission", "Crop", "Estimated Qty (kg)", "Submitted By", "Status"
        // Col 0: HarvestSubmission object
    };

    public HarvestWorkerWorkArea(HarvestWorkerProfile profile, CardSequencePanel cardPanel) {
        this.profile   = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadTable();
    }

    private void buildUI() {
        add(UIFactory.header("Harvest & Packaging",
                profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UIFactory.styledTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                HarvestSubmission sel = getSelected();
                btnApprove.setEnabled(sel != null
                    && StatusConstants.SUBMITTED.equals(sel.getStatus()));
                btnCreateBatch.setEnabled(sel != null
                    && StatusConstants.APPROVED.equals(sel.getStatus()));
                btnReject.setEnabled(sel != null
                    && StatusConstants.SUBMITTED.equals(sel.getStatus()));
            }
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnApprove     = UIFactory.primaryButton("Approve");
        btnCreateBatch = UIFactory.primaryButton("Create Batch");
        btnReject = UIFactory.primaryButton("Reject");
        btnApprove.setEnabled(false);
        btnCreateBatch.setEnabled(false);
        btnReject.setEnabled(false);

        btnApprove.addActionListener(e     -> onApprove());
        btnCreateBatch.addActionListener(e -> pushCreateBatchPanel());
        btnReject.addActionListener(e -> onReject());

        btnBar.add(btnCreateBatch);
        btnBar.add(btnApprove);
        btnBar.add(btnReject);
        add(btnBar, BorderLayout.SOUTH);
    }

    public void loadTable() {
        tableModel.setRowCount(0);
        for (WorkRequest r : ConfigureABusiness.workRequestDirectory.getAllRequests()) {
            if (r instanceof HarvestSubmission) {
                HarvestSubmission sub = (HarvestSubmission) r;
                tableModel.addRow(new Object[]{
                    sub,
                    sub.getCrop().getType(),
                    sub.getEstimatedQty(),
                    sub.getSubmittedBy(),
                    sub.getStatus()
                });
            }
        }
    }

    private HarvestSubmission getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return (HarvestSubmission) tableModel.getValueAt(row, 0);
    }

    private void onApprove() {
        HarvestSubmission sub = getSelected();
        if (sub == null) return;
        sub.approve();
        loadTable();
    }

    private void pushCreateBatchPanel() {
        HarvestSubmission sub = getSelected();
        if (sub == null) return;
        cardPanel.pushPanel(new CreateBatchPanel(sub, cardPanel, this));
    }

    private void onReject() {
        HarvestSubmission sub = getSelected();
         if (sub == null) return;
        sub.reject();
        loadTable();
    }
    
}
