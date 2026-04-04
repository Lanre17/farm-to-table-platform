/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ecosystem;

import java.util.ArrayList;

/**
 *
 * @author Hank_Local
 */
public class Enterprise {
    private static int count = 0;
    private int enterpriseId;
    private String name;

    private ArrayList<Organization> organizations = new ArrayList<>();

    public Enterprise(String name) {
        this.enterpriseId = ++count;
        this.name = name;
    }

    //HL: method that adds organizations to the enterprise (called in ConfigureABusiness) 
    public void addOrganization(Organization o) { 
        organizations.add(o); 
    }

    //HL: method that returns lists of organizations in the enterprise 
    public ArrayList<Organization> getOrganizations() { 
        return organizations; 
    }

    //HL: getters - oranization stores enterpriseId so org knows which enterprise it is in 
    public int getEnterpriseId() { 
        return enterpriseId; 
    }

    public String getName() { 
        return name; 
    }

    //HL: method that finds organizations in the enterprise by orgId
    //HL: used by admin panels to search org by ID 
    public Organization findOrganization(int id) {
        for (Organization o : organizations) {
            if (o.getOrgId() == id) return o;
        }
        //HL: null check if orgId not found 
        return null;
    }

    //HL: displays enetrprise name in UI tables/combo boxes
    @Override 
    public String toString() { 
        return name; 
    }

}
