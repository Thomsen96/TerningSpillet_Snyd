/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;


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

    public static void SeSpil() {
        Spil = new Spil(3,6);
        Spil.printTerninger(0);
        Spil.start_rounde();
        printstats();
        
        Spil.løgner(Spil.getHvis_tur());
        
        for (int i = 0; i < 10; i++) {
            Spil.gæt((int) (Math.random()* 6 + 1),(int) (Math.random()* 6 + 1),Spil.getHvis_tur());
        }
        
        Spil.løgner(Spil.getHvis_tur());
        
        for (int j = 0; j < 10; j++) {
            Spil.start_rounde();
            Spil.printTerninger(0);
            //printstats();

            for (int i = 0; i < 3; i++) {
                Spil.gæt((int) (Math.random()* 6 + 1),(int) (Math.random()* 6 + 1),Spil.getHvis_tur());
            }

            Spil.løgner(Spil.getHvis_tur());
        
        }    }
       


    public static void Spilselv() {
        Spil = new Spil(3,6);
        Spil.printTerninger(1);
        Spil.start_rounde();
        
        int spil_status = 1;
        String streng;
        int spiller2 = 6;
        int spiller3 = 7;
        
        while(true){
           switch(spil_status) {
               case 1:
                        System.out.println("Skriv f.eks Guess(4,3) for 4 3'ere, MyDices!, AllDices! eller Liar!");
                        streng = læsINTtastatur();
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
                            
                            Spil.gæt(værdi,antal,1);
                            if(Spil.getHvis_tur() != spil_status){
                                spil_status++;
                            }
                            
                        }else if(streng.matches("Liar!")){
                            Spil.løgner(Spil.getHvis_tur());
                            if(Spil.getSpil_status().matches("runde_slut")){
                                Spil.printTerninger(1);
                                Spil.start_rounde();
                                spil_status = Spil.getHvis_tur();
                            }else if(Spil.getSpil_status().matches("spil_færdig")){
                                spil_status = 4;
                            }
                        }else if(streng.trim().matches("MyDices!")){
                            Spil.printTerninger(1);
                            
                        }else if(streng.trim().matches("AllDices!")){
                            Spil.printTerninger(0);
                            
                        }else{
                            System.out.println("Ugyldig kommando!: "+streng);
                        }
                        break;
                case 2:
                        // Spiller 2
                        Spil.gæt(6,spiller2,2);
                        if(Spil.getHvis_tur() != spil_status){
                            spil_status = 3;
                        } 
                        spiller2=spiller2+1;
                        break;
                case 3:
                        // Spiller 3
                        Spil.gæt(6,spiller3,3);
                        if(Spil.getHvis_tur() != spil_status){
                            spil_status = 1;
                        }
                        spiller3=spiller2+1;
                        break;                    
                case 4:
                        // Spil færdig
                        System.out.println("Spillet er færdigt!");
                        return;                         
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
        String spil_status = "Start";

        while(true){
           switch(spil_status) {
                case "Start":
                    Spil.printTerninger(0); // Print terninger
                    Spil.start_rounde(); // Start runden
                    printstats(); // Viser alle kombi
                    spil_status = "Spil";
                    break;
                case "Spil":
                    System.out.println("Følgende kommandoer er tilladt:Guess(#,#), MyDices(#), AllDices! eller Liar!");
                    spil_status = læsCommandov2();
                    break;
                case "runde_slut":
                    Spil.printTerninger(0); // Print terninger
                    Spil.start_rounde(); // Start en ny runde
                    printstats(); // Viser alle kombi
                    spil_status = "Spil";
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

    private static String læsCommandov2() {
        String streng = læsINTtastatur();
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
            Spil.løgner(Spil.getHvis_tur());
            if(Spil.getSpil_status().matches("runde_slut")){
                return "runde_slut";
            }else if(Spil.getSpil_status().matches("spil_færdig")){
                return "spil_slut"; 
            }
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
        return "Spil";
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