/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.personnel;

/**
 *
 * @author p.starobinets
 */
public class UserAccount {
    private String username;
    private String password;
    private Profile profile;

    public UserAccount(String username, String password, Profile profile) {
        this.username = username;
        this.password = password;
        this.profile  = profile;
    }

    public boolean authenticate(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public String  getUsername()          { return username; }
    public Profile getProfile()           { return profile; }
    public void    updatePassword(String newPass) { this.password = newPass; }

    @Override

    public String toString() { return username + " [" + profile.getRole() + "]"; }

    
}
