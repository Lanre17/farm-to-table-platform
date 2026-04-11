/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork;

import com.github.javafaker.Faker;
import com.mycompany.farmtotablenetwork.distribution.DeliveryDirectory;
import com.mycompany.farmtotablenetwork.distribution.ShipmentReceiptConfirmationDirectory;
import com.mycompany.farmtotablenetwork.distribution.WarehouseDirectory;
import com.mycompany.farmtotablenetwork.ecosystem.Enterprise;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.farm.Crop;
import com.mycompany.farmtotablenetwork.farm.CropDirectory;
import com.mycompany.farmtotablenetwork.farm.HarvestBatch;
import com.mycompany.farmtotablenetwork.farm.HarvestBatchDirectory;
import com.mycompany.farmtotablenetwork.personnel.Person;
import com.mycompany.farmtotablenetwork.personnel.UserAccountDirectory;
import com.mycompany.farmtotablenetwork.personnel.profiles.FarmerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.HarvestWorkerProfile;
import com.mycompany.farmtotablenetwork.requests.HarvestSubmission;
import com.mycompany.farmtotablenetwork.requests.WorkRequestDirectory;
import com.mycompany.farmtotablenetwork.retail.InventoryDirectory;
import com.mycompany.farmtotablenetwork.retail.PurchaseOrderDirectory;
import com.mycompany.farmtotablenetwork.inspection.CertificationDirectory;
import com.mycompany.farmtotablenetwork.inspection.InspectionDirectory;
import com.mycompany.farmtotablenetwork.personnel.profiles.CertifierProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.DeliveryDriverProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InspectorProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.QualityAnalystProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.requests.InspectionRequest;
import com.mycompany.farmtotablenetwork.requests.CertificationApproval;


/**
 *
 * @author Lanre
 */
public class ConfigureABusiness {

    //Shared Directories
    public static UserAccountDirectory accountDirectory = new UserAccountDirectory();
    public static WorkRequestDirectory workRequestDirectory = new WorkRequestDirectory();       //ps added 4/4/26
    
    //Enterprise Directories
    //Farm & Harvest
    public static CropDirectory cropDirectory = new CropDirectory();        //ps added 4/4/26
    public static HarvestBatchDirectory batchDirectory = new HarvestBatchDirectory();       // ps added 4/4/26
    public static ShipmentReceiptConfirmationDirectory receiptDirectory = new ShipmentReceiptConfirmationDirectory();//HL: added import using AltEnter, 4/4/26
    public static WarehouseDirectory warehouseDirectory = new WarehouseDirectory(); //HL
    public static DeliveryDirectory deliveryDirectory = new DeliveryDirectory(); //HL 
    
    //Retail& Procurement
    // Shared in-memory storage for all retail purchase orders
    public static PurchaseOrderDirectory orderDirectory = new PurchaseOrderDirectory();  //LY added 4/4/26
    // Shared in-memory storage for all stocked inventory items
    public static InventoryDirectory inventoryDirectory = new InventoryDirectory(); //LY added 4/4/26
    
    public static CertificationDirectory certDirectory = new CertificationDirectory(); // EC added
    public static InspectionDirectory inspectionDirectory = new InspectionDirectory(); // EC added
    
    //Organization references
    public static Organization cropMgmt;                            //ps added 4/4/26
    public static Organization harvestAndPackaging;                 //ps added 4/4/26
    public static Organization inspectionDept;                      //ps added 4/4/26
    public static Organization certificationDept;                   //ps added 4/4/26
    public static Organization warehouseOps;                        //ps added 4/4/26
    public static Organization fleetMgmt;                           //ps added 4/4/26
    public static Organization procurement;                         //ps added 4/4/26
    public static Organization storefrontInventory;                 //ps added 4/4/26
   
       
    
