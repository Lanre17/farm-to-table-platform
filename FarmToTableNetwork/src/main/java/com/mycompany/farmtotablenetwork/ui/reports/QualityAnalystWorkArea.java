/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.reports;

import com.mycompany.farmtotablenetwork.ConfigureABusiness;
import com.mycompany.farmtotablenetwork.personnel.profiles.QualityAnalystProfile;
import com.mycompany.farmtotablenetwork.reporting.NetworkSummary;
import com.mycompany.farmtotablenetwork.ui.UIConstants;
import com.mycompany.farmtotablenetwork.ui.UIFactory;
import com.mycompany.farmtotablenetwork.ui.main.CardSequencePanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author p.starobinets
 */
public class QualityAnalystWorkArea extends JPanel {
    private final QualityAnalystProfile profile;
    private final CardSequencePanel cardPanel;
    
    // ── Batch status table 
    private DefaultTableModel batchTableModel;
    private JTable batchTable;
    
    //Open Requests Table
    private DefaultTableModel requestsTableModel;
    private JTable requestsTable;
    
    //Summary stat labels
    private JLabel passRateLabel;
    private JLabel warehouseLabel;
    private JLabel ordersLabel;
    
    public QualityAnalystWorkArea( QualityAnalystProfile profile, CardSequencePanel cardPanel){
        this.profile = profile;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BG_APP);
        buildUI();
        loadSummary();
    }

    private void buildUI() {
        //----------NORTH---------(Header)
        add(UIFactory.header("Analytics Dashboard", profile.getPerson().getFullName(), profile.getRole()), BorderLayout.NORTH);
        
        
        //---------CENTER-------- (scrollable dashboard)
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.Y_AXIS));
        dashboard.setBackground(UIConstants.BG_APP);
        dashboard.setBorder(BorderFactory.createEmptyBorder(
        
            UIConstants.PADDING, UIConstants.PADDING,
            UIConstants.PADDING, UIConstants.PADDING));
        
        //----------KPI card row--------------
        JPanel kpiRow = new JPanel(new GridLayout(1,3,UIConstants.PADDING,0));
        kpiRow.setBackground(UIConstants.BG_APP);
        kpiRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        kpiRow.add(buildKpiCard("Inspection Pass Rate", buildPassRateLabel()));
        kpiRow.add(buildKpiCard("Items in Warehouse",   buildWarehouseLabel()));
        kpiRow.add(buildKpiCard("Orders Received",      buildOrdersLabel()));
        
        dashboard.add(kpiRow);
        dashboard.add(Box.createVerticalStrut(UIConstants.PADDING));
        
        
        //-------Batch counts table------
        dashboard.add(buildSectionLabel("Harvest Batches by Status"));
        dashboard.add(Box.createVerticalStrut(4));
        batchTableModel = new DefaultTableModel(
            new String[]{"Status", "Count"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        batchTable = UIFactory.styledTable(batchTableModel);
        batchTable.setPreferredScrollableViewportSize(new Dimension(0, 120));
        JScrollPane batchScroll = UIFactory.tableScrollPane(batchTable);
        batchScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        dashboard.add(batchScroll);
        dashboard.add(Box.createVerticalStrut(UIConstants.PADDING));
        
        //------Open requests by Type table
        dashboard.add(buildSectionLabel("Open Work Requests by Type"));
        dashboard.add(Box.createVerticalStrut(4));
        requestsTableModel = new DefaultTableModel(
            new String[]{"Request Type", "Open Count"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        requestsTable = UIFactory.styledTable(requestsTableModel);
        requestsTable.setPreferredScrollableViewportSize(new Dimension(0, 150));
        JScrollPane requestsScroll = UIFactory.tableScrollPane(requestsTable);
        requestsScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        dashboard.add(requestsScroll);
        JScrollPane outerScroll = new JScrollPane(dashboard);

        outerScroll.setBorder(null);
        outerScroll.getViewport().setBackground(UIConstants.BG_APP);
        add(outerScroll, BorderLayout.CENTER);
        
        //---------SOUTH----------------
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, UIConstants.PADDING));
        btnBar.setBackground(UIConstants.BG_APP);
        btnBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER_LIGHT));
        JButton btnRefresh = UIFactory.secondaryButton("↻ Refresh");
        btnRefresh.addActionListener(e -> loadSummary());
        btnBar.add(btnRefresh);
        add(btnBar, BorderLayout.SOUTH);
        }
    
        //-------Helper methods for building the KPI cards
    private JLabel buildPassRateLabel() {
        passRateLabel = new JLabel("—");
        passRateLabel.setFont(UIConstants.FONT_HEADER_TITLE);
        passRateLabel.setForeground(UIConstants.TEXT_PRIMARY);
        passRateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return passRateLabel;
    }

    private JLabel buildWarehouseLabel() {
        warehouseLabel = new JLabel("—");
        warehouseLabel.setFont(UIConstants.FONT_HEADER_TITLE);
        warehouseLabel.setForeground(UIConstants.TEXT_PRIMARY);
        warehouseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return warehouseLabel;
    }

    private JLabel buildOrdersLabel() {
        ordersLabel = new JLabel("—");
        ordersLabel.setFont(UIConstants.FONT_HEADER_TITLE);
        ordersLabel.setForeground(UIConstants.TEXT_PRIMARY);
        ordersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return ordersLabel;
    }

    private JPanel buildKpiCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIConstants.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_SECTION_LABEL);
        titleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel,  BorderLayout.NORTH);
        card.add(valueLabel,  BorderLayout.CENTER);
        return card;
    }

    private JLabel buildSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIConstants.FONT_SECTION_LABEL);
        lbl.setForeground(UIConstants.TEXT_SECONDARY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
    
    //-------Re-reads all analytics from NetworkSummary and refreshes every widget.----
    private void loadSummary() {
        NetworkSummary summary = new NetworkSummary(
            ConfigureABusiness.batchDirectory,
            ConfigureABusiness.inspectionDirectory,
            ConfigureABusiness.warehouseDirectory,
            ConfigureABusiness.orderDirectory,
            ConfigureABusiness.workRequestDirectory
        );
        
        // --------KPI Labels----------
        /*
        float rate = summary.certPassFailRate();
        passRateLabel.setText(String.format("%.0f%%", rate));
        warehouseLabel.setText(String.valueOf(summary.totalWarehouseItems()));
        ordersLabel.setText(String.valueOf(summary.totalOrdersReceived()));
        */
        
        //------Batch counts table------
        batchTableModel.setRowCount(0);
        HashMap<String, Integer> batchCounts = summary.batchCountByStatus();
        for (Map.Entry<String, Integer> entry : batchCounts.entrySet()) {
            batchTableModel.addRow(new Object[]{ entry.getKey(), entry.getValue() });
        }
        
        //------Open Requests Table
        
        requestsTableModel.setRowCount(0);
        HashMap<String, Integer> openCounts = summary.openRequestsByType();
        for (Map.Entry<String, Integer> entry : openCounts.entrySet()) {
            requestsTableModel.addRow(new Object[]{ entry.getKey(), entry.getValue() });
        }

    }

}
