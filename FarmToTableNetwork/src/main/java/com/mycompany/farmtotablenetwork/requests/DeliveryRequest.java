/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.distribution.WarehouseItem;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author Hank_Local
 */
public class DeliveryRequest extends WorkRequest {
    private WarehouseItem warehouseItem;
    private int purchaseOrderId; //HL: decoupled from Retail
    private String driver;
    
    //HL: constructor - cross organization request from Warehouse Ops to Fleet Management, begins with requested status 
    public DeliveryRequest(WarehouseItem warehouseItem, int purchaseOrderId, Organization senderOrg, Organization receiverOrg){ //HL: added organization import using AltEnter
        super(senderOrg, receiverOrg, StatusConstants.REQUESTED); //HL: added StatusConstants import using AltEnter 
        this.warehouseItem = warehouseItem;
        this.purchaseOrderId = purchaseOrderId;
        this.driver = "";
        
    }
    
    //HL: method that assigns the driver to a delivery request when they click "Claim" 
    public void assign(String driverUsername){
        this.driver = driverUsername;
        this.updateStatus(StatusConstants.ASSIGNED);
    }
    
    //HL: method for driver to mark a delivery as in-transit 
    public void markInTransit(){
        this.updateStatus(StatusConstants.IN_TRANSIT);
    }
    
    //HL: method that automatically creates ShipmentReceiptConfirmation upon delivery 
    public void markDelivered(){
        this.updateStatus(StatusConstants.DELIVERED);
        //HL: fleet management becomes sender, retail becomes receiver 
        /* 
        
        TO DO: uncomment when storefrontInvetory is added to ConfigureABusiness 
        
        ShipmentReceiptConfirmation src = new ShipmentReceiptConfirmation(this, this.getReceiverOrg(), ConfigureABusiness.storefrontInventory); 
        ConfigureABusiness.workRequestDirectory.addRequest(src);
        ConfigureABusiness.receiptDirectory.addReceipt(src);
        */
    }
    
    //HL: getters
    public WarehouseItem getWarehouseItem() {
        return warehouseItem;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public String getDriver() {
        return driver;
    }
    
    //HL: Override that displays correct info in UI 
    @Override
    public String toString(){
        return "Delivery Request #" + getRequestId() + " - " + warehouseItem.getProductName() + " [" + getStatus() + "]"; 
    }
    
}
