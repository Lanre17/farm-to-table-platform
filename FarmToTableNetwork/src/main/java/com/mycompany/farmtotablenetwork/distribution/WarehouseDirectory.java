/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.distribution;

import com.mycompany.farmtotablenetwork.inspection.Certification;
import java.util.ArrayList;

/**
 *
 * @author Hank_Local
 */
public class WarehouseDirectory {
    private ArrayList<WarehouseItem> items = new ArrayList<>(); //HL: added import for ArrayList using AltEnter
    
    //HL: method that creates a new WarehouseItem & adds it to directory  
    public WarehouseItem newItem(Certification cert, String productName, int qty, String location){ //HL: added certification import using AltEnter 
        WarehouseItem item = new WarehouseItem(cert, productName, qty, location);
        items.add(item);
        return item;     
    }
    
    //HL: method that allows user to add existing items to warehouse inventory (needed for seeded data) 
    public void addWarehouseItem(WarehouseItem i) {
        items.add(i);
    }
    
    //HL: method that allows user to find items in a warehouse by ID 
    public WarehouseItem findItem(int id) {
        for (WarehouseItem i : items){
            if (i.getItemId() == id) return i; 
        }
        return null; 
    }
    
    //HL: method that populates warehouse invetory in UI (WarehouseManagerWorkArea) 
    public ArrayList<WarehouseItem> getAllItems() {
        return items;
    }
    
    //HL: method that allows user to find a list of items by status (specifically to show certified items that are available for inventory stocking) 
    public ArrayList<WarehouseItem> findByStatus(String status) {
        ArrayList<WarehouseItem> result = new ArrayList<>();
        for (WarehouseItem i : items){
            if (i.getStatus().equals(status)) result.add(i);
        }
        return result;
    }
    
}
