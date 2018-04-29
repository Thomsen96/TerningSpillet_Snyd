/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import Klient.Klient_raflebaeger;

/**
 *
 * @author chris
 */
public class KlientFunk {

    private static SpillerNetværk spiller;
    private static String state = "Tilslut_spil";
    public static Klient.Klient_raflebaeger baerger;
            

    public KlientFunk(int portnavn, String navn) {
        spiller = new SpillerNetværk(portnavn, navn);
        baerger = new Klient_raflebaeger(); // Opretter et tomt raflebaerger så der ikke opstår null-pointer exception, hvis der forsøges tilgået raflebærger før det er modtaget
                
        //System.out.println(spiller.modtag());
        //spiller.send("Hello server");

    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        KlientFunk.state = state;
    }

    String modtagKommando() {
        String streng = spiller.modtag();
                                    // && streng.endsWith("\n")
        if (streng.startsWith("msg:")) {
                    streng = streng.substring(4, streng.length());
                    System.out.println("Modtog en besked fra server: \""+streng+"\".");
                    return streng;
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
                }else if(streng.startsWith("ctr:tern")){
                streng = streng.substring(7, streng.length());
                baerger = spiller.modtagTerninger(streng);
                
                }else if(streng.startsWith("ctr:antaltern")){
                
                
                }else{
                    System.out.println("Ugyldig kommando fra state \"Initier_runde\".");
                    return "\null";
                }
            case "Ikke_tur":
                if(streng.matches("ctr:tur")){
                    state = "Tur";
                    System.out.println("Går fra Ikke_tur til Tur");
                    return "\null";
                }else if(streng.matches("ctr:runde slut")){
                    state = "Runde_slut";
                    System.out.println("Går fra Ikke_tur til Runde_slut");
                    return "\null";                    
                }else{
                    System.out.println("Ugyldig kommando fra state \"Ikke_tur\".");
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

    boolean Forbundet() {
        return spiller.getisConnected();
    }


}
