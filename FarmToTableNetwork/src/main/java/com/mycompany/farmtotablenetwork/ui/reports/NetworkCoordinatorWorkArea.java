/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.reports;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.NetworkCoordinatorProfile;
import com.mycompany.farmtotablenetwork.requests.WorkRequest;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
/**
 *
 * @author emmanuelcroll
 */


// read-only oversight panel for the Network Coordinator role
// shows all work requests across all enterprises with a status filter
public class NetworkCoordinatorWorkArea extends JPanel {

    private final NetworkCoordinatorProfile profile;
    private final CardSequencePanel cardPanel;

    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> filterCombo;

    private static final String[] COLUMNS = {
        "Request", "Type", "Sender Org", "Receiver Org", "Created", "Status"
        // col 0 stores the WorkRequest object
    };

    private static final String[] FILTER_OPTIONS = {
        "All",
        StatusConstants.SUBMITTED,
        StatusConstants.ASSIGNED,
        StatusConstants.IN_PROGRESS,
        StatusConstants.PASSED,
        StatusConstants.FAILED,
        StatusConstants.PENDING_REVIEW,
        StatusConstants.APPROVED,
        StatusConstants.DENIED,
        StatusConstants.REQUESTED,
        StatusConstants.IN_TRANSIT,
        StatusConstants.DELIVERED,
        StatusConstants.CONFIRMED,
        StatusConstants.FULFILLING,
        StatusConstants.SHIPPED,
        StatusConstants.RECEIVED,
        StatusConstants.PENDING,
        StatusConstants.STOCKED
    };

    public NetworkCoordinatorWorkArea(NetworkCoordinatorProfile profile, CardSequencePanel cardPanel) {
        this.profile   = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadTable("All");
    }
    
  private void buildUI() {
        // NORTH
        add(UIFactory.header("Network Overview — All Requests",
                profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);

        // CENTER — filter bar + table
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));

        // filter bar
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filterBar.setBackground(UIConstants.BG_APP);
        JLabel filterLabel = new JLabel("Filter by status:");
        filterLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        filterLabel.setForeground(UIConstants.TEXT_SECONDARY);
        filterCombo = new JComboBox<>(FILTER_OPTIONS);
        filterCombo.setFont(UIConstants.FONT_BODY);
        filterCombo.setPreferredSize(new Dimension(180, UIConstants.FIELD_HEIGHT));

        // reload table whenever filter selection changes
        filterCombo.addActionListener(e -> {
            String selected = (String) filterCombo.getSelectedItem();
            loadTable(selected == null ? "All" : selected);
        });

        filterBar.add(filterLabel);
        filterBar.add(filterCombo);
        center.add(filterBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UIFactory.styledTable(tableModel);
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // SOUTH — refresh button only, this is a read-only panel
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));
        JButton btnRefresh = UIFactory.secondaryButton("↻ Refresh");
        btnRefresh.addActionListener(e -> {
            String selected = (String) filterCombo.getSelectedItem();
            loadTable(selected == null ? "All" : selected);
        });
        btnBar.add(btnRefresh);
        add(btnBar, BorderLayout.SOUTH);
    }
  
}

