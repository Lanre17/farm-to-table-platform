package com.mycompany.farmtotablenetwork.ui;

/**
 * StatusConstants — all status strings used across work requests.
 * Reference these instead of hardcoding strings in any panel.
 */
public class StatusConstants {

    // Harvest Submission (Cross-org: Crop Mgmt → Harvest & Packaging)
    public static final String PLANTED = "Planted"; // added a status for Crop
    public static final String SUBMITTED  = "Submitted";
    public static final String REVIEWED   = "Reviewed";
    public static final String APPROVED   = "Approved";
    public static final String REJECTED   = "Rejected";
    public static final String PACKAGED   = "Packaged";

    // Inspection Request (Cross-enterprise: Farm → Inspection Agency)
    public static final String ASSIGNED    = "Assigned";
    public static final String IN_PROGRESS = "In Progress";
    public static final String PASSED      = "Passed";
    public static final String FAILED      = "Failed";

    // Certification Approval (Cross-org: Inspection Dept → Certification Dept)
    public static final String PENDING_REVIEW = "Pending Review";
    public static final String DENIED         = "Denied";

    // Delivery Request (Cross-org: Warehouse → Fleet)
    public static final String REQUESTED  = "Requested";
    public static final String IN_TRANSIT = "In Transit";
    public static final String DELIVERED  = "Delivered";

    // Purchase Order (Cross-enterprise: Retail → Distribution)
    public static final String CONFIRMED  = "Confirmed";
    public static final String FULFILLING = "Fulfilling";
    public static final String SHIPPED    = "Shipped";
    public static final String RECEIVED   = "Received";

    // Shipment Receipt Confirmation (Cross-enterprise: Distribution → Retail)
    public static final String PENDING = "Pending";
    public static final String STOCKED = "Stocked";
}
