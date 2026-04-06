/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.farm;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.farm.Crop;
import com.mycompany.farmtotablenetwork.requests.HarvestSubmission;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
/**
 *
 * @author p.starobinets
 */
public class SubmitHarvestPanel extends JPanel {
    private Crop crop;
    private final CardSequencePanel cardPanel;
    private final FarmerWorkArea parent;
    private JTextField fieldQty;
    private JLabel errorLabel;
    
    public SubmitHarvestPanel(Crop crop, CardSequencePanel cardPanel, FarmerWorkArea parent){
        this.cardPanel = cardPanel;
        this.parent = parent;
        this.crop = crop;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
    }

    private void buildUI() {
        //-------North aka Header----------
        
    add(UIFactory.headerSimple("Submit Harvest"), BorderLayout.NORTH);

        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING * 3,
            UIConstants.PADDING, UIConstants.PADDING * 3));

        //Container
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING,
                                            UIConstants.PADDING, UIConstants.PADDING)));

        
        //------------Center-----------
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Crop Reference"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Crop Type",    crop.getType(), row++);
        UIFactory.detailRow(card, gbc, "Field",        crop.getFieldLocation(), row++);
        UIFactory.detailRow(card, gbc, "Planting Date", crop.getPlantingDate(), row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Harvest Details"), gbc);
        gbc.gridwidth = 1;

        fieldQty = UIFactory.labeledField(card, gbc, "Estimated Quantity (kg) *", row++);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(formOuter);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);

        //---------SOUTH---------button bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack   = UIFactory.secondaryButton("← Back");
        JButton btnSubmit = UIFactory.primaryButton("Submit");
        btnBack.addActionListener(e   -> popPanel());
        btnSubmit.addActionListener(e -> onSubmit());

        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
    }
    //Validation block
    
    private boolean validateInputs() {
        try {
            float qty = Float.parseFloat(fieldQty.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("⚠ Quantity must be a positive number.");
            return false;
        }
        errorLabel.setText(" ");
        return true;
    }
    
    //create HarvestSubmission instance and add to workrequestdirectory, return to FarmerWorkArea
        private void onSubmit() {
        if (!validateInputs()) return;
        float qty = Float.parseFloat(fieldQty.getText().trim());
        HarvestSubmission sub = new HarvestSubmission(crop, 
                parent.getName(),               //automatically grabs the submitter's name
                qty,
                ConfigureABusiness.cropMgmt,
                ConfigureABusiness.harvestAndPackaging);
        ConfigureABusiness.workRequestDirectory.addRequest(sub);
        crop.setStatus(StatusConstants.SUBMITTED);
        parent.loadTable();
        
        // Show success
        JOptionPane.showMessageDialog(
            this,
        "Harvest submitted successfully.",
        "Success",
        JOptionPane.INFORMATION_MESSAGE
        );
        popPanel();
        
    }

        //Back button
    private void popPanel() {
        cardPanel.popPanel(this);
    }


    
}
