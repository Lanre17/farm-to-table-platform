/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;
import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.inspection.Certification;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
/**
 *
 * @author emmanuelcroll
 */


// cross-org work request — auto-created when InspectionRequest records a PASS
// routes from Inspection Dept to Certification Dept for final sign-off
public class CertificationApproval extends WorkRequest {

    private InspectionRequest inspection; // the inspection this approval is based on
    private String certifier;             // username of certifier, set on approve()

    public CertificationApproval(InspectionRequest inspection,
                                  Organization senderOrg,
                                  Organization receiverOrg) {
        super(senderOrg, receiverOrg, StatusConstants.PENDING_REVIEW);
        this.inspection = inspection;
        this.certifier  = "";
    }
    
    // certifier fills in cert type and expiry, creates the actual Certification object
    public void approve(String certifierUsername, String certType, String expiryDate) {
        this.certifier = certifierUsername;
        this.updateStatus(StatusConstants.APPROVED);
        Certification cert = new Certification(
            this.inspection, certifierUsername, certType, expiryDate
        );
        ConfigureABusiness.certDirectory.addCertification(cert);
    }

    // denial is terminal — no notification sent back to the Farm
    public void deny(String reason) {
        this.updateStatus(StatusConstants.DENIED);
    }

    public InspectionRequest getInspection() { return inspection; }
    public String            getCertifier()  { return certifier; }

    @Override
    public String toString() {
        return "CertificationApproval #" + getRequestId()
               + " — " + inspection.getBatch().getCrop().getType()
               + " [" + getStatus() + "]";
    }
}
