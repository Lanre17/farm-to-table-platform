/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.farm;
import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.farm.*;
import com.mycompany.farmtotablenetwork.personnel.profiles.FarmerProfile;
import com.mycompany.farmtotablenetwork.ui.*;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author p.starobinets
 */
public class FarmerWorkArea  extends JPanel {
    private final FarmerProfile profile;
    private final CardSequencePanel cardPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnNewCrop;
    private JButton btnSubmitHarvest;
    private static final String[] COLUMNS ={
        "Crop ID", "Type", "Field Location", "Planting Date", "Status"
        // Col 0 is Crop object stored for row-click retrieval. this is what @Override to String is for in the Crop.java
    };
    public FarmerWorkArea(FarmerProfile profile, CardSequencePanel cardPanel){
        this.profile = profile;
        this.cardPanel = cardPanel;
        
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadTable();
    }

    private void buildUI() {
    // NORTH
        add(UIFactory.header("Crop Management",
                profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);

    // CENTER — table
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = UIFactory.styledTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                btnSubmitHarvest.setEnabled(table.getSelectedRow() >= 0);
            }
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIConstants.BG_APP);
        center.setBorder(BorderFactory.createEmptyBorder(
            UIConstants.PADDING, UIConstants.PADDING, 0, UIConstants.PADDING));
        center.add(UIFactory.tableScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

// SOUTH — buttons
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));
        btnNewCrop       = UIFactory.primaryButton("+ New Crop");
        btnSubmitHarvest = UIFactory.primaryButton("Submit Harvest");
        btnSubmitHarvest.setEnabled(false);

        //stubbed out until panels are ready - ps 4/4/26
        btnNewCrop.addActionListener(e-> pushNewCropPanel());
        btnSubmitHarvest.addActionListener(e-> pushSubmitHarvestPanel());

        btnBar.add(btnSubmitHarvest);
        btnBar.add(btnNewCrop);
        add(btnBar, BorderLayout.SOUTH);

    }

    public void loadTable() {
        tableModel.setRowCount(0);
        for(Crop c: ConfigureABusiness.cropDirectory.getAllCrops()){
            tableModel.addRow(new Object[]{
                c,
                c.getType(),
                c.getFieldLocation(),
                c.getPlantingDate(),
                c.getStatus()
            });
        }
    }
    
    private Crop getSelectedCrop(){
        int row = table.getSelectedRow();
        if(row <0) 
            return null;
        return (Crop)tableModel.getValueAt(row, 0);
    }
    
         
    private void pushNewCropPanel() {
        cardPanel.pushPanel(new NewCropPanel(cardPanel, this));
    }

    private void pushSubmitHarvestPanel() {
        Crop selected = getSelectedCrop();
        if(selected == null)
            return;
        cardPanel.pushPanel(new SubmitHarvestPanel(selected, cardPanel, this));
    }
    
    public String getName() {
        return profile.getPerson().getFullName();
    }


  
}
