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

}