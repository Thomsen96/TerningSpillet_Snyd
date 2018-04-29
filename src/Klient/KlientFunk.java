/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import Klient.SpillerNetværk;
import Klient.Klient_raflebaeger;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import terningspillet_snyd.Terning;

/**
 *
 * @author chris
 */
public class KlientFunk {

    private static SpillerNetværk spiller;
    private static String state = "Tilslut_spil";
    public static Klient_raflebaeger baerger;
    public static int antal_terninger_ialt = 0;
            
    /**
     * Kontruktør der opretter et objekt af SpillerNetværk og parser portnavn og navn videre til den. Derudover opretter den også et tomt raflebaeger.
     * @param portnavn Portnavnet
     * @param navn Spiller navnet klienten vil bruge
     */
    public KlientFunk(int portnavn, String navn) {
        spiller = new SpillerNetværk(portnavn, navn);
        
        // Opretter et tomt raflebaerger så der ikke opstår null-pointer exception, hvis der forsøges tilgået raflebærger før det er modtaget
        baerger = new Klient_raflebaeger();   
        
        //System.out.println(spiller.modtag());
        //spiller.send("Hello server");

    }

    /**
     * Returner tilstandsmaskinens tilstand
     * @return state
     */
    public static String getState() {
        return state;
    }

    /**
     * Angiv tilstanden som man ønsker at sætte tilstandsmaskinen i
     * @param state Tilstanden
     */
    public static void setState(String state) {
        KlientFunk.state = state;
    }
    
    /**
     * Modtager kommandoer over netværket via SpillerNetværk objektet og udfra tilstandsmaskines
     * tilstand kan kommandoerne udfører funktioner.
     * @return String Strengen indeholder beskeder der skal udskrives til brugeren
     */
    String modtagKommando() {
        String streng;
        try {
            streng = spiller.modtag();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "\null";
        }

        if(streng == null || streng.matches("error")){
            System.out.println("Der modtages null eller \"error\".");
            return "\null";
        }
        if (streng.startsWith("msg:")) {
                    streng = streng.substring(4, streng.length());
                    System.out.println("Modtog en besked fra server: \""+streng+"\".");
                    return streng;
        }else if(streng.matches("ctr:kick")){
            try {
                spiller.lukforbindelse();
                streng = "Du er blevet kicket!";
                System.out.println(""+streng);
                return streng;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        switch (state) {
            case "Tilslut_spil":
                if(streng.matches("ctr:accepteret")){
                    state = "Vent_på_spil";
                    System.out.println("Går fra Tilslut_spil til Vent_på_spil");
                    return "\null";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Tilslut_spil\".");
                    return "\null";
                }
                
            case "Vent_på_spil":
                if(streng.matches("ctr:initier spil")){
                    state = "Initier_spil";
                    System.out.println("Går fra Vent_på_spil til Initier_spil");
                    return "\null";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Vent_på_spil\".");
                    return "\null";
                }                
            case "Initier_spil":
                if(streng.matches("ctr:initier runde")){
                    state = "Initier_runde";
                    System.out.println("Går fra Initier_spil til Initier_runde");
                    return "\null";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Initier_spil\".");
                    return "\null";
                }
            case "Initier_runde":
                if(streng.matches("ctr:start runde")){
                    state = "Ikke_tur";
                    System.out.println("Går fra Initier_runde til Ikke_tur");
                    return "\null";
                }else if(streng.startsWith("ctr:tern") && streng.endsWith("]")){ // Modtag klientens terninger
                    System.out.println("Står i Initier_runde og modtager terninger: "+streng);
                    streng = streng.substring(8, streng.length()); // Fjerner tekst-karakterne
                    baerger = spiller.modtagTerninger(streng);
                    baerger.toString();
                    return "\null";
                
                }else if(streng.startsWith("ctr:antaltern")){ // Modtag total antal terninger
                    System.out.println("Står i Initier_runde og modtager total antal terninger..");
                    streng = streng.trim().replace(" ", "");
                    streng = streng.substring(13, streng.length()); // Fjerner "ctr:antaltern"
                    Scanner beskedScanner = new Scanner(streng);
                    if(beskedScanner.hasNextInt()){
                        antal_terninger_ialt = beskedScanner.nextInt();
                    }else{
                        System.err.println("Besked scanner læste ikke en int! Så der er: "+antal_terninger_ialt+" terninger registeret!");
                        return "\null";
                    }
                    System.out.println("Der er "+streng+" antal terninger i alt.");
                    return "Der er "+streng+" antal terninger i alt";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Initier_runde\".: "+streng);
                    return "\null";
                }
                
