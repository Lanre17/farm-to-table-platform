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
public class Network {
     private static int count = 0;

    private int networkId;

    private String name;

    private ArrayList<Enterprise> enterprises = new ArrayList<>();

    public Network(String name) { this.networkId = ++count; this.name = name; }

    public void addEnterprise(Enterprise e) { enterprises.add(e); }

    public ArrayList<Enterprise> getEnterprises() { return enterprises; }

    public int    getNetworkId() { return networkId; }

    public String getName()      { return name; }

    public Enterprise findEnterprise(int id) {

        for (Enterprise e : enterprises) {

            if (e.getEnterpriseId() == id) return e;

        }

        return null;

    }

}
