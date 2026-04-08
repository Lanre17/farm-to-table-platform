/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hank_Local
 */
public class WarehouseManagerWorkArea extends JPanel { //HL: added import using AltEnter 
    private final WarehouseManagerProfile profile; //HL: added import using AltEnter
    private final CardSequencePanel cardPanel; //HL: added import using AltEnter
    
    private DefaultTableModel tableModel;//HL: added import using AltEnter
    private JTable table; //HL: added import using AltEnter
    private JButton btnReceive; //HL: added import using AltEnter
    private JButton btnCreateDelivery;
    
    //HL: Column 0 - stores Warehouse Item for retrival upon clicking a row 
    private static final String[] COLUMNS = { "Item", "Product", "Qty", "Location", "Cert Type", "Status"}; 
    
    //HL: constuctor 
    public WarehouseManagerWorkArea(WarehouseManagerProfile profile, CardSequencePanel cardPanel){
        this.profile = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout()); //HL: added import using AltEnter
        setBackground(UIConstants.BG_APP); //HL: added import using AltEnter
        buildUI(); //HL: added method using AltEnter 
        loadTable();//HL: added method using AltEnter 
    }

    //HL: method that populates consistent UI pattern with other roles in the ecosystem 
    private void buildUI() {
        //HL: NORTH - adds panel header w/title & user info (based on who is logged in) 
        add(UIFactory.header("Warehouse Operations", profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH); 
        
        //HL: CENTER - populates table of warehouse items (center of panel) 
        tableModel = new DefaultTableModel(COLUMNS, 0){
             @Override public boolean isCellEditable(int r, int c) { return false; } //HL: ensures warehouse items in table are not editable 
        };
        table = UIFactory.styledTable(tableModel);
        
        //HL: ensures user can only create a Delivery when a row in the table is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnCreateDelivery.setEnabled(table.getSelectedRow() >= 0);
            }
        });
        
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING)); //HL: added import using AltEnter
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        
        //HL: SOUTH - buttons (bottom of panel) 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnReceive = UIFactory.primaryButton("+ Receive Batch");
        btnCreateDelivery = UIFactory.primaryButton("Create Delivery");
        btnCreateDelivery.setEnabled(false);

        btnReceive.addActionListener(e -> pushReceiveBatchPanel()); //HL: added method using AltEnter
        btnCreateDelivery.addActionListener(e -> pushCreateDeliveryPanel()); //HL: added method using AltEnter

        btnBar.add(btnCreateDelivery);
        btnBar.add(btnReceive);
        add(btnBar, BorderLayout.SOUTH);
        
    }

    //HL: method to load table in UI panel 
    public void loadTable() {
        tableModel.setRowCount(0);
        for (WarehouseItem item : ConfigureABusiness.warehouseDirectory.getAllItems()) { //HL: added ConfigureABusiness & WarehouseItem imports using AltEnter
            tableModel.addRow(new Object[]{
                item, //HL: Column 0, warehouseitem stored here for retreival 
                item.getProductName(),
                item.getQty(),
                item.getLocation(),
                item.getCertification().getCertType(),
                item.getStatus()
            });
            
        }
    }
    
    //HL: getter for WarehouseItem, adds to Column 0 in table 
    private WarehouseItem getSelected(){
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return (WarehouseItem) tableModel.getValueAt(row, 0);
    }

    //HL: method that pushes to ReceiveBatchPanel, Warehouse Manager completes form 
    private void pushReceiveBatchPanel() {
        cardPanel.pushPanel(new ReceiveBatchPanel(cardPanel, this));
    }

    //HL: method that pushes to CreateDeliverPanel - passes the WarehouseItem selected in table 
    private void pushCreateDeliveryPanel() {
        WarehouseItem selected = getSelected();
        if (selected == null) return;
        cardPanel.pushPanel(new CreateDeliveryPanel(selected, cardPanel, this));
    }
    
}
