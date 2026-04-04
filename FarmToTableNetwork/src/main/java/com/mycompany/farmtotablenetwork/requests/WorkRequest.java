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
public class WorkRequest {
    private static int count = 0;

    protected int requestId;

    protected String status;

    protected Organization senderOrg;

    protected Organization receiverOrg;

    protected String createdAt;

    public WorkRequest(Organization senderOrg, Organization receiverOrg, String initialStatus) {

        this.requestId   = ++count;

        this.senderOrg   = senderOrg;

        this.receiverOrg = receiverOrg;

        this.status      = initialStatus;

        this.createdAt   = LocalDate.now().toString();

    }

    public void   updateStatus(String s) { this.status = s; }

    public String getStatus()            { return status; }

    public int    getRequestId()         { return requestId; }

    public Organization getSenderOrg()   { return senderOrg; }

    public Organization getReceiverOrg() { return receiverOrg; }

    public String getCreatedAt()         { return createdAt; }

    @Override

    public String toString() {

        return getClass().getSimpleName() + " #" + requestId + " [" + status + "]";

    }

}
