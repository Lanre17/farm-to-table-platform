/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.distribution;

import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;

/**
 *
 * @author Hank_Local
 */
public class Shipment {
    private static int count = 0;
    private int shipmentId;
    private DeliveryRequest deliveryRequest; //HL: added import using AltEnter 
    private String trackingNotes;
    private String status;
    
    //HL: constructor, shipment created by warehouse manager upon DeliveryRequest 
    public Shipment(DeliveryRequest deliveryRequest, String trackingNotes) {
        this.shipmentId = ++count;
        this.deliveryRequest = deliveryRequest;
        this.trackingNotes = trackingNotes;
        this.status = "Prepared";
    }
    
    //HL: method that allows Warehouse manager to update shipment status 
    public void updateShipmentStatus(String s){
        this.status = s;
    }
    
    //HL: getters 
    public int getShipmentId() {
        return shipmentId;
    }

    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }

    public String getTrackingNotes() {
        return trackingNotes;
    }

    public String getStatus() {
        return status;
    }
    
    //HL: Override to display proper info in UI 
    @Override 
    public String toString(){
        return "Shipment #" + shipmentId + " [" + status + "]";
    }
    
}
