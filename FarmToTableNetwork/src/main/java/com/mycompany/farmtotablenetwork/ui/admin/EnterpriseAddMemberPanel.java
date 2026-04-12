/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.admin;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.ecosystem.Organization;
import com.mycompany.farmtotablenetwork.personnel.Person;
import com.mycompany.farmtotablenetwork.personnel.Profile;
import com.mycompany.farmtotablenetwork.personnel.UserAccount;
import com.mycompany.farmtotablenetwork.personnel.profiles.CertifierProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.DeliveryDriverProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.FarmerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.HarvestWorkerProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InspectorProfile;
import com.mycompany.farmtotablenetwork.personnel.profiles.InventoryClerkProfile;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Lanre
 */
class EnterpriseAddMemberPanel extends JPanel {
    // Logged-in enterprise admin context and parent panel reference.
    private final Profile profile;
    private final CardSequencePanel cardPanel;
    private final EnterpriseAdminWorkArea parent;
    private final UserAccount editingAccount; // null = add mode, non-null = edit mode

    // Form fields.
    private JTextField fieldFirst;
    private JTextField fieldLast;
    private JTextField fieldEmail;
    private JTextField fieldPhone;
    private JTextField fieldUsername;
    private JTextField fieldPassword;
    private JComboBox<String> comboRole;
    private JComboBox<Organization> comboOrg;
    private JLabel errorLabel;

    // Add mode constructor.
    public EnterpriseAddMemberPanel(Profile profile,
            CardSequencePanel cardPanel,
            EnterpriseAdminWorkArea parent) {
        this(profile, cardPanel, parent, null);
    }

    // Edit mode constructor.
    public EnterpriseAddMemberPanel(Profile profile,
            CardSequencePanel cardPanel,
            EnterpriseAdminWorkArea parent,
            UserAccount editingAccount) {
        this.profile = profile;
        this.cardPanel = cardPanel;
        this.parent = parent;
        this.editingAccount = editingAccount;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadOrganizations();
        loadRoles();

        if (isEditMode()) {
            populateFieldsForEdit();
        }
    }

    private boolean isEditMode() {
        return editingAccount != null;
    }

