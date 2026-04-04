/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import java.util.ArrayList;


/**
 *
 * @author Hank_Local
 */
public class WorkRequestDirectory {
    
    //HL: master list of work requests (all types) 
    private ArrayList<WorkRequest> requests = new ArrayList<>();
     
    //HL: adds work request to list, accepts all 6 types or work requests w/o needed separate lists
    public void addRequest(WorkRequest r) { 
        requests.add(r); 
    }
     
    //HL: method that gets the list of all requests
    public ArrayList<WorkRequest> getAllRequests() { 
        return requests; 
    }

    //HL: method that allows list filtering to return requests by a specific status (ex. Submitted, In Progress, Complete) 
    public ArrayList<WorkRequest> findByStatus(String status) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest r : requests) {
            if (r.getStatus().equals(status)) result.add(r);
        }
        return result;
    }

    //HL: method that allows list filtering to return requests by a specic org (ex. crop mgmt, certification dept, fleet/deliver mgmt, procurement/purchasing) 
    public ArrayList<WorkRequest> findByOrg(Organization org) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest r : requests) {
            if (r.getSenderOrg() == org || r.getReceiverOrg() == org) result.add(r);
        }
        return result;
    }

    //HL: method that finds a specific work request by its ID number, 
    public WorkRequest findById(int id) {
        for (WorkRequest r : requests) {
            if (r.getRequestId() == id) return r;
        }
        //HL: null check if searching for an ID that doesn't exist
        return null;
    }

}
