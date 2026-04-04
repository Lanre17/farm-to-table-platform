/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.distribution;

import com.mycompany.farmtotablenetwork.requests.ShipmentReceiptConfirmation;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import java.util.ArrayList;

/**
 *
 * @author Hank_Local
 */
public class ShipmentReceiptConfirmationDirectory {
    private ArrayList<ShipmentReceiptConfirmation> receipts = new ArrayList<>(); //HL: added ArrayList & ShipmentReceiptConfirmation imports using AltEnter 
    
    //HL: adds receipt to Directory 
    public void addReceipt(ShipmentReceiptConfirmation r){
        receipts.add(r); 
    }
    
    //HL: returns list of all receipts (used by InventoryClerkWorkArea)
    public ArrayList<ShipmentReceiptConfirmation> getAllReceipts(){
        return receipts;
    }
    
    //HL: returns list of receipts that are pending (used by InventoryClerkWorkArea so user can see shipments that need confirmation) 
    public ArrayList<ShipmentReceiptConfirmation> getPendingReceipts(){
        ArrayList<ShipmentReceiptConfirmation> result = new ArrayList<>();
        for (ShipmentReceiptConfirmation r : receipts){
            if (StatusConstants.PENDING.equals(r.getStatus())) result.add(r); //HL: added import for StatusConstants using AltEnter 
        }
        return result; 
    }
    
}
