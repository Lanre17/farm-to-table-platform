/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.inspection;

import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
import java.util.ArrayList;

/**
 *
 * @author emmanuelcroll
 */
// stores all InspectionRequests — used by InspectorWorkArea to load the requests table
public class InspectionDirectory {

    private ArrayList<InspectionRequest> requests = new ArrayList<>();

    public void addInspectionRequest(InspectionRequest r) { requests.add(r); }

    public InspectionRequest findRequest(int id) {
        for (InspectionRequest r : requests) {
            if (r.getRequestId() == id) return r;
        }
        return null; // caller must null-check
    }

    public ArrayList<InspectionRequest> getAllRequests() { return requests; }

    public ArrayList<InspectionRequest> findByStatus(String status) {
        ArrayList<InspectionRequest> result = new ArrayList<>();
        for (InspectionRequest r : requests) {
            if (r.getStatus().equals(status)) result.add(r);
        }
        return result;
    }
    
    // shortcut for loading the open requests table in InspectorWorkArea
    public ArrayList<InspectionRequest> getOpenRequests() {
        return findByStatus(StatusConstants.SUBMITTED);
    }

    // filters the table to show only requests assigned to the logged-in inspector
    public ArrayList<InspectionRequest> findByAssignedInspector(String username) {
        ArrayList<InspectionRequest> result = new ArrayList<>();
        for (InspectionRequest r : requests) {
            if (username.equals(r.getInspector())) result.add(r);
        }
        return result;
    }
}
