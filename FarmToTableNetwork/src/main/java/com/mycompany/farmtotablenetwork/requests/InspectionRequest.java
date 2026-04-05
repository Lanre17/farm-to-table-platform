/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author emmanuelcroll
 */


// cross-enterprise work request — Farm sends it, Inspection Agency receives it
public class InspectionRequest extends WorkRequest {

    private HarvestBatch batch;    // the batch being inspected
    private String inspector;      // username of assigned inspector, empty until claimed
    private String result;         // "Passed" or "Failed", empty until recorded

    public InspectionRequest(HarvestBatch batch,
                              Organization senderOrg,
                              Organization receiverOrg) {
        super(senderOrg, receiverOrg, StatusConstants.SUBMITTED);
        this.batch     = batch;
        this.inspector = "";
        this.result    = "";
    }
}
