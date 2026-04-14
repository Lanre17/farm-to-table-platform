/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.admin;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.personnel.Person;
import com.mycompany.farmtotablenetwork.personnel.Profile;
import com.mycompany.farmtotablenetwork.personnel.profiles.CertifierProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.DeliveryDriverProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.FarmerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.HarvestWorkerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InspectorProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.NetworkCoordinatorProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.ProcurementOfficerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.QualityAnalystProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.WarehouseManagerProfile;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Hank_Local
 */
public class AddEditUserPanel extends JPanel {
    private final CardSequencePanel cardPanel;
    
    //HL: reference to SystemAdminWorkArea - refreshes the JTable after a new account is created 
    private final SystemAdminWorkArea parent; 
    
    //HL: UI text fields for System Admin to edit existing user account information 
    private JTextField fieldFirst;
    private JTextField fieldLast;
    private JTextField fieldEmail;
    private JTextField fieldPhone;
    private JTextField fieldUsername;
    private JTextField fieldPassword;
    private JComboBox<String> comboRole; //HL: System Admin can assign a user account role by a combo box in UI instead of a text field. No random roles created
    private JComboBox<Organization> comboOrg;//HL: System Admin can assign a users organization by a combo box in UI instead of a text field. No random orgs created
    private JLabel errorLabel;
    
    //HL: applicable user roles 
    private static final String[] ROLES = {
        "Farmer", "Harvest Worker", "Inspector", "Certifier",
        "Warehouse Manager", "Delivery Driver",
        "Procurement Officer", "Inventory Clerk",
        "Network Coordinator", "Quality Analyst"
    };
    
    //HL: constructor 
    public AddEditUserPanel(CardSequencePanel cardPanel, SystemAdminWorkArea parent) {
        this.cardPanel = cardPanel;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI(); //HL: created method using AltEnter 
    }

