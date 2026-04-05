/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.inspection;

import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author emmanuelcroll
 */


// NOT a WorkRequest subclass — this is the certified output artifact
// Henry's WarehouseManager reads CertificationDirectory.findByStatus(CERTIFIED) to create WarehouseItems

public class Certification {

    private static int count = 0; // auto-generates a unique cert ID across all instances
    private int certId;
    private InspectionRequest inspection; // the inspection that produced this cert
    private String certifier;             // username of the certifier who approved it
    private String certType;              // e.g. "Organic", "Standard"
    private String expiryDate;            // format YYYY-MM-DD
    private String status;

    // status starts as CERTIFIED — only created when CertificationApproval.approve() is called
    public Certification(InspectionRequest inspection, String certifier,
                         String certType, String expiryDate) {
        this.certId     = ++count;
        this.inspection = inspection;
        this.certifier  = certifier;
        this.certType   = certType;
        this.expiryDate = expiryDate;
        this.status     = StatusConstants.CERTIFIED;
    }

    public void   updateStatus(String s)     { this.status = s; }
    public int    getCertId()                { return certId; }
    public InspectionRequest getInspection() { return inspection; }
    public String getCertifier()             { return certifier; }
    public String getCertType()              { return certType; }
    public String getExpiryDate()            { return expiryDate; }
    public String getStatus()                { return status; }

    @Override
    public String toString() {
        return certType + " Cert #" + certId
               + " — " + inspection.getBatch().getCrop().getType()
               + " [" + status + "]";
    }
}
