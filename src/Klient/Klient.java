/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author chris
 */
public class Klient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JTabbedPane faneblade = new JTabbedPane();
        
        Velkomstskærm Velkomstskærm = new Velkomstskærm();
        Spil_skærm Spil_skærm = new Spil_skærm();
        Grafikdemo Grafikdemo = new Grafikdemo();
        
        faneblade.add("Spillet", Spil_skærm);
        faneblade.add("Start", Velkomstskærm);
        
        
        JFrame vindue = new JFrame("Snyd");
        vindue.add( faneblade );
        vindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // reagér på luk
        vindue.pack();                  // lad vinduet selv bestemme sin størrelse
        vindue.setVisible(true);      
        
        /*
        JFrame vindue = new JFrame("Snyd");
        Velkomstskærm Velkomstskærm = new Velkomstskærm();
        Spil_skærm Spil_skærm = new Spil_skærm();
        vindue.add(Velkomstskærm);
        vindue.add(Spil_skærm);
        
        vindue.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE); // reagér på luk
	vindue.setSize(600,500);                      // sæt vinduets størrelse
	vindue.setVisible(true);                      // åbn vinduet
        */
    }
    
}
