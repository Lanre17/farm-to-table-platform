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
public class Certification {

    private static int count = 0;
    private int certId;
    private InspectionRequest inspection;
    private String certifier;
    private String certType;
    private String expiryDate;
    private String status;

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
