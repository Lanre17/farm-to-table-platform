/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel.profiles;

import com.mycompany.farmtotablenetwork.personnel.Person;
import com.mycompany.farmtotablenetwork.personnel.Profile;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
/**
 *
 * @author emmanuelcroll
 */


// Network Coordinator role profile — used by MainFrame instanceof routing
public class NetworkCoordinatorProfile extends Profile {
    public NetworkCoordinatorProfile(Person person, Organization organization) {
        super(person, "Network Coordinator", organization);
    }
}
