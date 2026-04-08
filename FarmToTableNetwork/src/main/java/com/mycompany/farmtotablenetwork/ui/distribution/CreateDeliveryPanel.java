/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.Shipment;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Hank_Local
 */
public class CreateDeliveryPanel extends JPanel { //HL: added import using AltEnter 
    private final WarehouseItem item; //HL: item selected in WarehouseManagerWorkArea, added import using AltEnter
    private final CardSequencePanel cardPanel;
    private final WarehouseManagerWorkArea parent;

    private JTextField fieldPoId; //HL: added import using AltEnter
    private JTextField fieldNotes;
    private JLabel errorLabel; //HL: added import using AltEnter
    
    //HL: constructor, item is pass from WarehouseManagerWorkArea (parent) 
    public CreateDeliveryPanel(WarehouseItem item, CardSequencePanel cardPanel, WarehouseManagerWorkArea parent) {
        this.item = item;
        this.cardPanel = cardPanel;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP); //HL: added import using AltEnter
        buildUI(); //HL: created method using AltEnter 
    }

    //HL: method that populates consistent UI pattern with other roles in the ecosystem
    private void buildUI() {
        //HL: NORTH - title header 
        add(UIFactory.headerSimple("Create Delivery Request"), BorderLayout.NORTH); //HL: added import using AltEnter 
        
        //HL: form padding 
        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING * 3, UIConstants.PADDING, UIConstants.PADDING * 3)); //HL: added import using AltEnter
        
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
        
        //HL: read-only item reference, allows WarehouseManager to view & confirm item they are creating a delivery for 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Item Reference"), gbc);
        gbc.gridwidth = 1;

        UIFactory.detailRow(card, gbc, "Product", item.getProductName(), row++);
        UIFactory.detailRow(card, gbc, "Qty", String.valueOf(item.getQty()), row++);
        UIFactory.detailRow(card, gbc, "Location", item.getLocation(), row++);
        UIFactory.detailRow(card, gbc, "Cert Type",item.getCertification().getCertType(), row++);
        
        //HL: delivery details area - Purchase Order ID & Tracking Notes (optional) 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Delivery Details"), gbc);
        gbc.gridwidth = 1;
        
        //HL: Purchase Order ID links the delivery to PurchaseOrder & stored as an integer 
        fieldPoId  = UIFactory.labeledField(card, gbc, "Purchase Order ID *", row++);
        //HL: makes tracking notes optional — if there are notes, they are passed to Shipment, notes do not require validation 
        fieldNotes = UIFactory.labeledField(card, gbc, "Tracking Notes", row++);
        
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
        
        //HL: text is wrapped in scroll pane if UI is resized
        formOuter.add(card, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(formOuter); //HL: added import using AltEnter 
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);
        
        //HL: SOUTH - buttons (back & create delivery) 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING)); //HL: added import using AltEnter 
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack   = UIFactory.secondaryButton("Back"); //HL: added import using AltEnter 
        JButton btnSubmit = UIFactory.primaryButton("Create Delivery");
        btnBack.addActionListener(e -> popPanel()); //HL: created method using AltEnter
        btnSubmit.addActionListener(e -> onSubmit()); //HL: created method using AltEnter
        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
        
    }
    
    //HL: method that validates Purchase Order ID (again, tracking notes are optional and do not require validation) 
    //HL: called by onSubmit()method which will only proceed if all fields are true
    private boolean validateInputs() {
        try {
            int id = Integer.parseInt(fieldPoId.getText().trim()); //HL enforces whole numbers (ex. cannot add 1.5) 
            if (id <= 0) throw new NumberFormatException(); //HL: enforces positive whole numbers (0 and negatives not allowed) 
        } catch (NumberFormatException ex) {
            errorLabel.setText("Purchase Order ID must be a positive whole number");
            return false;
        }
        errorLabel.setText(" ");
        return true;
    }

    //HL: method that creates a DeliveryRequest (for driver) & Shipment
    //HL: only runs if input is validated
    private void onSubmit() {
        if (!validateInputs()) return;
        int poId = Integer.parseInt(fieldPoId.getText().trim());
        
        //HL: creates DeliveryRequest, adds it to DeliveryDirectory (cross-organization request) 
        DeliveryRequest dr = ConfigureABusiness.deliveryDirectory.newDelivery( //HL: added imports using AltEnter
                item,
                poId,
                ConfigureABusiness.warehouseOps, //HL: sender 
                ConfigureABusiness.fleetMgmt //HL: receiver 
        );
        
        //HL: adds to workRequestDirectory 
        ConfigureABusiness.workRequestDirectory.addRequest(dr);
        
        //HL: Creates Shipment when DeliveryRequest is made by Warehouse Manager 
        new Shipment(dr, fieldNotes.getText().trim()); //HL: fieldNotes are optional, added Shipment import using AltEnter
        
        parent.loadTable(); //HL: refreshes WarehouseManagerWorkArea (parent) table
        popPanel();
        
    }

    //HL: back button navigation 
    private void popPanel() {
        cardPanel.popPanel(this);
    }
    
}
