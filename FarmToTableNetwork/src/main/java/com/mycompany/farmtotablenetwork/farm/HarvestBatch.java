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
public class HarvestBatch {
    private static int count =0; //for auto id generation
    private int batchId;
    private Crop crop;
    private float quantityKg; // Metric system FTW since it actually makes sense unlike the Empirial nonsense
    private String grade;
    private String packagingType;
    private String status;

    public HarvestBatch(Crop crop, float quantityKg, String grade, String packagingType) {
        this.batchId = ++count; //auto id generation
        this.crop = crop;
        this.quantityKg = quantityKg;
        this.grade = grade;
        this.packagingType = packagingType;
        this.status = StatusConstants.PACKAGED; // pulling statuses from the constants file
    }
    
    public void updateStatus(String s){
        this.status = s;
    }
    public void updateGrade(String g){
        this.grade = g;
    }
    public int getBatchId(){
        return batchId;
    }
    public Crop getCrop() {
        return crop;
    }
    public float getQuantityKg() {
        return quantityKg;
    }
    public String getGrade() {
        return grade;
    }
    public String getPackagingType() {
        return packagingType;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    @Override
    public String toString(){
        return crop.getType()+" batch #" + batchId + "("+grade+" grade)";
    }
     
    
}
