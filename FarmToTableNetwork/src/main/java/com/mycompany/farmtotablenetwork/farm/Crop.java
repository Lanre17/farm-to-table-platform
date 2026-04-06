/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.farm;
import com.mycompany.farmtotablenetwork.ui.StatusConstants;
/**
 *
 * @author p.starobinets
 */
public class Crop {
    private static int count = 0;
    private int cropId;
    private String type;
    private String plantingDate; //YYYY-MM-DD We are using strings for dates for this demo story because of the time constraints
    private String fieldLocation;
    private String status;
    
    public Crop (String type, String plantingDate, String fieldLocation){
        this.cropId = ++count; //for auto item id generation
        this.type = type;
        this.plantingDate = plantingDate;
        this.fieldLocation = fieldLocation;
        this.status = StatusConstants.PLANTED; // All statuses that are used across the ecosystem are kept in a reusable file for consistent implementation
    }
    public void updateStatus(String s){
        this.status = s;
    }
    public int getCropId(){
        return cropId;
    }
    public String getType(){
        return type;
    }
    public String getPlantingDate() {
        return plantingDate;
    }
    public String getFieldLocation() {
        return fieldLocation;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    @Override
    public String toString(){
        return type + "(Crop #" + cropId + ")";}
    }
    

