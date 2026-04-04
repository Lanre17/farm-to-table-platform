/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
/**
 *
 * @author p.starobinets
 */
public class Profile {
    private static int count = 0;
    protected int profileId;
    protected Person person;
    protected String role;
    protected Organization organization;

    public Profile(Person person, String role, Organization organization) {
        this.profileId    = ++count; //generates auto ids
        this.person       = person;
        this.role         = role;
        this.organization = organization;
    }
    public int          getProfileId()    { return profileId; }
    public Person       getPerson()       { return person; }
    public String       getRole()         { return role; }
    public Organization getOrganization() { return organization; }

    @Override

    public String toString() {
        return role + ": " + person.getFullName();
    }
}
