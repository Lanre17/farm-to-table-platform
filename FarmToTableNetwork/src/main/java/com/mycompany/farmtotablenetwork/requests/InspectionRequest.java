/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;


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
    
    // inspector self-assigns from the open requests table, no admin needed
    public void assign(String inspectorUsername) {
        this.inspector = inspectorUsername;
        this.updateStatus(StatusConstants.ASSIGNED);
    }
    
    // PASSED — updates status and auto-creates a CertificationApproval
    // FAILED — terminal, no re-inspection
    public void recordResult(String result) {
        this.result = result;
        if (StatusConstants.PASSED.equals(result)) {
            this.updateStatus(StatusConstants.PASSED);
            this.batch.updateStatus(StatusConstants.PASSED); // update status on the HarvestBatch object too ps added 4/14/26
            // auto-create CertificationApproval, no manual step needed
            CertificationApproval ca = new CertificationApproval(
                this,
                this.getReceiverOrg(),               // Inspection Dept becomes sender
                ConfigureABusiness.certificationDept // Certification Dept receives it
            );
            ConfigureABusiness.certDirectory.addCertificationApproval(ca);
            ConfigureABusiness.workRequestDirectory.addRequest(ca);
        } else {
            this.updateStatus(StatusConstants.FAILED);
            this.batch.updateStatus(StatusConstants.FAILED); // update status on the HarvestBatch object too ps added 4/14/26
        }
    }

    public HarvestBatch getBatch()     { return batch; }
    public String       getInspector() { return inspector; }
    public String       getResult()    { return result; }

    @Override
    public String toString() {
        return "InspectionRequest #" + getRequestId()
               + " — " + batch.getCrop().getType()
               + " [" + getStatus() + "]";
    }
}