    public static void configure() {
        Enterprise farmEnterprise           = new Enterprise("Farm/Producer");                    //ps added 4/4/26
        Enterprise inspectionEnterprise     = new Enterprise("Quality & Inspection Agency");      //ps added 4/4/26
        Enterprise distributionEnterprise   = new Enterprise("Distribution / Logistics Co.");     //ps added 4/4/26
        Enterprise retailEnterprise         = new Enterprise("Retail Store / Restaurant");        //ps added 4/4/26

        
        cropMgmt            = new Organization ("Crop Management", farmEnterprise.getEnterpriseId());                       //ps added 4/4/26
        harvestAndPackaging = new Organization ("Harvest & packaging", farmEnterprise.getEnterpriseId());                   //ps added 4/4/26
        inspectionDept      = new Organization("Inspection Department", inspectionEnterprise.getEnterpriseId());            //ps added 4/4/26
        certificationDept   = new Organization("Certification Department", inspectionEnterprise.getEnterpriseId());         //ps added 4/4/26
        warehouseOps        = new Organization("Warehouse Operations", distributionEnterprise.getEnterpriseId());           //ps added 4/4/26
        fleetMgmt           = new Organization("Fleet / Delivery Management", distributionEnterprise.getEnterpriseId());    //ps added 4/4/26
        procurement         = new Organization("Procurement / Purchasing", retailEnterprise.getEnterpriseId());             //ps added 4/4/26
        storefrontInventory = new Organization("Storefront / Inventory", retailEnterprise.getEnterpriseId());               //ps added 4/4/26

        
        
        seedAuth(); //HL
      //  seedFarm();    //ps added 4/4/26
        seedInspection(); // EC 
        seedDistribution(); //HL
        seedRetail(); // LY added 4/5/26
    }
    
