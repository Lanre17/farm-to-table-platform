/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.retail;

import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import java.util.ArrayList;

/**
 *
 * @author Lanre
 */
public class PurchaseOrderDirectory {
    // Stores all purchase orders created by retail
    private ArrayList<PurchaseOrder> orders = new ArrayList<>();

 
    // Factory method to create and store a new PurchaseOrder
    public PurchaseOrder newOrder(String productName, int qty, String distributor,
                                 String requestedDate,
                                 com.mycompany.farmtotablenetwork.ecosystem.Organization senderOrg,
                                 com.mycompany.farmtotablenetwork.ecosystem.Organization receiverOrg) {

        PurchaseOrder o = new PurchaseOrder(
                productName, qty, distributor, requestedDate, senderOrg, receiverOrg
        );

        orders.add(o);
        return o;
    }
     
    // Allows other classes to add an already-created order into the directory
    public void addPurchaseOrder(PurchaseOrder o) {
        orders.add(o);
    }

    // Returns all saved purchase orders
    public ArrayList<PurchaseOrder> getAllOrders() {
        return orders;
    }
}
