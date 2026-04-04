/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import java.time.LocalDate;


/**
 *
 * @author p.starobinets
 */

//HL: abstract base class for all 6 work requests
//HL: because its abstract, user cannot do new work requests, only subclasses can be instantiated 


public class WorkRequest {
    private static int count = 0; //HL: Work request ID counter shared across all instances 
    
    //HL: protected instead of private means that subclass requests (Inspection Request, etc) can read these fields w/o a getter 
    protected int requestId; //HL: unique work request ID
    protected String status; //HL: work request status string 
    protected Organization senderOrg; //HL: identifies organization that sent work request 
    protected Organization receiverOrg; //HL: identifies orgnaization that receives work request 
    protected String createdAt; //HL: date string 

    //HL: constructor 
    public WorkRequest(Organization senderOrg, Organization receiverOrg, String initialStatus) {
        this.requestId = ++count; //HL: increments work request ID, ensures there are no duplicate IDs regardless of work request type 
        this.senderOrg = senderOrg; //
        this.receiverOrg = receiverOrg;
        this.status = initialStatus;
        this.createdAt = LocalDate.now().toString();
    }

    //HL: method that updates status of work request 
    public void updateStatus(String s) { 
        this.status = s; 
    }
    
    
    //HL: getters
    public String getStatus() { 
        return status; 
    }

    public int getRequestId() { 
        return requestId; 
    }

    public Organization getSenderOrg() { 
        return senderOrg; 
    }

    public Organization getReceiverOrg() { 
        return receiverOrg; 
    }

    public String getCreatedAt() { 
        return createdAt; 
    }

    
    
    //HL: override method that will always run on subclass instance
    //HL: ensures you get the correct request type name to display in UI 
    @Override
    public String toString() {
        return getClass().getSimpleName() + " #" + requestId + " [" + status + "]";
    }

}
