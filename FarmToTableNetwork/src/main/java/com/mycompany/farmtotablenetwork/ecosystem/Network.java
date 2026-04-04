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

//HL: Represents Regional Distribution Network (2nd level of hierarchy) 
//HL: container for all 4 enterprise objects (Farm/Producer, Quality & Inspection Agency, Distribution/Logistics, Retail Store/Restaurant)

public class Network {
    private static int count = 0;
    private int networkId;
    private String name;

    //HL: list of enterprise objects
    private ArrayList<Enterprise> enterprises = new ArrayList<>();

    public Network(String name) { 
        this.networkId = ++count; this.name = name; 
    }

    public void addEnterprise(Enterprise e) { 
        enterprises.add(e); 
    }

    public ArrayList<Enterprise> getEnterprises() { 
        return enterprises; 
    }

    //HL: getters
    public int getNetworkId() { 
        return networkId; 
    }

    public String getName() { 
        return name; 
    }

    //HL: method that finds enterprise by ID 
    public Enterprise findEnterprise(int id) {
        for (Enterprise e : enterprises) {
            if (e.getEnterpriseId() == id) return e;
        }
        return null; //HL: null check
    }

}
