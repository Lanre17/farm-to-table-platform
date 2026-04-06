/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmtotablenetwork.ui.main;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author p.starobinets
 */
public class CardSequencePanel extends JPanel {
 
    public CardSequencePanel() {
         setLayout(new CardLayout());
     }
    
   //method to add a panel aka go forward
    public void pushPanel(JPanel panel) {
            String name = String.valueOf(System.nanoTime());
            this.add(panel, name);
            ((CardLayout) this.getLayout()).show(this, name);
            this.revalidate();
            this.repaint();
        }
    
    //method to remove a panel aka go back
     public void popPanel(JPanel panel) {
        this.remove(panel);
        ((CardLayout) this.getLayout()).next(this);
    }


}

