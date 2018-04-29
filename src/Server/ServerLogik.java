/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Spil;
import static Server.Spil_tilstandsmaskine.Spil;

/**
 *
 * @author john
 */
public class ServerLogik {
    public static Spil spilLogik;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 8998;
        int antalSpillere = 2;
        int antalTerninger = 6;
        
        ServerFunk logik = new ServerFunk(port);
        spilLogik = new Spil(antalSpillere, antalTerninger);
        
        logik.modtagForbindelse(antalSpillere);
        logik.initierSpil();
        logik.initierRunde(raflebærgre, antalTerninger);
        
        while(!spilLogik.getSpil_status().equals("spil slut")){
           switch(spilLogik.getSpil_status()) {
                case "start":
                    spilLogik.printTerninger(0); // Print terninger
                    spilLogik.start_rounde(); // Start runden
                    printstats(); // Viser alle kombi
                    break;
                case "spil":
                    System.out.println("Følgende kommandoer er tilladt:Guess(#,#), MyDices(#), AllDices! eller Liar!");
                    læsCommandov2("");
                    break;
                case "runde_slut":
                    spilLogik.printTerninger(0); // Print terninger
                    spilLogik.start_rounde(); // Start en ny runde
                    printstats(); // Viser alle kombi
                    break; 
                case "spil_slut":
                    System.out.println("Spillet er færdigt!");
                    return; // Afslut spil                
                default:
                    System.out.println("Fejl, ugyldig tilstand");
                    return;    
            }    
        }
    }
    
    
    
    
    /**
     * Behandler en komando fra spille til spillogikken
     * @param streng 
     */
    private static void læsCommandov2(String streng) {
        if(streng.startsWith("Guess(") && streng.endsWith(")")){
            streng = streng.substring(6);
            int antal = 0;
            int værdi = 0;

            try {
                String antal_string = streng.substring(0,streng.indexOf(","));

                for (int i = 0; i < antal_string.length(); i++) {
                    if(antal_string.charAt(i) > 57 || antal_string.charAt(i) < 48){
                        antal = -1;
                        i = antal_string.length();
                    }else
                        antal += ((int) antal_string.charAt(i)-48)* (int) Math.pow(10, antal_string.length()-1-i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                antal = 0;
            }

            try {
                String værdi_string = streng.substring(streng.indexOf(",")+1,streng.indexOf(")"));
                for (int i = 0; i < værdi_string.length(); i++) {
                    if(værdi_string.charAt(i) > 57 || værdi_string.charAt(i) < 48){
                        værdi = -1;
                        i = værdi_string.length();
                    }else
                        værdi += ((int) værdi_string.charAt(i)-48)* (int) Math.pow(10, værdi_string.length()-1-i);
                }                                
            } catch (Exception e) {
                e.printStackTrace();
                værdi = 0;
            }

            spilLogik.gæt(værdi,antal);
            
        }else if(streng.matches("Liar!")){
            spilLogik.løgner();
        }else if(streng.startsWith("MyDices(") && streng.endsWith(")")){
            int spiller = 0;
            streng = streng.substring(8,streng.indexOf(")"));

            for (int i = 0; i < streng.length(); i++) {
                if(streng.charAt(i) > 57 || streng.charAt(i) < 48){
                    spiller = -1;
                    i = streng.length();
                }else{
                    spiller += ((int) streng.charAt(i)-48)* (int) Math.pow(10, streng.length()-1-i);
                }
            }
        
            spilLogik.printTerninger(spiller);

        }else if(streng.trim().matches("AllDices!")){
            spilLogik.printTerninger(0);

        }else{
            System.out.println("Ugyldig kommando!: "+streng);
        }
    }
    
    private static void printstats() {
        if(debug){
            for (int i = 1; i < 7; i++) {
                System.out.println("   Terninger    Komb");
                System.out.println(i+"'er: "+Spil.getAntalØjne(i)+" \t "+Spil.getKombinationer(i));
            }            
        }    
    }
}
