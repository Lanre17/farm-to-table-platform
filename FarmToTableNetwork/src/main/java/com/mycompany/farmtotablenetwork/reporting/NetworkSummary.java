/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.reporting;

import com.mycompany.farmtotablenetwork.distribution.WarehouseDirectory;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.farm.HarvestBatchDirectory;
import com.mycompany.farmtotablenetwork.inspection.InspectionDirectory;
import com.mycompany.farmtotablenetwork.requests.WorkRequest;
import com.mycompany.farmtotablenetwork.requests.WorkRequestDirectory;
import com.mycompany.farmtotablenetwork.retail.PurchaseOrderDirectory;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import java.util.ArrayList;
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
    
    // returns the % of InspectionRequests with status Passed
    // returns 0.0 if no requests exist to avoid divide-by-zero
    public float certPassFailRate() {
        int passed = 0;
        int total  = 0;
        for (com.mycompany.farmtotablenetwork.requests.InspectionRequest r
                : inspectionDirectory.getAllRequests()) {
            total++;
            if (com.mycompany.farmtotablenetwork.ui.StatusConstants.PASSED
                    .equals(r.getStatus())) {
                passed++;
            }
        }
        return total == 0 ? 0.0f : (float) passed / total * 100;
    }
    
    
    //Method 3 Open WorkRequests grouped by class name
    
        public HashMap<String, Integer> openRequestsByType() {
        ArrayList<String> terminal = new ArrayList<>();
        terminal.add(StatusConstants.PASSED);
        terminal.add(StatusConstants.FAILED);
        terminal.add(StatusConstants.DENIED);
        terminal.add(StatusConstants.RECEIVED);
        terminal.add(StatusConstants.STOCKED);
        terminal.add(StatusConstants.DELIVERED);
        terminal.add(StatusConstants.REJECTED);

        HashMap<String, Integer> counts = new HashMap<>();
        for (WorkRequest r : workRequestDirectory.getAllRequests()) {
            if (!terminal.contains(r.getStatus())) {
                String type = r.getClass().getSimpleName();
                counts.put(type, counts.getOrDefault(type, 0) + 1);
            }
        }
        return counts;
    }

    
    //Methods 4 & 5 Total WarehouseItems and total received orders
    public int totalWarehouseItems() {
        return warehouseDirectory.findByStatus(StatusConstants.WAREHOUSED).size();
    }

    public int totalOrdersReceived() {
        return orderDirectory.findByStatus(StatusConstants.RECEIVED).size();
    }



    
}
