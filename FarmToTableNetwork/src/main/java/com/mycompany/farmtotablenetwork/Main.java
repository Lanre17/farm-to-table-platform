/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.farmtotablenetwork;

import javax.swing.SwingUtilities;
import com.mycompany.farmtotablenetwork.ui.main.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author p.starobinets
 */
public class Main {
    public static void main(String[] args) {
        ConfigureABusiness.configure();
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(ConfigureABusiness.accountDirectory);
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
