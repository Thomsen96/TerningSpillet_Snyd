/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


/**
 *
 * @author chris
 */
public class Spil_tilstandsmaskine {
    
    public static Spil Spil;
    private static boolean debug = true;
    static java.util.Scanner tastatur = new java.util.Scanner(System.in);  // forbered Scanner klassen
    
    
    private static void printstats() {
            if(debug){
                for (int i = 1; i < 7; i++) {
                    System.out.println("   Terninger    Komb");
                    System.out.println(i+"'er: "+Spil.getAntalØjne(i)+" \t "+Spil.getKombinationer(i));
                }            
            }    
        }
    
    /**
     * Opretter et Spil objekt og udgør tilstandsmaskinen der styrer spillet
     * 
     * @param spillere Antal spillere der skal deltage i spillet
     * @param antal_terninger Antal terninger hver spiller skal have
     */
    public static void GAME(int spillere, int antal_terninger) {
        Spil = new Spil(spillere,antal_terninger); // Opret og start spillet 

        while(true){
           switch(Spil.getSpil_status()) {
                case "start":
                    Spil.printTerninger(0); // Print terninger
                    Spil.start_rounde(); // Start runden
                    printstats(); // Viser alle kombi
                    break;
                case "spil":
                    System.out.println("Følgende kommandoer er tilladt:Guess(#,#), MyDices(#), AllDices! eller Liar!");
                    læsCommandov2(læsINTtastatur());
                    break;
                case "runde_slut":
                    Spil.printTerninger(0); // Print terninger
                    Spil.start_rounde(); // Start en ny runde
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
     * Modtager en streng som indeholder en kommando som skal "trækkes ud" af strengen
     * @param streng
     */
    public static void læsCommandov2(String streng) {
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

            Spil.gæt(værdi,antal);
            
        }else if(streng.matches("Liar!")){
            Spil.løgner();
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
        
            Spil.printTerninger(spiller);

        }else if(streng.trim().matches("AllDices!")){
            Spil.printTerninger(0);

        }else{
            System.out.println("Ugyldig kommando!: "+streng);
        }
    }

     /**
     * Funktioner der aflæser tastaturet med exception handeling
     */
    public static String læsINTtastatur() {
        String læstint = "";

        try {
            læstint = tastatur.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            tastatur.nextLine(); // Læs linjeskift
            return "bob bob"; // Returner et ugyldt menu valg
        }
        return læstint;
    }     
}