/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.requests;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;

/**
 *
 * @author Lanre
 */
public class PurchaseOrder extends WorkRequest {
    private String productName;
    private int qty;           // whole units only
    private String distributor;
    private String requestedDate; // YYYY-MM-DD

    public PurchaseOrder(String productName, int qty, String distributor,
            String requestedDate,
            Organization senderOrg, Organization receiverOrg) {

        super(senderOrg, receiverOrg, StatusConstants.SUBMITTED);

        this.productName = productName;
        this.qty = qty;
        this.distributor = distributor;
        this.requestedDate = requestedDate;
    }

    // ── Status transitions ─────────────────────────────
    public void confirm() {
        updateStatus(StatusConstants.CONFIRMED);
    }

    public void fulfill() {
        updateStatus(StatusConstants.FULFILLING);
    }

    public void markShipped() {
        updateStatus(StatusConstants.SHIPPED);
    }

    public void receive() {
        updateStatus(StatusConstants.RECEIVED);
    }

    // ── Getters ────────────────────────────────────────
    public String getProductName() {
        return productName;
    }

    public int getQty() {
        return qty;
    }

    public String getDistributor() {
        return distributor;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    @Override
    public String toString() {
        return "PurchaseOrder #" + getRequestId()
                + " — " + productName + " x" + qty
                + " [" + getStatus() + "]";
    }
}
