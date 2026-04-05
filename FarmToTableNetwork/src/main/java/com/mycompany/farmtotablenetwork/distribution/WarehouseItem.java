package com.mycompany.farmtotablenetwork.distribution;

import com.mycompany.farmtotablenetwork.inspection.Certification;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 * 
 * @author Hank_Local
 */
public class WarehouseItem {

    private static int count = 0;
    private int itemId;
    private Certification certification;
    private String productName;
    private int qty; //HL: whole packaged units 
    private String location;
    private String status;

    //HL: constructor - links certified batch to a warehouse location 
    public WarehouseItem(Certification certification, String productName, int qty, String location) {
        this.itemId = ++count;
        this.certification = certification;
        this.productName = productName;
        this.qty = qty;
        this.location = location;
        this.status = StatusConstants.WAREHOUSED;
    }

    //HL: method that is called by a Warehouse Manager when genererating a DeliveryRequest (updates status to requested) 
    public void reserve(){
        updateStatus(StatusConstants.REQUESTED); 
    }
    
    //HL: method that updates quantity of a product in the warehouse when delivery is completed 
    public void deplete(int amount){
        this.qty = Math.max(0, this.qty - amount); //HL: prevents quantity from going negative, 0 is lowest value possible) 
    }

    public void updateStatus(String s) {
        this.status = s;
    }
    
    //HL: getters 
    public int getItemId() {
        return itemId;
    }

    public Certification getCertification() {
        return certification;
    }

    public String getProductName() {
        return productName;
    }

    public int getQty() {
        return qty;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
    
    //HL: Override method to display correct info in UI 
    @Override 
    public String toString(){
        return productName + " (Item #" + itemId + ", qty: " + qty + ")";
    }
    
    
}
