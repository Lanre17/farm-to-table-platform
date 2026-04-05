/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.inspection;

import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.requests.CertificationApproval;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import java.util.ArrayList;

/**
 *
 * @author emmanuelcroll
 */


// holds two lists — certified output artifacts and the pending approval queue
// Henry calls findByStatus(CERTIFIED) from WarehouseManagerWorkArea to populate WarehouseItems
public class CertificationDirectory {

    private ArrayList<Certification> certs = new ArrayList<>();
    private ArrayList<CertificationApproval> approvals = new ArrayList<>();

    // factory method — creates and stores a new certification in one step
    public Certification newCertification(InspectionRequest inspection,
            String certifier, String certType, String expiryDate) {
        Certification c = new Certification(inspection, certifier, certType, expiryDate);
        certs.add(c);
        return c;
    }

    // used when CertificationApproval.approve() creates the cert directly
    public void addCertification(Certification c) { certs.add(c); }

    public Certification findCert(int id) {
        for (Certification c : certs) {
            if (c.getCertId() == id) return c;
        }
        return null; // caller must null-check
    }

    public ArrayList<Certification> getAllCertifications() { return certs; }

    // Henry uses this to get certs available for warehousing
    public ArrayList<Certification> findByStatus(String status) {
        ArrayList<Certification> result = new ArrayList<>();
        for (Certification c : certs) {
            if (c.getStatus().equals(status)) result.add(c);
        }
        return result;
    }
}
