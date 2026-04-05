/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.retail;

import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author Lanre
 */
public class InventoryItem {
    // Simple counter to give each inventory item a unique ID

    private static int count = 0;

    private int inventoryId;
    private int purchaseOrderId;   // Stores the related order ID without tightly coupling to PurchaseOrder
    private String productName;
    private int qty;               // Whole units only
    private String shelfLocation;
    private String receivedDate;   // YYYY-MM-DD
    private String status;

    public InventoryItem(int purchaseOrderId, String productName,
            int qty, String shelfLocation, String receivedDate) {

        this.inventoryId = ++count;
        this.purchaseOrderId = purchaseOrderId;
        this.productName = productName;
        this.qty = qty;
        this.shelfLocation = shelfLocation;
        this.receivedDate = receivedDate;
        this.status = StatusConstants.STOCKED;
    }

    // Updates quantity if stock changes later
    public void updateInventory(int newQty) {
        this.qty = newQty;
    }

    // Lets the inventory item status be changed when needed
    public void updateStatus(String status) {
        this.status = status;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQty() {
        return qty;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return productName + " (Inv #" + inventoryId + ", qty: " + qty + ")";
    }
}
