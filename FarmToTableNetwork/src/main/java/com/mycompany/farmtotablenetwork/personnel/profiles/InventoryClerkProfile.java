/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel.profiles;

import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.personnel.Person;
import com.mycompany.farmtotablenetwork.personnel.Profile;

/**
 *
 * @author Lanre
 */
public class InventoryClerkProfile extends Profile {
    // Retail role for receiving deliveries and stocking inventory
    public InventoryClerkProfile(Person person, Organization organization) {
        super(person, "Inventory Clerk", organization);
    }
}
