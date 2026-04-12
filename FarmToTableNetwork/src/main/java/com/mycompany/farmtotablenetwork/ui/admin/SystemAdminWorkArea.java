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
 * @author Hank_Local
 */
public class SystemAdminWorkArea extends JPanel {
    private final Profile profile;
    private final CardSequencePanel cardPanel;
    
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnAdd;
    private JButton btnDelete;
    
    //HL: Column 0 - stores UserAccount when user clicks on row 
    //HL: defines & establishes 5 columns in JTable 
    private static final String[] COLUMNS = { 
        "Account", "Username", "Role", "Full Name", "Email"
    }; 
    
    //HL: constructor 
    public SystemAdminWorkArea(Profile profile, CardSequencePanel cardPanel) {
        this.profile = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout()); //HL: added import using AltEnter
        setBackground(UIConstants.BG_APP); //HL: added import using AltEnter
        buildUI(); //HL: created method using AltEnter
        loadTable(); //HL: created method using AltEnter
    }

    private void buildUI() {
        
        //HL: NORTH - adds panel header w/ title
        add(UIFactory.header("System Administration — All Accounts", profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);
        
        //HL: makes info in JTable non-editable (handling user account edits/additions in AddEditUserPanel) 
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UIFactory.styledTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnDelete.setEnabled(table.getSelectedRow() >= 0);
            }
        });
        
        //HL: UI design for consistent user experience 
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        
        //HL: SOUTH - Add/Delete buttons at bottom of panel 
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));
        
        btnDelete = UIFactory.dangerButton("Delete Account");
        btnAdd    = UIFactory.primaryButton("+ Add Account");
        btnDelete.setEnabled(false);

        btnDelete.addActionListener(e -> onDelete()); //HL: created method using AltEnter
        btnAdd.addActionListener(e -> pushAddPanel()); //HL: created method using AltEnter

        btnBar.add(btnDelete);
        btnBar.add(btnAdd);
        add(btnBar, BorderLayout.SOUTH);
        
    }

    //HL: method that loads JTable in SystemAdminWorkArea UI - displays account, username, role, full name, & email address
    private void loadTable() {
        tableModel.setRowCount(0);
        for (UserAccount a : ConfigureABusiness.accountDirectory.getAllAccounts()) {
            tableModel.addRow(new Object[]{
                a,
                a.getUsername(),
                a.getProfile().getRole(),
                a.getProfile().getPerson().getFullName(),
                a.getProfile().getPerson().getEmail()
            });
        }
        
    }
    
    //HL: user account getter 
    private UserAccount getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return (UserAccount) tableModel.getValueAt(row, 0);
    }

    //HL: method that deletes user accounts w/ confirmation check 
    private void onDelete() {
        UserAccount selected = getSelected();
        if (selected == null) return;

        //HL: asks System Admin if they actually want to delete a selected account from the JTable or not 
        int confirm = JOptionPane.showConfirmDialog(this, "Delete account \"" + selected.getUsername() + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            ConfigureABusiness.accountDirectory.getAllAccounts().remove(selected);
            loadTable();
        }
    }

    private void pushAddPanel() {
        
    }
    
}
