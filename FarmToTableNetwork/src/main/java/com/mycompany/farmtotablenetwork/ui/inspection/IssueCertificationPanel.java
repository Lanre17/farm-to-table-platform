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
}