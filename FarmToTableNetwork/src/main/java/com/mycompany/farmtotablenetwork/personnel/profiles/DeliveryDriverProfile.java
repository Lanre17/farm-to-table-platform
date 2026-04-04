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
 * @author Hank_Local
 */
public class DeliveryDriverProfile extends Profile { //HL: added Person import using AltEnter
    public DeliveryDriverProfile(Person person, Organization organization){ //HL: added Person & Organization imports using AltEnter 
        super(person, "Delivery Driver", organization); 
    }
    
}
