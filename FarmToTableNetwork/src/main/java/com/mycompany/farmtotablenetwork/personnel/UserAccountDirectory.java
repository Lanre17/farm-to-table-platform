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
public class UserAccountDirectory {
    private ArrayList<UserAccount> accounts = new ArrayList<>();
    public UserAccount newAccount(String username, String password, Profile profile) {

        UserAccount ua = new UserAccount(username, password, profile);
        accounts.add(ua);
        return ua;
    }

    public UserAccount authenticate(String username, String password) {
        for (UserAccount a : accounts) {
            if (a.authenticate(username, password)) return a;
        }
        return null; // LoginPanel must null-check this
    }

    public UserAccount findAccount(String username) {
        for (UserAccount a : accounts) {
            if (a.getUsername().equals(username)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<UserAccount> getAllAccounts() {
        return accounts;
    }

}
