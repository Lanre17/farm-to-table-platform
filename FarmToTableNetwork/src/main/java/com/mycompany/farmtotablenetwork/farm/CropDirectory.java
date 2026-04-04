/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.farm;

import java.util.ArrayList;

/**
 *
 * @author p.starobinets
 */
public class CropDirectory {
    
    private ArrayList<Crop> crops = new ArrayList<>();
    
    //method to create new crop
    public Crop newCrop (String type, String plantingDate, String fieldLocation){
        Crop c = new Crop(type, plantingDate, fieldLocation);
        crops.add(c);
        return c;
    }
    
    //method to add a crop to the directory
    public void addCrop(Crop c){
        crops.add(c);
    }
    
    //method to find a crop by ID
    public Crop findCrop(int id){
        for (Crop c: crops){
            if(c.getCropId()==id)
                return c;
        }
        return null;
    }
    
    //an Array List to pull all crops
    public ArrayList<Crop> getAllCrops(){
        return crops;
    }
}
