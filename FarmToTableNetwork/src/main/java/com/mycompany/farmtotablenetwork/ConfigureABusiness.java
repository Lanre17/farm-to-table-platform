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
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.requests.PurchaseOrder;
import com.mycompany.farmtotablenetwork.retail.InventoryItem;


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
        seedFarm();    //ps added 4/4/26
        //seedInsp
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

    }
    
    
    private static void seedFarm(){
        
        // - Crops seeding
        Crop tomatoes =    cropDirectory.newCrop("Tomato", "2026-03-01", "Field A");  //ps added 4/4/26
        Crop lettuce = cropDirectory.newCrop("Lettuce", "2026-03-10", "Field B");       //ps added 4/4/26
        
        //Harvest Batches seeding
        HarvestBatch batch1 = batchDirectory.newBatch(tomatoes, 250.5f, "A", "Crate");   //this batch is the one inspected for the demo story   ps added 4/4/26
        
        //HarvestSubmission seeding // it's already approved so HarvestWorker can see it
        HarvestSubmission sub1 = new HarvestSubmission(tomatoes, "farmer1", 250.5f, cropMgmt, harvestAndPackaging); //placeholder code for when Emmanuel's classes are ready
        sub1.approve(); 
        
    }
    
    private static void seedDistribution() {
        
    }

    private static void seedRetail() {
        Faker faker = new Faker();

        // Purchase Orders seeding
        PurchaseOrder po1 = orderDirectory.newOrder(
                "Tomatoes", 50, "Distribution Co.", "2026-04-05", procurement, warehouseOps);

        PurchaseOrder po2 = orderDirectory.newOrder(
                "Lettuce", 30, "Distribution Co.", "2026-03-28", procurement, warehouseOps);

        // move one order through the full lifecycle for demo/reporting
        po2.confirm();
        po2.fulfill();
        po2.markShipped();
        po2.receive();

        // add retail requests to shared work request directory
        workRequestDirectory.addRequest(po1);
        workRequestDirectory.addRequest(po2);

        // Inventory seeding
        InventoryItem item1 = inventoryDirectory.newItem(
                po2.getRequestId(), "Lettuce", 30, "Shelf A1", "2026-04-01");

        
        // end of Retail seeding block as of 4/5/26
    }

    

    
}
