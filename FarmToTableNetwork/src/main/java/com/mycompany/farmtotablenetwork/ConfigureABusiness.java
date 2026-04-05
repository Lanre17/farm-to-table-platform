/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork;

import com.github.javafaker.Faker;
import com.mycompany.farmtotablenetwork.distribution.ShipmentReceiptConfirmationDirectory;
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

/**
 *
 * @author Lanre
 */
public class ConfigureABusiness {

    //Shared Directories
    public static UserAccountDirectory accountDirectory = new UserAccountDirectory();
    public static WorkRequestDirectory workRequestDirectory = new WorkRequestDirectory();       //ps added 4/4/26
    
    //Enterprise Directories
    public static CropDirectory cropDirectory = new CropDirectory();        //ps added 4/4/26
    public static HarvestBatchDirectory batchDirectory = new HarvestBatchDirectory();       // ps added 4/4/26
    public static ShipmentReceiptConfirmationDirectory receiptDirectory = new ShipmentReceiptConfirmationDirectory(); //HL: added import using AltEnter, 4/4/26
    
    //Organization references
    public static Organization cropMgmt;                            //ps added 4/4/26
    public static Organization harvestAndPackaging;                 //ps added 4/4/26
    public static Organization inspectionDept;                       //ps added 4/4/26
    public static Organization certificationDept;                   //ps added 4/4/26
    public static Organization warehouseOps;                        //ps added 4/4/26
    public static Organization fleetMgmt;                           //ps added 4/4/26
    public static Organization procurement;                         //ps added 4/4/26
    public static Organization storefrontInventory;                 //ps added 4/4/26
       
    
    public static void configure() {
        Enterprise farmEnterprise = new Enterprise("Farm/Producer");                            //ps added 4/4/26
        Enterprise inspectionEnterprise   = new Enterprise("Quality & Inspection Agency");      //ps added 4/4/26
        Enterprise distributionEnterprise = new Enterprise("Distribution / Logistics Co.");     //ps added 4/4/26
        Enterprise retailEnterprise       = new Enterprise("Retail Store / Restaurant");        //ps added 4/4/26

        
        cropMgmt = new Organization ("Crop Management", farmEnterprise.getEnterpriseId());                              //ps added 4/4/26
        harvestAndPackaging = new Organization ("Harvest & packaging", farmEnterprise.getEnterpriseId());               //ps added 4/4/26
        inspectionDept = new Organization("Inspection Department", inspectionEnterprise.getEnterpriseId());             //ps added 4/4/26
        certificationDept = new Organization("Certification Department", inspectionEnterprise.getEnterpriseId());       //ps added 4/4/26
        warehouseOps         = new Organization("Warehouse Operations", distributionEnterprise.getEnterpriseId());      //ps added 4/4/26
        fleetMgmt            = new Organization("Fleet / Delivery Management", distributionEnterprise.getEnterpriseId());//ps added 4/4/26
        procurement          = new Organization("Procurement / Purchasing", retailEnterprise.getEnterpriseId());        //ps added 4/4/26
        storefrontInventory  = new Organization("Storefront / Inventory", retailEnterprise.getEnterpriseId());          //ps added 4/4/26

        
        
        //seedAuth
        seedFarm();    //ps added 4/4/26
        //seedInsp
        //seedLogistics
        //seerRetail
    }
    private static void seedFarm(){
        Faker faker = new Faker();
        
        // - Crops seeding
        Crop tomatoes =    cropDirectory.newCrop("Tomato", "2026-03-01", "Field A");  //ps added 4/4/26
        Crop lettuce = cropDirectory.newCrop("Lettuce", "2026-03-10", "Field B");       //ps added 4/4/26
        
        //Harvest Batches seeding
        HarvestBatch batch1 = batchDirectory.newBatch(tomatoes, 250.5f, "A", "Crate");   //this batch is the one inspected for the demo story   ps added 4/4/26
        
        //HarvestSubmission seeding // it's already approved so HarvestWorker can see it
        HarvestSubmission sub1 = new HarvestSubmission(tomatoes, "farmer1", 250.5f, cropMgmt, harvestAndPackaging); //placeholder code for when Emmanuel's classes are ready
        sub1.approve(); 
        
        
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
        //end of Farm & Harvest seeding block as of 4/4/26
        
    }
}
