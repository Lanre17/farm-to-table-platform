package com.mycompany.farmtotablenetwork.distribution;

/**
 * STUB — placeholder so DeliveryRequest.java compiles while waiting for
 * Emmanuel's Certification.java. Replace this entire file with the real
 * implementation once Certification.java and CertificationDirectory.java
 * are pushed.
 *
 * @author Hank_Local
 */
public class WarehouseItem {

    // Minimal fields needed by DeliveryRequest and its toString()
    private static int count = 0;
    private int itemId;
    private String productName;
    private int qty;
    private String location;
    private String status;

    // Stub constructor — no Certification parameter yet
    public WarehouseItem(String productName, int qty, String location) {
        this.itemId      = ++count;
        this.productName = productName;
        this.qty         = qty;
        this.location    = location;
        this.status      = "Warehoused";
    }

    public int    getItemId()      { return itemId; }
    public String getProductName() { return productName; }
    public int    getQty()         { return qty; }
    public String getLocation()    { return location; }
    public String getStatus()      { return status; }

    public void updateStatus(String s) { this.status = s; }

    @Override
    public String toString() {
        return productName + " (Item #" + itemId + ", qty: " + qty + ") [STUB]";
    }
}
