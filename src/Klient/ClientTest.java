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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        KlientFunk klient;
        klient = new KlientFunk(8998,"Mads");
        
        JTabbedPane faneblade = new JTabbedPane();
        
        Velkomstskærm Velkomstskærm = new Velkomstskærm();
        Spil_skærm Spil_skærm = new Spil_skærm();
        Grafikdemo Grafikdemo = new Grafikdemo();
        
        faneblade.add("Spillet", Spil_skærm);
        faneblade.add("Start", Velkomstskærm);
        
        Spil_skærm.setlogik(klient);
        
        
        JFrame vindue = new JFrame("Snyd");
        vindue.add( faneblade );
        vindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // reagér på luk
        vindue.pack();                  // lad vinduet selv bestemme sin størrelse
        vindue.setVisible(true);

        int antal = 1;
        int værdi = 2;
        
        
                
        
        
        while(klient.Forbundet()){
            String msg = klient.modtagKommando();            
            
            if(!msg.matches("\null") && !msg.startsWith("ctr:")){
                Spil_skærm.tilføjText_til_tekstboks(msg);
            }else if(msg.matches("ctr:start runde")){
                Spil_skærm.tegnTerninger(klient.baerger.antalTerninger());
                //Spil_skærm.sætAntalTerningerIAlt();
                System.out.println("Sender antal terninger til GUI: "+klient.baerger.antalTerninger());
            }
                
            if(klient.getState() == "Tur"){
                Spil_skærm.visknapper();
                /*
                antal = læsINTtastatur();
                
                if(antal == -1){
                    klient.sendKommando("Liar!");
                }else{
                    værdi = læsINTtastatur();
                    klient.sendKommando("Guess("+antal+","+værdi+")");
                }
                */
                
                
                /*
                if(antal == 11 && værdi == 6){
                    klient.sendKommando("Liar!");
                }else{
                    antal++;
                    klient.sendKommando("Guess("+antal+","+værdi+")");
                    if(antal >= 12){
                        værdi++;
                        antal=1;
                    }                    
                }
                */
            }else if(klient.getState() == "Ikke_tur"){
                Spil_skærm.skjulknapper();
            }
        }

        
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
