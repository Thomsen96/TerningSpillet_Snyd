/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.util.Scanner;
import terningspillet_snyd.Terning;

/**
 *
 * @author chris
 */
public class Klient_raflebaeger extends terningspillet_snyd.Raflebaeger{
    
    /**
     * Opretter et raflebaeger der tilføjer terninger udfra "besked" strengen
     * @param besked Streng der indeholder terninger på formatet "1 2 3 4 5 6"
     */
    public Klient_raflebaeger(String besked) {
        super(0,false);
        tilføjTerning(besked);
    }

    /**
     * Opret et tomt raflebaeger
     */
    public Klient_raflebaeger() {
        super(0,false);
    }
    
    @Override
    public void Sorter() {
        System.err.println("Klienten kan ikke sortere terningerne!!");
        System.exit(0);
    }

    @Override
    public void ryst() {
        throw new IllegalStateException("Klienten kan ikke ryste raflebærgeret!!");
    }

    @Override
    public void fjernTerning() {
        System.err.println("Klienten kan ikke fjerne terninger!!");
        System.exit(0);
    }

    /**
     * Tilføjer terninger til raflebaeret udfra strengen den modtager
     * @param besked Streng der indeholder terninger
     */
    private void tilføjTerning(String besked) {

        Scanner beskedScanner = new Scanner(besked);

        System.out.println("modtag terning called; message recived:" +  besked );

        while(beskedScanner.hasNextInt()){
            Terning t = new Terning(beskedScanner.nextInt());
            super.tilføjTerning(t);
            //System.out.println("Ny terning tilføjet");
        }
        
        System.out.println("Terninger i raflebaeret: "+this.toString());
    }

    @Override
    public void tilføjTerning(Terning t) {
        System.err.println("Klienten kan ikke tilføje terninge objekter. Brug tilføjTerning(String besked) i stedet!!");
        System.exit(0);
    }
    
    
    
    
    
    
    
    
}
