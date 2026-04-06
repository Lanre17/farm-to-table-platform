/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.farm.Crop;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author p.starobinets
 */
public class HarvestSubmission extends WorkRequest {
    private Crop crop;
    private String submittedBy;
    private float estimatedQty;
    
    public HarvestSubmission(Crop crop, String submittedBy, float estimatedQty, Organization senderOrg, Organization receiverOrg){
        super (senderOrg, receiverOrg, StatusConstants.SUBMITTED);
        
        this.crop = crop;
        this.submittedBy = submittedBy;
        this.estimatedQty = estimatedQty;
    }
    
    /* from Polina
    Approval of a `HarvestSubmission` does **not** auto-create the batch. 
    The Harvest Worker fills a separate form pre-filled with the crop reference, 
    and enters quantity, grade, and packaging type manually. This is an intentional
    design decision given the time constraints. We may add this as an improvment if we are
    ahead of schedule.*/
    
    public void approve(){
        updateStatus(StatusConstants.APPROVED);
        crop.setStatus(StatusConstants.APPROVED);
    }
    
    public void reject(){
        updateStatus(StatusConstants.REJECTED);
        crop.setStatus(StatusConstants.REJECTED);
    }

    public Crop getCrop() {
        return crop;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public float getEstimatedQty() {
        return estimatedQty;
    }
    
    @Override
    public String toString(){
        return "HarvestSubmission #"+getRequestId()+"-"+crop.getType()+"["+getStatus()+"]";
    }
}
