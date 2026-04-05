/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.retail;

import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Lanre
 */

public class InventoryClerkWorkArea extends JPanel {
    private final InventoryClerkProfile profile;
    private final JPanel cardPanel;

    public InventoryClerkWorkArea(InventoryClerkProfile profile, JPanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Simple placeholder UI for inventory role
        JLabel label = new JLabel("Inventory Clerk Work Area", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
