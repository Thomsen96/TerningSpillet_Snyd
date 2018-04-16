/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class TerningSpillet_Snyd {
    
    public static Runde runde;
    private static boolean debug = true;

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) {
        // TODO code application logic here
        runde = new Runde(3,6);
        runde.printTerninger();
        runde.start_rounde();
        
        runde.løgner();
        
        for (int i = 0; i < 10; i++) {
            runde.gæt((int) (Math.random()* 6 + 1),(int) (Math.random()* 6 + 1));
        }
        
        runde.løgner();
        
        for (int j = 0; j < 10; j++) {
            runde.start_rounde();
            runde.printTerninger();
            //printstats();

            for (int i = 0; i < 3; i++) {
                runde.gæt((int) (Math.random()* 6 + 1),(int) (Math.random()* 6 + 1));
            }

            runde.løgner();
        
        }
        
             

    }

    private static void printstats() {
        if(debug){
            for (int i = 1; i < 7; i++) {
                System.out.println("   Terninger    Komb");
                System.out.println(i+"'er: "+runde.getAntalØjne(i)+" \t "+runde.getKombinationer(i));
            }            
        }    
    }
    
}
