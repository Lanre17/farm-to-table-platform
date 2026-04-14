/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.admin;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.Profile;
import com.mycompany.farmtotablenetwork.personnel.UserAccount;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lanre
 */
public class EnterpriseAdminWorkArea extends JPanel {
    // Logged-in admin context and shared card navigation.

    private final Profile profile;
    private final CardSequencePanel cardPanel;

    // Member table and action controls.
    private DefaultTableModel tableModel;
    private JTable memberTable;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;

    // Column 0 stores the UserAccount object for row actions.
    private static final String[] COLUMNS = {
        "Username", "Full Name", "Role", "Organization", "Email", "Phone"
    };

    public EnterpriseAdminWorkArea(Profile profile, CardSequencePanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);

        buildUI();
        loadTable();
    }

    private void buildUI() {

        // Standard page header.
        add(UIFactory.header(
                "Enterprise Admin",
                profile.getPerson().getFullName(),
                profile.getRole()
        ), BorderLayout.NORTH);

        // Read-only member table.
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        memberTable = UIFactory.styledTable(tableModel);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.PADDING,
                UIConstants.PADDING,
                0,
                UIConstants.PADDING
        ));
        center.add(UIFactory.tableScrollPane(memberTable), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Bottom action bar.
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));

        btnAdd = UIFactory.primaryButton("+ Add Member");
        btnEdit = UIFactory.secondaryButton("Edit");
        btnDelete = UIFactory.dangerButton("Delete");
        btnRefresh = UIFactory.secondaryButton("Refresh");

        btnAdd.addActionListener(e -> pushAddMemberPanel());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());
        btnRefresh.addActionListener(e -> loadTable());

        btnBar.add(btnRefresh);
        btnBar.add(btnDelete);
        btnBar.add(btnEdit);
        btnBar.add(btnAdd);

        add(btnBar, BorderLayout.SOUTH);
    }

    public void loadTable() {
        tableModel.setRowCount(0);

        // Show only accounts belonging to the admin's enterprise.
        int myEnterpriseId = profile.getOrganization().getEnterpriseId();

        for (UserAccount ua : ConfigureABusiness.accountDirectory.getAllAccounts()) {
            if (ua == null || ua.getProfile() == null || ua.getProfile().getOrganization() == null) {
                continue;
            }

            if (ua.getProfile().getOrganization().getEnterpriseId() == myEnterpriseId) {
                tableModel.addRow(new Object[]{
                    ua,
                    ua.getProfile().getPerson().getFullName(),
                    ua.getProfile().getRole(),
                    ua.getProfile().getOrganization().getName(),
                    ua.getProfile().getPerson().getEmail(),
                    ua.getProfile().getPerson().getPhone()
                });
            }
        }
    }

    private UserAccount getSelectedAccount() {
        int row = memberTable.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return (UserAccount) tableModel.getValueAt(row, 0);
    }

    private void pushAddMemberPanel() {
        // Pushes the add-member form onto the shared card stack.
        EnterpriseAddMemberPanel panel
                = new EnterpriseAddMemberPanel(profile, cardPanel, this);
        cardPanel.pushPanel(panel);
    }

    private void onEdit() {
        UserAccount selected = getSelectedAccount();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        cardPanel.pushPanel(new EnterpriseAddMemberPanel(profile, cardPanel, this, selected));
    }

    private void onDelete() {
        UserAccount selected = getSelectedAccount();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        
        }
        
        //prevents from deleting your own account
        if (selected.getProfile() == profile) {
        JOptionPane.showMessageDialog(this, "You cannot delete your own account.", 
            "Action Not Allowed", JOptionPane.WARNING_MESSAGE);
        return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete user \"" + selected.getUsername() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            ConfigureABusiness.accountDirectory.getAllAccounts().remove(selected);
            loadTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Account deleted successfully.",
                    "Delete Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
