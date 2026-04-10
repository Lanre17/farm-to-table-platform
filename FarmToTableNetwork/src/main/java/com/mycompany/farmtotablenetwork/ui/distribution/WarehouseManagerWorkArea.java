/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
    
    //HL: table 1 (top) for warehoused items 
    private DefaultTableModel warehouseTableModel;
    private JTable warehouseTable;
    
    //HL: table 2 (bottom) for all purchase orders (both submitted & fulfilled) 
    private DefaultTableModel ordersTableModel;
    private JTable ordersTable;
    
    //HL: buttons 
    private JButton btnReceive; //HL: added import using AltEnter
    private JButton btnCreateDelivery;
    
    //HL: Table 1 Column 0 - stores Warehouse Item for retrieval upon clicking a row 
    private static final String[] WAREHOUSE_COLS = { "Item", "Product", "Qty", "Location", "Cert Type", "Status" }; 
    
    //HL: Table 2 Column 0 - stores Purchase Order for retrieval upon clicking a row 
    private static final String[] ORDER_COLS = { "Order", "Product", "Qty", "Distributor", "Requested", "Status" };
    
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
        
        //HL: CENTER - populates both tables - Warehouse Items on top, Purchase Orders on bottom 
        //HL: gives tables equal spacing in UI 
        JPanel center = new JPanel(new GridLayout(2, 1, 0, UIConstants.PADDING)); //HL: added import using AltEnter 
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));
        
        
        //HL: top table for warehouse items 
        warehouseTableModel = new DefaultTableModel(WAREHOUSE_COLS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; } //HL: ensures table info is not editable 
        };
        warehouseTable = UIFactory.styledTable(warehouseTableModel);
        
        //HL: enforces that user can only create dlievery when an item in the top table is selected 
        warehouseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnCreateDelivery.setEnabled(warehouseTable.getSelectedRow() >= 0);
            }
        });
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIConstants.BG_APP);
        JLabel topLabel = new JLabel(" Warehoused Items"); //HL: added import using AltEnter 
        topLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        topLabel.setForeground(UIConstants.TEXT_SECONDARY);
        topPanel.add(topLabel, BorderLayout.NORTH);
        topPanel.add(UIFactory.tableScrollPane(warehouseTable), BorderLayout.CENTER);
        center.add(topPanel);
        
        //HL: bottom table for purchase orders
        ordersTableModel = new DefaultTableModel(ORDER_COLS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; } //HL: ensures table info is not editable
        };
        ordersTable = UIFactory.styledTable(ordersTableModel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UIConstants.BG_APP);
        JLabel bottomLabel = new JLabel(" Purchase Orders from Retail");
        bottomLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        bottomLabel.setForeground(UIConstants.TEXT_SECONDARY);
        bottomPanel.add(bottomLabel, BorderLayout.NORTH);
        bottomPanel.add(UIFactory.tableScrollPane(ordersTable), BorderLayout.CENTER);
        center.add(bottomPanel);

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
    
    //HL: method to load both Warehoused Items & Purchase Orders tables in UI
    //HL: automatically refreshes both tables whenever warehoused items or purhcase order status is changed 
     public void loadTables() {
         
         //HL: Table 1: All Warehouse items 
        warehouseTableModel.setRowCount(0);
        for (WarehouseItem item : ConfigureABusiness.warehouseDirectory.getAllItems()) {
            warehouseTableModel.addRow(new Object[]{
                item,
                item.getProductName(),
                item.getQty(),
                item.getLocation(),
                item.getCertification().getCertType(),
                item.getStatus()
            });
        }
         
         //HL: Table 2: All Purchase Orders (submitted & fulfilled) 
        ordersTableModel.setRowCount(0);
         for (PurchaseOrder po : ConfigureABusiness.orderDirectory.getAllOrders()) { //HL: added purchase order import using AltEnter 
            ordersTableModel.addRow(new Object[]{
                po,
                po.getProductName(),
                po.getQty(),
                po.getDistributor(),
                po.getRequestedDate(),
                po.getStatus()
            });
        }
         
     }
    

    //HL: method to refresh both tables 
    public void loadTable() {
        loadTables();
    }
    
    //HL: getter for WarehouseItem, adds to Column 0 in table 
    private WarehouseItem getSelected(){
        int row = warehouseTable.getSelectedRow();
        if (row < 0) return null;
        return (WarehouseItem) warehouseTableModel.getValueAt(row, 0);
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
