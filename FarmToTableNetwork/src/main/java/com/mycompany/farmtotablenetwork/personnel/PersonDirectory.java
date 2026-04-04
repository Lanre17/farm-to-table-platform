/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel;

import java.util.ArrayList;

/**
 *
 * @author p.starobinets
 */
public class PersonDirectory {
    private ArrayList<Person> persons = new ArrayList<>();
    
    public Person newPerson(String fn, String ln, String email, String phone) { 
        Person p = new Person(fn,ln,email,phone); 
        persons.add(p); 
        return p; 
    }
    
}
