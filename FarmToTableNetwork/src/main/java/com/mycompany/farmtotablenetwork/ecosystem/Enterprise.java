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

    public void addOrganization(Organization o) { organizations.add(o); }

    public ArrayList<Organization> getOrganizations() { return organizations; }

    public int    getEnterpriseId() { return enterpriseId; }

    public String getName()         { return name; }

    public Organization findOrganization(int id) {

        for (Organization o : organizations) {

            if (o.getOrgId() == id) return o;

        }

        return null;

    }

    @Override public String toString() { return name; }

}
