/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author Hank_Local
 */
public class ShipmentReceiptConfirmation extends WorkRequest {
    private DeliveryRequest deliveryRequest;
    private String confirmedBy;
    
    //HL: constructor - automatically creates w/ pending status when driver makes the delivery 
    public ShipmentReceiptConfirmation(DeliveryRequest deliveryRequest, Organization senderOrg, Organization receiverOrg){ //HL: added organization import using AltEnter
        super(senderOrg, receiverOrg, StatusConstants.PENDING); //HL: added StatusContants import using AltEnter
        this.deliveryRequest = deliveryRequest; 
        this.confirmedBy = ""; 
    }
    
    //HL: method that creates the ability for a user at the retail store/restaurant to confirm shipment has been received (called by invetory clerk) 
    public void confirm(String confirmedByUsername){
        this.confirmedBy = confirmedByUsername;
        this.updateStatus(StatusConstants.CONFIRMED);
    }
    
    //HL: method that allows user to update status of shipment to STOCKED in inventory (called by inventory clerk after confirming delivery)
    public void stock(){
        this.updateStatus(StatusConstants.STOCKED); 
    }

    //HL: getter for delivery request 
    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }
    
    //HL: getter for user that confirmed shipment receipt 
    public String getConfirmedBy(){
        return confirmedBy; 
    }
    
    //HL: Override method to display correct info in UI 
    @Override
    public String toString(){
        return "ReceiptConfirmation #" + getRequestId() + " - " + deliveryRequest.getWarehouseItem().getProductName() + " [" + getStatus() + "]";
    }
    
}
