/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.distribution;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.requests.DeliveryRequest;
import java.util.ArrayList;

/**
 *
 * @author Hank_Local
 */
public class DeliveryDirectory {
    private ArrayList<DeliveryRequest> deliveries = new ArrayList<>(); //HL: added imports for ArrayList & DeliveryRequest using AltEnter 
    
    //HL: method that creates new DeliveryRequest & adds it to directory 
    public DeliveryRequest newDelivery(WarehouseItem item, int purchaseOrderId, Organization senderOrg, Organization receiverOrg) { //HL: added org import using AltEnter
        DeliveryRequest dr = new DeliveryRequest(item, purchaseOrderId, senderOrg, receiverOrg);
        deliveries.add(dr);
        return dr;    
    }
    
    //HL: method that adds existing delivery requests to directory (needed for seeded data) 
    public void addDeliveryRequest(DeliveryRequest r) {
        deliveries.add(r);
    }
    
    //HL: method that finds delivery by ID 
    public DeliveryRequest findDelivery(int id) {
        for (DeliveryRequest r : deliveries){
            if (r.getRequestId() == id) return r; 
        }
        //HL: null check if ID not found
        return null; 
    }
    
    //HL: method that populates list of deliverys in UI Table (DeliveryDriverWorkArea) 
    public ArrayList<DeliveryRequest> getAllDeliveries() {
        return deliveries;
    }
    
    //HL: method that allows user to view list of deliveries by status (specifically for driver to see requested deliveries they can claim) 
    public ArrayList<DeliveryRequest> findByStatus(String status) {
        ArrayList<DeliveryRequest> result = new ArrayList<>();
        for (DeliveryRequest r: deliveries){
            if (r.getStatus().equals(status)) result.add(r);
        }
        return result;
    }
    
    
}
