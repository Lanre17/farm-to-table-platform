/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ecosystem;

/**
 *
 * @author p.starobinets
 */
public class Organization {
    private static int count = 0; //generates unique ID
    private int orgId;
    private String name;
    private int enterpriseId;

    public Organization(String name, int enterpriseId) {
        this.orgId = ++count;
        this.name = name;
        this.enterpriseId = enterpriseId;
    }

    public int getOrgId(){
        return orgId; 
    }
    public String getName(){ 
        return name; 
    }
    public int getEnterpriseId(){ 
        return enterpriseId; 
    }

    @Override
    public String toString()   { return name + " (Org #" + orgId + ")"; }
  
}
