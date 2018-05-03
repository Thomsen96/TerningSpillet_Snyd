/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author john
 */
public class ClientTest {
    
   static Scanner tastatur = new Scanner(System.in);
   static int antal_terninger;
   static boolean running = true;
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
        //Denne kode kommer fra Jakob Falk
//        faneblade.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
//                protected int calculateTapAreaHight(int t, int h, int m){
//                    return 0;
//                }
//            }
//        );
        
        while(Velkomstskærm.start == 0 && vindue.isShowing()){
            try {
                Thread.sleep(1000);
                //System.out.println("Start="+Velkomstskærm.start);
            } catch (InterruptedException ex) {
                
            }
        }
        if (!vindue.isShowing()){
            System.exit(0);
        }
        String navn = Velkomstskærm.getnavn();
        int port = Velkomstskærm.getport();
        String IP = Velkomstskærm.getIP();
        
        klient = new KlientFunk(port,navn,IP);
        //klient = new KlientFunk(8998,"John");
        faneblade.add("Spillet", Spil_skærm);
        
        Spil_skærm.setlogik(klient);
        faneblade.setSelectedIndex(0); // Sætter siden til spillet
        faneblade.remove(Velkomstskærm);
        
        String slut_besked = "Fejl!";
        while(klient.Forbundet() && vindue.isShowing()){
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
                //Spil_skærm.skjulknapper(); // Implementeret i Spil_skærm
            }
        }
       String taber = "testperson";
       if (!vindue.isShowing()){
            System.exit(0);
       }
       javax.swing.JOptionPane.showMessageDialog(vindue, ""+slut_besked);
       vindue.setVisible(false);
       System.exit(0);
        
        
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
