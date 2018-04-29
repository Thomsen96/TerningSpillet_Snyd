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
    
    public Klient_raflebaeger(String besked) {
        super(0,false);
        tilføjTerning(besked);
    }
    public Klient_raflebaeger() {
        super(0,false);
    }
    
    @Override
    public void Sorter() {
        System.err.println("Klienten kan ikke sortere terningerne!!");
    }

    @Override
    public void ryst() {
        System.err.println("Klienten kan ikke ryste raflebærgeret!!");
    }

    @Override
    public void fjernTerning() {
        System.err.println("Klienten kan ikke fjerne terninger!!");
    }

    public void tilføjTerning(String besked) {

        Scanner beskedScanner = new Scanner(besked);

        System.out.println("modtag terning called; message recived:" +  besked );

        // if the first number is message, first int read is integer
        while(beskedScanner.hasNextInt()){
            Terning t = new Terning(beskedScanner.nextInt());
            this.tilføjTerning(t);
            //System.out.println("Ny terning tilføjet");
        }
        
        for (Terning terning : terninger) {
            System.out.println(" "+terning.getVærdi());
        }
    }
    
    
    
    
    
    
}
