/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ecosystem;

/**
 *
 * @author Hank_Local
 */
public class Ecosystem {
    private static int count = 0; //HL: ecosystem ID, will always be 1 
    private int ecosystemId;
    private String name; //HL: ecosystem name, "Farm-to-Table Network"
    private Network network; //HL: single network in the ecosystem 

    public Ecosystem(String name, Network network) {
        this.ecosystemId = ++count;
        this.name    = name;
        this.network = network;

    }

    //HL: getters 
    public Network getNetwork() { 
        return network; 
    }
    public String getName() { 
        return name; 
    }

    public int getEcosystemId() { 
        return ecosystemId; 
    }

}