    //HL: UI panel creation matching consistent design patterns for the application 
    private void buildUI() {
        //HL: NORTH - header with panel title 
        add(UIFactory.headerSimple("Add New User Account"), BorderLayout.NORTH);
        
        //HL: UI design pattern matching 
        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING * 3, UIConstants.PADDING, UIConstants.PADDING * 3));
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING,
                                            UIConstants.PADDING, UIConstants.PADDING)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        
        //HL: CENTER - person, account, & role/organization 
        //HL: PERSON DETAILS: first name, last namee, email, phone 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Person Details"), gbc);
        gbc.gridwidth = 1;
        
        fieldFirst = UIFactory.labeledField(card, gbc, "First Name ", row++);
        fieldLast  = UIFactory.labeledField(card, gbc, "Last Name ", row++);
        fieldEmail = UIFactory.labeledField(card, gbc, "Email ", row++);
        fieldPhone = UIFactory.labeledField(card, gbc, "Phone", row++);
        
        //HL: ACCOUNT DETAILS: account username & password 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Account Details"), gbc);
        gbc.gridwidth = 1;

        fieldUsername = UIFactory.labeledField(card, gbc, "Username ", row++);
        fieldPassword = UIFactory.labeledField(card, gbc, "Password ", row++);
        
        //HL: ROLE & ORGANIZATION DETAILS - combo boxes to change role/org so no random roles/orgs are generated by System Admin 
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Role & Organization"), gbc);
        gbc.gridwidth = 1;
        
        //HL: roles
        comboRole = UIFactory.labeledCombo(card, gbc, "Role ", ROLES, row++);
        
        //HL: organizations 
        Organization[] orgs = {
            ConfigureABusiness.cropMgmt,
            ConfigureABusiness.harvestAndPackaging,
            ConfigureABusiness.inspectionDept,
            ConfigureABusiness.certificationDept,
            ConfigureABusiness.warehouseOps,
            ConfigureABusiness.fleetMgmt,
            ConfigureABusiness.procurement,
            ConfigureABusiness.storefrontInventory
        };
        comboOrg = UIFactory.labeledCombo(card, gbc, "Organization ", orgs, row++);
        
        //HL: consistent UI design matching (Glue) w/ error label
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(formOuter);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);
        
        //HL: SOUTH - buttons at bottom of panel. Back button & create account button 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack = UIFactory.secondaryButton("Back");
        JButton btnSubmit = UIFactory.primaryButton("Create Account");
        btnBack.addActionListener(e -> popPanel()); //HL: created method using AltEnter
        btnSubmit.addActionListener(e -> onSubmit()); //HL: created method using AltEnter
        btnBar.add(btnBack);
        btnBar.add(btnSubmit);
        add(btnBar, BorderLayout.SOUTH);
        
        
    }

    //HL: validation method to ensure all fields/combo box selections are made before adding/saving a user account 
    private boolean validateInputs() {
        if (fieldFirst.getText().trim().isEmpty()) {
            errorLabel.setText("First name required"); return false;
        }
        if (fieldLast.getText().trim().isEmpty()) {
            errorLabel.setText("Last name required"); return false;
        }
        if (!fieldEmail.getText().trim().contains("@")) {
            errorLabel.setText("Enter a valid email address"); return false;
        }
        if (fieldUsername.getText().trim().isEmpty()) {
            errorLabel.setText("Username required"); return false;
        }
        if (fieldPassword.getText().trim().isEmpty()) {
            errorLabel.setText("Password required"); return false;
        }
        //HL: ensures System Admin cannot edit/create a username that is already being used by another user 
        if (ConfigureABusiness.accountDirectory.findAccount(fieldUsername.getText().trim()) != null) {
            errorLabel.setText("Username already exists"); return false;
        }
        if (comboOrg.getSelectedItem() == null) {
            errorLabel.setText("Select an organization"); return false;
        }
        errorLabel.setText(" ");
        return true;
    }
    
    //HL: method that only runs if all validations are successful 
    private void onSubmit() {
        if (!validateInputs()) return;
        
        Person person = new Person(fieldFirst.getText().trim(), fieldLast.getText().trim(), fieldEmail.getText().trim(), fieldPhone.getText().trim());
        Organization org = (Organization) comboOrg.getSelectedItem();
        String role = (String) comboRole.getSelectedItem();
        Profile profile = buildProfile(role, person, org);
        
        //HL: new account created in Account Directory 
        ConfigureABusiness.accountDirectory.newAccount(fieldUsername.getText().trim(), fieldPassword.getText().trim(), profile);
        
        parent.loadTable();
        popPanel();
        
    }

    //HL: method that takes the role that SysAdmin selects from combo box in UI, and instantiates the respective Profile 
    //HL: "case" maps the role to its profile - ex "Farmer" role is mapped to a new FarmerProfile  
    //HL: need so both the person & organization are passed so the new profile understands the person assigned to the new profile & the organization they're in
    private Profile buildProfile(String role, Person person, Organization org) {
        switch (role) {
            case "Farmer": return new FarmerProfile(person, org);
            case "Harvest Worker": return new HarvestWorkerProfile(person, org);
            case "Inspector": return new InspectorProfile(person, org);
            case "Certifier": return new CertifierProfile(person, org);
            case "Warehouse Manager": return new WarehouseManagerProfile(person, org);
            case "Delivery Driver": return new DeliveryDriverProfile(person, org);
            case "Procurement Officer": return new ProcurementOfficerProfile(person, org);
            case "Inventory Clerk": return new InventoryClerkProfile(person, org);
            //HL: TO-DO, UNCOMMENT after Emmanuel creates NetworkCoordinatorProfile
            case "Network Coordinator": return new NetworkCoordinatorProfile(person, org);
            case "Quality Analyst": return new QualityAnalystProfile(person, org);
            default: return new FarmerProfile(person, org);
        }
    }
    
    
    //HL: back button navigation
    private void popPanel() {
        cardPanel.popPanel(this); 
    }
    
    
}
