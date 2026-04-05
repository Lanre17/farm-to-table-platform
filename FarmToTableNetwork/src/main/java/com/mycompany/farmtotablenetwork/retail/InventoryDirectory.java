/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.retail;

import java.util.ArrayList;

/**
 *
 * @author Lanre
 */
public class InventoryDirectory {
    // Stores all inventory items in the retail store
    private ArrayList<InventoryItem> items = new ArrayList<>();

    // Creates and saves a new inventory item
    public InventoryItem newItem(int purchaseOrderId, String productName,
            int qty, String shelfLocation, String receivedDate) {

        InventoryItem item = new InventoryItem(
                purchaseOrderId, productName, qty, shelfLocation, receivedDate
        );

        items.add(item);
        return item;
    }

    // Allows adding an existing inventory item into the directory
    public void addInventoryItem(InventoryItem item) {
        items.add(item);
    }

    // Returns all inventory items for display or processing
    public ArrayList<InventoryItem> getAllItems() {
        return items;
    }
}
