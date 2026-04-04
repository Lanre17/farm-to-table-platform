/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.farm;

import java.util.ArrayList;

/**
 *
 * @author p.starobinets
 */
public class HarvestBatchDirectory {
    private ArrayList<HarvestBatch> batches = new ArrayList<>();
    
    //Create new HarvestBatch method
    public HarvestBatch newBatch(Crop crop, float qty, String grade, String packaging){
        HarvestBatch b = new HarvestBatch(crop, qty, grade, packaging);
        batches.add(b);
        return b;
    }
    
    //add to harvestBatchDirectory method
    public void addHarvestBatch(HarvestBatch b){
        batches.add(b);
    }
    
    //find Batch by ID method
    public HarvestBatch findBatch(int id){
        for (HarvestBatch b: batches){
            if(b.getBatchId()==id)
                return b;
        }
        return null;
    }
    
    //compile into one list
    public ArrayList<HarvestBatch> getAllBatches(){
        return batches;
    }
    
    //basic for loop to filter the list by status
    public ArrayList<HarvestBatch> findbyStatus(String status){
        ArrayList<HarvestBatch> result = new ArrayList<>();
        for (HarvestBatch b: batches){
            if(b.getStatus().equals(status))result.add(b);
        }
        return result;
    }
   
}
