/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel;

/**
 *
 * @author p.starobinets
 */
public class Person {
    private static int count = 0;
    private int personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Person(String firstName, String lastName, String email, String phone) {
        this.personId = ++count;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.phone     = phone;
    }

    public int    getPersonId()    { return personId; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public String getPhone()       { return phone; }
    public String getFullName()    { return firstName + " " + lastName; }
    public void   setEmail(String e) { this.email = e; }
    public void   setPhone(String p) { this.phone = p; }

    @Override

    public String toString() { return getFullName() + " <" + email + ">"; }

    
}