            case "Ikke_tur":
                if(streng.matches("ctr:tur")){
                    state = "Tur";
                    System.out.println("Går fra Ikke_tur til Tur");
                    return "\null";
                }else if(streng.matches("ctr:gaet ikke accepteret")){
                    state = "Tur_ikke_accepteret";
                    System.out.println("Dit gæt blev ikke accepteret!");
                    return "\null";
                }else if(streng.matches("ctr:runde slut")){
                    state = "Runde_slut";
                    System.out.println("Går fra Ikke_tur til Runde_slut");
                    return "\null";                    
                }else if(streng.matches("ctr:gaet accepteret")){
                    System.out.println("Dit gæt blev accepteret og turen skifter til en anden (Bliver i Ikke_tur)");
                    return "\null";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Ikke_tur\".: "+streng);
                    return "\null";
                }  
            case "Tur":
                if(streng.matches("ctr:gaet accepteret")){
                    state = "Ikke_tur";
                    System.out.println("Dit gæt blev accepteret og turen skifter til en anden (Går fra Tur til Ikke_tur)");
                    return "\null";
                }else if(streng.matches("ctr:runde slut")){
                    state = "Runde_slut";
                    System.out.println("Går fra Tur til Runde_slut");
                    return "\null";                    
                }else{
                    System.out.println("Ugyldig kommando fra state \"Tur\".");
                    return "\null";
                }
            case "Tur_ikke_accepteret":
                if(streng.matches("ctr:tur")){
                    state = "Tur";
                    System.out.println("Går fra Tur_ikke_accepteret til Tur");
                    return "\null";
                }
            case "Runde_slut":
                if(streng.matches("ctr:initier runde")){
                    state = "Initier_runde";
                    System.out.println("Går fra Runde_slut til Initier_runde");
                    return "\null";
                }else if(streng.matches("ctr:spil slut")){
                    state = "Spil_slut";
                    System.out.println("Går fra Runde_slut til Spil_slut");
                    return "\null";                    
                }else{
                    System.out.println("Ugyldig kommando fra state \"Runde_slut\".");
                    return "\null";
                }    
            case "Spil_slut":
                if(streng.matches("ctr:tilslut spil")){
                    state = "Tilslut_spil";
                    System.out.println("Går fra Spil_slut til Tilslut_spil");
                    return "\null";
                }else{
                    System.out.println("Ugyldig kommando fra state \"Spil_slut\".");
                    return "\null";
                } 
            default:
                System.out.println("Default casen kører, noget galt! Her er state: \""+state+"\".");
                return "\null";
                    
        }

    }

    public boolean Forbundet() {
        return spiller.getisConnected();
    }

    void sendKommando(String streng) {
        if (streng.startsWith("Guess(") && streng.endsWith(")")) {
            System.out.println("Sender \""+streng+"\" til serveren");
            spiller.send(streng);
        }else if(streng.matches("Liar!")){
            System.out.println("Sender \""+streng+"\" til serveren");
            spiller.send(streng);            
        }
        state = "Ikke_tur";
    }

    int[] getTerninger() {
        int[] Terninger = new int[6];
        
        for (int i = 0; i < baerger.terninger.size(); i++) {
            Terninger[i] = baerger.terninger.get(i).getVærdi();
        }
        return Terninger;
    }


}