    private static void seedAuth() {
        Faker faker = new Faker();
        
        //UserAccounts for Farm roles - ps added this block 4/4/26
        Person farmerPerson = new Person (
            faker.name().firstName(), faker.name().lastName(),
            faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        
        FarmerProfile farmerProfile = new FarmerProfile(farmerPerson, cropMgmt);
        accountDirectory.newAccount("farmer1", "password", farmerProfile);
        
        Person hwPerson = new Person (
            faker.name().firstName(), faker.name().lastName(),
            faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        
        HarvestWorkerProfile hwProfile = new HarvestWorkerProfile(hwPerson, harvestAndPackaging);
        accountDirectory.newAccount("harvest1", "password", hwProfile);
           
        // UserAccounts for Retail roles
        Person procPerson = new Person(
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());

        ProcurementOfficerProfile procProfile = new ProcurementOfficerProfile(procPerson, procurement);
        accountDirectory.newAccount("proc1", "password", procProfile);

        Person clerkPerson = new Person(
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());

        InventoryClerkProfile clerkProfile = new InventoryClerkProfile(clerkPerson, storefrontInventory);
        accountDirectory.newAccount("clerk1", "password", clerkProfile);
        
        //UserAccounts for Inspection roles 
        Person inspectorPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        InspectorProfile inspectorProfile = new InspectorProfile (inspectorPerson, inspectionDept); 
        accountDirectory.newAccount("inspector1", "password", inspectorProfile); 
        
        //UserAccounts for Certification roles 
        Person certifierPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        CertifierProfile certifierProfile = new CertifierProfile (certifierPerson, certificationDept); 
        accountDirectory.newAccount("certifier1", "password", certifierProfile); 
        
        //UserAccounts for Warehouse Manager roles 
        Person warehouseManagerPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        WarehouseManagerProfile warehouseManagerProfile = new WarehouseManagerProfile (warehouseManagerPerson, warehouseOps); 
        accountDirectory.newAccount("warehouse1", "password", warehouseManagerProfile); 
        
        //UserAccounts for Delivery Driver roles 
        Person deliveryPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        DeliveryDriverProfile deliveryDriverProfile = new DeliveryDriverProfile (deliveryPerson, fleetMgmt); 
        accountDirectory.newAccount("driver1", "password", deliveryDriverProfile); 
        
        //QA Person Profile
        Person qualityAnalystPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        QualityAnalystProfile qaProfile = new QualityAnalystProfile (qualityAnalystPerson, cropMgmt);
        accountDirectory.newAccount("qa1", "password", qaProfile);
        
        
        //Emmanuel, unstub this when you have the NetworkCoordinatorProfile ready
        
        /*
        Person networkCoordinatorPerson = new Person (
                faker.name().firstName(), faker.name().lastName(),
                faker.internet().emailAddress(), faker.phoneNumber().cellPhone());
        NetworkCoordinatorProfile networkCoordinatorProfile = new NetworkCoordinatorProfile (networkCoordinatorPerson,         InspectorProfile inspectorProfile = new InspectorProfile (inspectorPerson, inspectionDept); 
        accountDirectory.newAccount("network1", "password", networkCoordinatorProfile);
        */
        

    }
    
    
    /*private static void seedFarm(){
        
        // - Crops seeding
        Crop tomatoes =    cropDirectory.newCrop("Tomato", "2026-03-01", "Field A");  //ps added 4/4/26
        Crop lettuce = cropDirectory.newCrop("Lettuce", "2026-03-10", "Field B");       //ps added 4/4/26
        
        //Harvest Batches seeding
        HarvestBatch batch1 = batchDirectory.newBatch(tomatoes, 250.5f, "A", "Crate");   //this batch is the one inspected for the demo story   ps added 4/4/26
        
        //HarvestSubmission seeding // it's already approved so HarvestWorker can see it
        HarvestSubmission sub1 = new HarvestSubmission(tomatoes, "farmer1", 250.5f, cropMgmt, harvestAndPackaging); //placeholder code for when Emmanuel's classes are ready
        sub1.approve(); 
        
    }*/
    
    private static void seedInspection() {
    // get the tomato batch Polina seeded in seedFarm()
    HarvestBatch seedBatch = batchDirectory.findBatch(1);

    if (seedBatch != null) {
        // create an InspectionRequest that is already Passed for the demo
        InspectionRequest passedRequest = new InspectionRequest(
            seedBatch, harvestAndPackaging, inspectionDept
        );
        passedRequest.assign("inspector1");

        // using updateStatus directly instead of recordResult() to avoid
        // auto-creating a CertificationApproval — we create it manually below
        passedRequest.updateStatus(com.mycompany.farmtotablenetwork.ui.StatusConstants.PASSED);
        inspectionDirectory.addInspectionRequest(passedRequest);
        workRequestDirectory.addRequest(passedRequest);

        // create the CertificationApproval manually for this seeded path
        CertificationApproval seedApproval = new CertificationApproval(
            passedRequest, inspectionDept, certificationDept
        );

        // approve directly — creates a Certification Henry uses in seedDistribution()
        seedApproval.approve("certifier1", "Organic", "2027-01-01");
        certDirectory.addCertificationApproval(seedApproval);
        workRequestDirectory.addRequest(seedApproval);
    }
}
    
    private static void seedDistribution() {
        //HL: pull certifiecation from tomato inspection
        //HL: seedInspection() method runs first 
        com.mycompany.farmtotablenetwork.inspection.Certification tomatoCert = certDirectory.findCert(1);
        
        if (tomatoCert != null){
            //HL: WarehouseItem tomoto available for delivery 
            com.mycompany.farmtotablenetwork.distribution.WarehouseItem tomatoItem = warehouseDirectory.newItem(tomatoCert, "Tomatoes", 200, "Aisle B3");
            
            //HL: Delivery Request pre-assigned to driver1, ready to claim & deliver 
            com.mycompany.farmtotablenetwork.requests.DeliveryRequest dr = deliveryDirectory.newDelivery(tomatoItem, 1, warehouseOps, fleetMgmt);
            dr.assign("driver1");
            workRequestDirectory.addRequest(dr);
            
            //HL: shipment created with DeleiveryRequest 
            new com.mycompany.farmtotablenetwork.distribution.Shipment(dr, "Ready for pickup — Aisle B3");
        } else {
           System.out.println("[seedDistribution] WARNING: cert #1 not found — check seedInspection() ran first."); 
        }
        
        //HL: give reporting panel data upon a completed delivery
        //HL: seedFarm()method only has 1 batch (batch #1)
        //HL: create a single cert here using lettuce (seeded) as the reference crop
        com.mycompany.farmtotablenetwork.farm.Crop lettuceCrop = cropDirectory.findCrop(2);
        
        if (lettuceCrop != null){
            //HL: create luttuce HarvestBatch (no harvest submission necessary for seeding) 
            com.mycompany.farmtotablenetwork.farm.HarvestBatch lettuceBatch = batchDirectory.newBatch(lettuceCrop, 180.0f, "A", "Crate");
            
            //HL: inspectionRequest (INTENTIONALLY not added to inspectionDirectory, otherwise the request would be seen in Inspector table with no way to act on the request) 
            com.mycompany.farmtotablenetwork.requests.InspectionRequest lettuceInspection = new com.mycompany.farmtotablenetwork.requests.InspectionRequest(lettuceBatch, harvestAndPackaging, inspectionDept);
            lettuceInspection.updateStatus(com.mycompany.farmtotablenetwork.ui.StatusConstants.PASSED);
            
            com.mycompany.farmtotablenetwork.inspection.Certification lettuceCert = certDirectory.newCertification(lettuceInspection, "certifier1", "Standard", "2027-06-01");
            
            com.mycompany.farmtotablenetwork.distribution.WarehouseItem lettuceItem = warehouseDirectory.newItem(lettuceCert, "Lettuce", 150, "Aisle C1");
            
            //HL: markDelivered() automatically creaters ShipmentReceiptConfirmation in receiptDirectory 
            //HL: when the app first runs, this gives clerk1 a pending receipt to review
            com.mycompany.farmtotablenetwork.requests.DeliveryRequest completedDr = deliveryDirectory.newDelivery(lettuceItem, 2, warehouseOps, fleetMgmt);
            completedDr.assign("driver1");
            completedDr.markDelivered();
            workRequestDirectory.addRequest(completedDr);
        } else {
            System.out.println("[seedDistribution] WARNING: lettuce crop #2 not found — check seedFarm() ran first.");
        }
    }

    private static void seedRetail() {
        Faker faker = new Faker();

        // Purchase Orders seeding
        // po1: newly submitted order waiting for downstream processing
        PurchaseOrder po1 = orderDirectory.newOrder(
                "Tomatoes", 50, "Distribution Co.", "2026-04-05", procurement, warehouseOps
        );

        // po2: completed order already received and stocked
        PurchaseOrder po2 = orderDirectory.newOrder(
                "Lettuce", 30, "Distribution Co.", "2026-03-28", procurement, warehouseOps
        );

        po2.confirm();
        po2.fulfill();
        po2.markShipped();
        po2.receive();

        // po3: in-progress order to show mid-lifecycle visibility in Procurement dashboard
        PurchaseOrder po3 = orderDirectory.newOrder(
                "Cucumbers", 80, "Fresh Valley Distribution", "2026-04-10", procurement, warehouseOps
        );

        po3.confirm();
        po3.fulfill();

        // Add retail requests to shared work request directory
        workRequestDirectory.addRequest(po1);
        workRequestDirectory.addRequest(po2);
        workRequestDirectory.addRequest(po3);

        // Inventory seeding
        // item1: stocked from completed po2
        inventoryDirectory.newItem(
                po2.getRequestId(), "Lettuce", 30, "Shelf A1", "2026-04-01"
        );

        // item2: additional stocked inventory for richer dashboard/demo state
        inventoryDirectory.newItem(
                999, "Tomatoes", 120, "Cooler B2", "2026-04-03"
        );

        // item3: another stocked item to make inventory view feel more realistic
        inventoryDirectory.newItem(
                1000, "Bell Peppers", 60, "Shelf C1", "2026-04-04"
        );
       
        // end of Retail seeding block as of 4/8/26 (Updated) 
    }  
    
}
