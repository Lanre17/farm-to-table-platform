/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.inspection;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.CertifierProfile;
import com.mycompany.farmtotablenetwork.requests.CertificationApproval;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author emmanuelcroll
 */




// work area panel for the Certifier role — loads on login via MainFrame instanceof routing
public class CertifierWorkArea extends JPanel {

    private final CertifierProfile profile;
    private final CardSequencePanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnApprove;
    private JButton btnDeny;

    // col 0 stores the CertificationApproval object for retrieval on row click
    private static final String[] COLUMNS = {
        "Approval #", "Crop", "Inspector", "Inspection Date", "Status"
    };

    public CertifierWorkArea(CertifierProfile profile, CardSequencePanel cardPanel) {
        this.profile   = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadTable();
    }
    
    private void buildUI() {
        // NORTH
        add(UIFactory.header("Certification Work Area",
                profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);

        // CENTER — table of pending certification approvals
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UIFactory.styledTable(tableModel);

        // Approve and Deny only available when a PENDING_REVIEW row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasPending = getSelected() != null
                    && StatusConstants.PENDING_REVIEW.equals(getSelected().getStatus());
                btnApprove.setEnabled(hasPending);
                btnDeny.setEnabled(hasPending);
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

        btnApprove = UIFactory.primaryButton("Approve →");
        btnDeny    = UIFactory.dangerButton("Deny");
        btnApprove.setEnabled(false);
        btnDeny.setEnabled(false);

        btnApprove.addActionListener(e -> pushIssueCertPanel());
        btnDeny.addActionListener(e    -> onDeny());

        btnBar.add(btnDeny);
        btnBar.add(btnApprove);
        add(btnBar, BorderLayout.SOUTH);
    }

}