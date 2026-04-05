/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Lanre
 */

public class ProcurementOfficerWorkArea extends JPanel{
    private final ProcurementOfficerProfile profile;
    private final JPanel cardPanel;

    public ProcurementOfficerWorkArea(ProcurementOfficerProfile profile, JPanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Simple placeholder UI (we will enhance later)
        JLabel label = new JLabel("Procurement Officer Work Area", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
