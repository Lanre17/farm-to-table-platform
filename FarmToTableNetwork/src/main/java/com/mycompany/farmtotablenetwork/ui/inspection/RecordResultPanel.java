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

}
