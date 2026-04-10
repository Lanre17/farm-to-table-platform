/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.reporting;

import com.mycompany.farmtotablenetwork.distribution.WarehouseDirectory;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.farm.HarvestBatchDirectory;
import com.mycompany.farmtotablenetwork.inspection.InspectionDirectory;
import com.mycompany.farmtotablenetwork.requests.WorkRequestDirectory;
import com.mycompany.farmtotablenetwork.retail.PurchaseOrderDirectory;
import java.util.HashMap;

/**
 *
 * @author p.starobinets
 */
public class NetworkSummary {
    private final HarvestBatchDirectory  batchDirectory;
    private final InspectionDirectory    inspectionDirectory;
    private final WarehouseDirectory     warehouseDirectory;
    private final PurchaseOrderDirectory orderDirectory;
    private final WorkRequestDirectory   workRequestDirectory;

    	public NetworkSummary(  HarvestBatchDirectory batchDirectory, 
                                InspectionDirectory inspectionDirectory,
                                WarehouseDirectory warehouseDirectory,
                                PurchaseOrderDirectory orderDirectory,
                                WorkRequestDirectory workRequestDirectory) {
        this.batchDirectory       = batchDirectory;
        this.inspectionDirectory  = inspectionDirectory;
        this.warehouseDirectory   = warehouseDirectory;
        this.orderDirectory       = orderDirectory;
        this.workRequestDirectory = workRequestDirectory;
    	}

    // ── Method 1 (Polina): Count of HarvestBatches grouped by status ──
    /*
    Returns a map of status string → count of HarvestBatches with that status.
   * Used by QualityAnalystWorkArea to show a batch status breakdown table.
   * Example result: {"Packaged": 3, "Failed": 1, "Certified": 2} */

    public HashMap<String, Integer> batchCountByStatus() {
        HashMap<String, Integer> counts = new HashMap<>();
        for (HarvestBatch b : batchDirectory.getAllBatches()) {
            String status = b.getStatus();
            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }
        return counts;
    }
    
    //Method 2 Inspection pass/fail rate as a percentage
    
    
    
    //Method 3 Open WorkRequests grouped by class name
    
    
    //Methods 4 & 5 Total WarehouseItems and total received orders

    
}