    private void buildUI() {

        // Top header for the form panel.
        add(UIFactory.headerSimple(isEditMode() ? "Edit Enterprise Member" : "Add Enterprise Member"),
                BorderLayout.NORTH);

        JPanel formOuter = new JPanel(new BorderLayout());
        formOuter.setBackground(UIConstants.BG_APP);
        formOuter.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING * 3,
                UIConstants.PADDING,
                UIConstants.PADDING * 3
        ));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(
                        UIConstants.PADDING,
                        UIConstants.PADDING,
                        UIConstants.PADDING,
                        UIConstants.PADDING
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Person details section.
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(UIFactory.sectionDivider("Person Details"), gbc);

        gbc.gridwidth = 1;
        fieldFirst = UIFactory.labeledField(card, gbc, "First Name *", row++);
        fieldLast = UIFactory.labeledField(card, gbc, "Last Name *", row++);
        fieldEmail = UIFactory.labeledField(card, gbc, "Email *", row++);
        fieldPhone = UIFactory.labeledField(card, gbc, "Phone", row++);

        // Account details section.
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Account Details"), gbc);

        gbc.gridwidth = 1;
        fieldUsername = UIFactory.labeledField(card, gbc, "Username *", row++);
        fieldPassword = UIFactory.labeledField(card, gbc,
                isEditMode() ? "New Password (optional)" : "Password *", row++);

        // Role and organization section.
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        card.add(UIFactory.sectionDivider("Role & Organization"), gbc);

        gbc.gridwidth = 1;
        comboRole = UIFactory.labeledCombo(card, gbc, "Role *", new String[]{}, row++);
        comboOrg = UIFactory.labeledCombo(card, gbc, "Organization *", new Organization[]{}, row++);

        // Spacer + error label.
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        card.add(Box.createVerticalGlue(), gbc);

        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        errorLabel = UIFactory.errorLabel();
        card.add(errorLabel, gbc);

        formOuter.add(card, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(formOuter);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConstants.BG_APP);
        add(scroll, BorderLayout.CENTER);

        // Bottom buttons.
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        JButton btnBack = UIFactory.secondaryButton("Back");
        JButton btnSubmit = UIFactory.primaryButton(isEditMode() ? "Save Changes" : "Create Member");

        btnBack.addActionListener(e -> cardPanel.popPanel(this));
        btnSubmit.addActionListener(e -> onSubmit());

        btnBar.add(btnBack);
        btnBar.add(btnSubmit);

        add(btnBar, BorderLayout.SOUTH);
    }

    private void populateFieldsForEdit() {
        fieldFirst.setText(editingAccount.getProfile().getPerson().getFirstName());
        fieldLast.setText(editingAccount.getProfile().getPerson().getLastName());
        fieldEmail.setText(editingAccount.getProfile().getPerson().getEmail());
        fieldPhone.setText(editingAccount.getProfile().getPerson().getPhone());
        fieldUsername.setText(editingAccount.getUsername());
        fieldPassword.setText("");
        comboRole.setSelectedItem(editingAccount.getProfile().getRole());
        comboOrg.setSelectedItem(editingAccount.getProfile().getOrganization());

        // Lock fields that are not safe to change with current model.
        fieldFirst.setEditable(false);
        fieldLast.setEditable(false);
        fieldUsername.setEditable(false);
        comboRole.setEnabled(false);
        comboOrg.setEnabled(false);
    }

    private void loadOrganizations() {
        comboOrg.removeAllItems();

        int myEnterpriseId = profile.getOrganization().getEnterpriseId();

        Organization[] allOrgs = {
            ConfigureABusiness.cropMgmt,
            ConfigureABusiness.harvestAndPackaging,
            ConfigureABusiness.inspectionDept,
            ConfigureABusiness.certificationDept,
            ConfigureABusiness.warehouseOps,
            ConfigureABusiness.fleetMgmt,
            ConfigureABusiness.procurement,
            ConfigureABusiness.storefrontInventory
        };

        for (Organization org : allOrgs) {
            if (org != null && org.getEnterpriseId() == myEnterpriseId) {
                comboOrg.addItem(org);
            }
        }
    }

    private void loadRoles() {
        comboRole.removeAllItems();

        int myEnterpriseId = profile.getOrganization().getEnterpriseId();

        if (ConfigureABusiness.cropMgmt != null
                && myEnterpriseId == ConfigureABusiness.cropMgmt.getEnterpriseId()) {
            comboRole.addItem("Farmer");
            comboRole.addItem("Harvest Worker");
            comboRole.addItem("Quality Analyst");
        } else if (ConfigureABusiness.inspectionDept != null
                && myEnterpriseId == ConfigureABusiness.inspectionDept.getEnterpriseId()) {
            comboRole.addItem("Inspector");
            comboRole.addItem("Certifier");
        } else if (ConfigureABusiness.warehouseOps != null
                && myEnterpriseId == ConfigureABusiness.warehouseOps.getEnterpriseId()) {
            comboRole.addItem("Warehouse Manager");
            comboRole.addItem("Delivery Driver");
        } else if (ConfigureABusiness.procurement != null
                && myEnterpriseId == ConfigureABusiness.procurement.getEnterpriseId()) {
            comboRole.addItem("Procurement Officer");
            comboRole.addItem("Inventory Clerk");
        }
    }

    private boolean validateInputs() {
        String first = fieldFirst.getText().trim();
        String last = fieldLast.getText().trim();
        String email = fieldEmail.getText().trim();
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();

        if (first.isEmpty()) {
            errorLabel.setText("First name is required.");
            return false;
        }
        if (!first.matches("[A-Za-z][A-Za-z\\-' ]*")) {
            errorLabel.setText("First name must contain letters only.");
            return false;
        }

        if (last.isEmpty()) {
            errorLabel.setText("Last name is required.");
            return false;
        }
        if (!last.matches("[A-Za-z][A-Za-z\\-' ]*")) {
            errorLabel.setText("Last name must contain letters only.");
            return false;
        }

        if (email.isEmpty() || !email.contains("@")) {
            errorLabel.setText("Enter a valid email address.");
            return false;
        }

        if (username.isEmpty()) {
            errorLabel.setText("Username is required.");
            return false;
        }

        if (!isEditMode()) {
            if (password.isEmpty()) {
                errorLabel.setText("Password is required.");
                return false;
            }

            if (ConfigureABusiness.accountDirectory.findAccount(username) != null) {
                errorLabel.setText("Username already exists.");
                return false;
            }
        }

        if (comboRole.getSelectedItem() == null) {
            errorLabel.setText("Please select a role.");
            return false;
        }

        if (comboOrg.getSelectedItem() == null) {
            errorLabel.setText("Please select an organization.");
            return false;
        }

        errorLabel.setText(" ");
        return true;
    }

    private void onSubmit() {
        if (!validateInputs()) {
            return;
        }

        if (isEditMode()) {
            editingAccount.getProfile().getPerson().setEmail(fieldEmail.getText().trim());
            editingAccount.getProfile().getPerson().setPhone(fieldPhone.getText().trim());

            if (!fieldPassword.getText().trim().isEmpty()) {
                editingAccount.updatePassword(fieldPassword.getText().trim());
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Member updated successfully.",
                    "Update Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            Person person = new Person(
                    fieldFirst.getText().trim(),
                    fieldLast.getText().trim(),
                    fieldEmail.getText().trim(),
                    fieldPhone.getText().trim()
            );

            String role = (String) comboRole.getSelectedItem();
            Organization org = (Organization) comboOrg.getSelectedItem();

            Profile newProfile = buildProfile(role, person, org);

            ConfigureABusiness.accountDirectory.newAccount(
                    fieldUsername.getText().trim(),
                    fieldPassword.getText().trim(),
                    newProfile
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Member added successfully.",
                    "Create Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        parent.loadTable();
        cardPanel.popPanel(this);
    }

    private Profile buildProfile(String role, Person person, Organization org) {
        switch (role) {
            case "Farmer":
                return new FarmerProfile(person, org);
            case "Harvest Worker":
                return new HarvestWorkerProfile(person, org);
            case "Inspector":
                return new InspectorProfile(person, org);
            case "Certifier":
                return new CertifierProfile(person, org);
            case "Warehouse Manager":
                return new WarehouseManagerProfile(person, org);
            case "Delivery Driver":
                return new DeliveryDriverProfile(person, org);
            case "Procurement Officer":
                return new ProcurementOfficerProfile(person, org);
            case "Inventory Clerk":
                return new InventoryClerkProfile(person, org);
            case "Quality Analyst":
                return new QualityAnalystProfile(person, org);
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
