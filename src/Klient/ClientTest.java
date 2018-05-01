/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonListener;

/**
 *
 * @author john
 */
public class ClientTest {
    
   static Scanner tastatur = new Scanner(System.in);
   static int antal_terninger;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        KlientFunk klient = null;
        
        JTabbedPane faneblade = new JTabbedPane();
        
        Velkomstskærm Velkomstskærm = new Velkomstskærm();
        Spil_skærm Spil_skærm = new Spil_skærm();
        //Grafikdemo Grafikdemo = new Grafikdemo();
        
        faneblade.add("Start", Velkomstskærm);

        
        
        JFrame vindue = new JFrame("Snyd");
        vindue.add( faneblade );
        vindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // reagér på luk
        vindue.pack();                  // lad vinduet selv bestemme sin størrelse
        vindue.setVisible(true);
        
        
        while(Velkomstskærm.start == 0){
            try {
                Thread.sleep(1000);
                //System.out.println("Start="+Velkomstskærm.start);
            } catch (InterruptedException ex) {
                
            }
        }
        
        String navn = Velkomstskærm.getnavn();
        int port = Velkomstskærm.getport();
        String IP = Velkomstskærm.getIP();
        
        klient = new KlientFunk(port,navn,IP);
        //klient = new KlientFunk(8998,"John");
        faneblade.add("Spillet", Spil_skærm);
        
        Spil_skærm.setlogik(klient);
        faneblade.setSelectedIndex(1); // Sætter siden til spillet
        faneblade.remove(Velkomstskærm);
        
        String slut_besked = "";
        while(klient.Forbundet()){
            String msg = klient.modtagKommando();            
            
            if(!msg.matches("\null") && !msg.startsWith("ctr:")){
                Spil_skærm.tilføjText_til_tekstboks(msg);
                if(msg.endsWith("omgang!")){
                    slut_besked = msg;
                }
                
            }else if(msg.matches("ctr:start runde")){
                Spil_skærm.tegnTerninger(klient.baerger.antalTerninger());
                Spil_skærm.sætAntalTerningerIAlt(klient.antal_terninger_ialt);
                System.out.println("Sender antal terninger til GUI: "+klient.baerger.antalTerninger());
            }
                
            if("Tur".equals(klient.getState())){
                Spil_skærm.visknapper();
            }else if("Ikke_tur".equals(klient.getState())){
                Spil_skærm.skjulknapper();
            }
        }
       String taber = "testperson";
       javax.swing.JOptionPane.showMessageDialog(vindue, ""+slut_besked);
       vindue.setVisible(false);
       System.exit(1);
        
        
    }
     /**
     * Funktioner der aflæser tastaturet med exception handeling
     */
    public static int læsINTtastatur() {
        int læstint = 0;

        try {
            læstint = tastatur.nextInt();
        } catch (Exception e) {
            tastatur.nextLine(); // Læs linjeskift
            return -1; // Returner et ugyldt menu valg
        }

        tastatur.nextLine(); // Læs linjeskift
        return læstint;
    }
    
}
