/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.distribution;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.DeliveryDriverProfile;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
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
public class DeliveryDriverWorkArea extends JPanel { //HL: added import using AltEnter 
    private final DeliveryDriverProfile profile; //HL: added import using AltEnter
    private final CardSequencePanel cardPanel; //HL: added import using AltEnter

    private DefaultTableModel tableModel; //HL: added import using AltEnter
    private JTable table; //HL: added import using AltEnter
    private JButton btnClaim; //HL: added import using AltEnter
    private JButton btnMarkInTransit;
    private JButton btnMarkDelivered;
    
    //HL: column 0, stores DeliveryRequest for retreiving on row click 
    private static final String[] COLUMNS = {
        "Request", "Product", "Qty", "Purchase Order ID", "Driver", "Status"
    };
    
    //HL: constructor 
    public DeliveryDriverWorkArea(DeliveryDriverProfile profile, CardSequencePanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout()); //HL: added import using AltEnter
        setBackground(UIConstants.BG_APP); //HL: added import using AltEnter
        buildUI(); //HL: created method using AltEnter
        loadTable(); //HL: created method using AltEnter
    }

    private void buildUI() {
        //HL: NORTH - generates title & user info in header
        add(UIFactory.header("Fleet / Delivery Management", profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH); //HL: added import using AltEnter
        
        //HL: CENTER - generates table of all DeliveryReqeusts 
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; } //HL: ensures fields are not editable 
        };
        table = UIFactory.styledTable(tableModel);
        
        /*HL: button to update a DeliveryRequest:
            claim (requested status only - aka unassigned deliveries)
            Mark in-transit (driver can only do so on deliveries that are assigned to them) 
            Mark delivered (driver can only do so on assigned deliveries that have an IN_TRANSIT status) 
        */
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DeliveryRequest selected = getSelected(); //HL: added import using AltEnter, created method using AltEnter
                if (selected == null) {
                    btnClaim.setEnabled(false);
                    btnMarkInTransit.setEnabled(false);
                    btnMarkDelivered.setEnabled(false);
                } else {
                    String status = selected.getStatus();
                    String driverName = profile.getPerson().getFullName();
                    btnClaim.setEnabled(StatusConstants.REQUESTED.equals(status)); //HL: added import using AltEnter
                    btnMarkInTransit.setEnabled(
                        StatusConstants.ASSIGNED.equals(status)
                        && driverName.equals(selected.getDriver())
                    );
                    btnMarkDelivered.setEnabled(
                        StatusConstants.IN_TRANSIT.equals(status)
                        && driverName.equals(selected.getDriver())
                    );
                }
            }
        });
        
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING)); //HL: added import using AltEnter
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        
        //HL: SOUTH - buttons (Claim, Mark In Transit, Mark Delivered) 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING)); //HL: added import using AltEnter
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnClaim = UIFactory.primaryButton("Claim");
        btnMarkInTransit = UIFactory.primaryButton("Mark In Transit");
        btnMarkDelivered = UIFactory.primaryButton("Mark Delivered");
        btnClaim.setEnabled(false);
        btnMarkInTransit.setEnabled(false);
        btnMarkDelivered.setEnabled(false);

        btnClaim.addActionListener(e -> onClaim()); //HL: created method using AltEnter
        btnMarkInTransit.addActionListener(e -> onMarkInTransit()); //HL: created method using AltEnter
        btnMarkDelivered.addActionListener(e -> onMarkDelivered());

        btnBar.add(btnMarkDelivered);
        btnBar.add(btnMarkInTransit);
        btnBar.add(btnClaim);
        add(btnBar, BorderLayout.SOUTH);
        
    }

    //HL: method that loads table of all DeliveryRequests
    public void loadTable() {
        tableModel.setRowCount(0);
        for (DeliveryRequest dr : ConfigureABusiness.deliveryDirectory.getAllDeliveries()) { //HL: added import using AltEnter 
            tableModel.addRow(new Object[]{
                dr, //HL: Column 0, deilveryRequest stored here for retreival
                dr.getWarehouseItem().getProductName(),
                dr.getWarehouseItem().getQty(),
                dr.getPurchaseOrderId(),
                dr.getDriver().isEmpty() ? "Unassigned" : dr.getDriver(),
                dr.getStatus()
            });
        }
    }

    //HL: getter for selected DeliveryRequest in table 
    private DeliveryRequest getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return (DeliveryRequest) tableModel.getValueAt(row, 0);
    }

    //HL: method that automatically assigns the logged-in driver to a delivery request, no manual driver assignment needed  
    private void onClaim() {
        DeliveryRequest dr = getSelected();
        if (dr == null) return;
        dr.assign(profile.getPerson().getFullName());
        loadTable();
    }

    //HL: method that allows a driver to mark a delivery as in-transit 
    private void onMarkInTransit() {
        DeliveryRequest dr = getSelected();
        if (dr == null) return;
        dr.markInTransit();
        
        //ps added 4/14/26. This bit marks the PO as "in transit"
        PurchaseOrder po = ConfigureABusiness.orderDirectory.findOrder(dr.getPurchaseOrderId());
        if (po != null) 
            po.markInTransit();
        //end of polina's addition
        
        loadTable();
    }

    //HL: method that allows a driver to mark an in-transit delivery as delivered 
    //HL: automatically creates ShipmentReceiptConfirmation (and adds it to receiptDirectory) 
    //HL: Clerk will see the pending receipt in InventoryClerkWorkArea 
    private void onMarkDelivered() {
        DeliveryRequest dr = getSelected();
        if (dr == null) return;
        dr.markDelivered();
        
        //ps added 4/14/26 these 3 lines update the status on the PO
        PurchaseOrder po = ConfigureABusiness.orderDirectory.findOrder(dr.getPurchaseOrderId());
        if (po != null) 
            po.receive();
        //end of polina's addition
        loadTable();
    }
    
}
