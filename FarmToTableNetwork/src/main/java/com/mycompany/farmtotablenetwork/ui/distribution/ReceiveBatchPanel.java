/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.inspection.Certification;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Hank_Local
 */
public class ReceiveBatchPanel extends JPanel { //HL: added import using AltEnter 
    private final CardSequencePanel cardPanel; //HL: added import using AltEnter
    private final WarehouseManagerWorkArea parent; //HL: reference to parent (WarehouseManagerWorkArea) so table refreshes after submission 
    
    private JComboBox<Certification> comboCert; //HL: added import using AltEnter
    private JTextField fieldProduct; //HL: added import using AltEnter
    private JTextField fieldQty;
    private JTextField fieldLocation;
    private JLabel errorLabel;
    
    //HL: constructor 
    public ReceiveBatchPanel(CardSequencePanel cardPanel, WarehouseManagerWorkArea parent) {
        this.cardPanel = cardPanel;
        this.parent = parent;
        setLayout(new BorderLayout()); //HL: added import using AltEnter
        setBackground(UIConstants.BG_APP); //HL: added import using AltEnter
        buildUI(); //HL: created method using AltEnter 
    }

    //HL: method that populates consistent UI pattern with other roles in the ecosystem 
    private void buildUI() {
        //HL: NORTH - panel title header 
        add(UIFactory.headerSimple("Receive Certified Batch"), BorderLayout.NORTH); //HL: added import using AltEnter
        
        //HL: UI structure padding to match NewCropPanel 
        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING * 3, UIConstants.PADDING, UIConstants.PADDING * 3));
        
        //HL: card form container 
        JPanel card = new JPanel(new GridBagLayout()); //HL: added import using AltEnter
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING,
                        UIConstants.PADDING, UIConstants.PADDING)));

        GridBagConstraints gbc = new GridBagConstraints(); //HL: added import using AltEnter
        gbc.insets = new Insets(6, 8, 6, 8); //HL: added import using AltEnter
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        
        //HL: area to select certification 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Select Certification"), gbc);
        gbc.gridwidth = 1;
        
        //HL: ensures that only "Certified" status certifications into combo box by filtering through certDirectory 
        ArrayList<Certification> certifiedList = ConfigureABusiness.certDirectory.findByStatus(StatusConstants.CERTIFIED);
        comboCert = UIFactory.labeledCombo(card, gbc, "Certification *", certifiedList.toArray(new Certification[0]), row++);
        
        //HL: area to view item details (name, quantity, warehouse location) 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Item Details"), gbc);
        gbc.gridwidth = 1;
        
        fieldProduct = UIFactory.labeledField(card, gbc, "Product Name ", row++);
        fieldQty = UIFactory.labeledField(card, gbc, "Quantity (whole units) ", row++);
        fieldLocation = UIFactory.labeledField(card, gbc, "Warehouse Location ", row++);
        
        //HL: vertical glue that pushes error label to bottom of panel
        //HL: without this the UI would combine all rows together 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc); //HL: added import using AltEnter 
        gbc.weighty = 0;
        
        //HL: error label - blank until validateInputs() method discovers any errors 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(formOuter); //HL: added import using AltEnter 
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);
        
        //HL: SOUTH - buttons (back & add to warehouse) 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING)); //HL: added import using AltEnter
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack   = UIFactory.secondaryButton("Back"); //HL: added import using AltEnter
        JButton btnSubmit = UIFactory.primaryButton("Add to Warehouse");
        btnBack.addActionListener(e -> popPanel()); //HL: created method using AltEnter
        btnSubmit.addActionListener(e -> onSubmit()); //HL: created method using AltEnter
        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
        
        
    }
    
    //HL: method to validate form inputs 
    //HL: called by onSubmit()method which will only proceed if all fields are true, if any are false user gets 1 of 4 error messages 
    //HL: checks combo box, product name text field, quantity (ensures Qty is positive whole #), and warehouse text field  
    private boolean validateInputs() {
        if (comboCert.getSelectedItem() == null) {
            errorLabel.setText("No certified batches available.");
            return false;
        }
        if (fieldProduct.getText().trim().isEmpty()) {
            errorLabel.setText("Product name required.");
            return false;
        }
        try {
            int q = Integer.parseInt(fieldQty.getText().trim());
            if (q <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Quantity must be a positive whole number.");
            return false;
        }
        if (fieldLocation.getText().trim().isEmpty()) {
            errorLabel.setText("Warehouse location required.");
            return false;
        }
        errorLabel.setText(" ");
        return true;
    }
    
    //HL: method that only runs if inputs are all validated
    //HL: if all inputs are validated, adds item to warehouseDirectory
    private void onSubmit() {
        if (!validateInputs()) return;
        
        Certification cert = (Certification) comboCert.getSelectedItem(); //HL: certification object extracted (no null check necessary, validateInputs already confirms)
        ConfigureABusiness.warehouseDirectory.newItem(cert, fieldProduct.getText().trim(),Integer.parseInt(fieldQty.getText().trim()), fieldLocation.getText().trim());
        parent.loadTable(); // refreshes WarehouseManagerWorkArea (parent) table
        popPanel();
    }

    //HL: back button navigation 
    private void popPanel() {
        cardPanel.popPanel(this);
    }
    
}
