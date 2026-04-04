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
     private ArrayList<WorkRequest> requests = new ArrayList<>();

    public void addRequest(WorkRequest r)     { requests.add(r); }

    public ArrayList<WorkRequest> getAllRequests() { return requests; }

    public ArrayList<WorkRequest> findByStatus(String status) {

        ArrayList<WorkRequest> result = new ArrayList<>();

        for (WorkRequest r : requests) {

            if (r.getStatus().equals(status)) result.add(r);

        }

        return result;

    }

    public ArrayList<WorkRequest> findByOrg(Organization org) {

        ArrayList<WorkRequest> result = new ArrayList<>();

        for (WorkRequest r : requests) {

            if (r.getSenderOrg() == org || r.getReceiverOrg() == org) result.add(r);

        }

        return result;

    }

    public WorkRequest findById(int id) {

        for (WorkRequest r : requests) {

            if (r.getRequestId() == id) return r;

        }

        return null;

    }

}
