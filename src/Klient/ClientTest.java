/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.util.Scanner;

/**
 *
 * @author john
 */
public class ClientTest {
    
   static Scanner tastatur = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        KlientFunk klient;
        klient = new KlientFunk(8998,"Mark");
        int antal = 1;
        int værdi = 2;
        
        
        
        while(klient.Forbundet()){
            klient.modtagKommando();
            if(klient.getState() == "Tur"){
                antal = læsINTtastatur();
                værdi = læsINTtastatur();
                
                if(antal != -1){
                    klient.sendKommando("Guess("+antal+","+værdi+")");
                }else{
                    klient.sendKommando("Liar!");
                }
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
