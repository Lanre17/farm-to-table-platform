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
    private static int count = 0;

    private int ecosystemId;

    private String name;

    private Network network;

    public Ecosystem(String name, Network network) {

        this.ecosystemId = ++count;

        this.name    = name;

        this.network = network;

    }

    public Network getNetwork() { return network; }

    public String  getName()    { return name; }

    public int     getEcosystemId() { return ecosystemId; }

}
