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
