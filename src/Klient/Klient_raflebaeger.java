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
        super(0,false); // Kalder superklassen "Raflebaeger" og opretter et raflebærger med 0 terninger
        tilføjTerning(besked); 
    }

    /**
     * Opret et tomt raflebaeger
     */
    public Klient_raflebaeger() {
        super(0,false);
    }
    
     /**
     * Kan ikke bruges af klienten!!
     */
    @Override
    @Deprecated
    public void Sorter() {
        throw new IllegalStateException("Klienten kan ikke sortere terningerne!!");
    }
    
     /**
     * Kan ikke bruges af klienten!!
     */
    @Override
    @Deprecated
    public void ryst() {
        throw new IllegalStateException("Klienten kan ikke ryste raflebærgeret!!");
    }

     /**
     * Kan ikke bruges af klienten!!
     */
    @Override
    @Deprecated
    public void fjernTerning() {
        throw new IllegalStateException("Klienten kan ikke fjerne terninger!!");
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
            super.tilføjTerning(t); // Kalder superklassen "Raflebaeger"'s tilføjTerning
        }
        
        System.out.println("Terninger i raflebaeret: "+this.toString());
    }

    
     /**
     * Kan ikke bruges af klienten!!
     */
    @Override
    @Deprecated
    public void tilføjTerning(Terning t) {
        throw new IllegalStateException("Klienten kan ikke tilføje terninge objekter. Brug tilføjTerning(String besked) i stedet!!");
    }  
}