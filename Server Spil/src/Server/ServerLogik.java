/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author john
 */
public class ServerLogik {

    private static Spil spilLogik;
    private static ServerFunk serverLogik;
    private static Scanner tastatur = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PrintStream o = null; 
        try {
            o = new PrintStream(new File("A.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerLogik.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        // Store current System.out before assigning a new value 
        PrintStream console = System.out; 
  
        // Assign o to output stream 
        System.setOut(o); 
        System.out.println("This will be written to the text file!"); 
        o.close();
  
        
        int antalSpillere = Integer.parseInt(args[0]);
        int antalTerninger = Integer.parseInt(args[1]);
        int port = Integer.parseInt(args[2]);
        
        //System.out.println("antalSpillere: "+antalSpillere);
        //System.out.println("antalTerninger: "+antalTerninger);
        
        // TODO code application logic here
        /*
        System.out.println("Indtast porten du vil åbne på:");

        int port = læsINTtastatur();
        
        int antalSpillere = 0;
        do{
            System.out.println("Indtast antallet af spillere:");
            antalSpillere = læsINTtastatur();
        
        }while(antalSpillere < 2);
        
        
        int antalTerninger = 0;
        do{
            System.out.println("Indtast antallet af terninger:");
            antalTerninger = læsINTtastatur();
        
        }while(antalTerninger < 1);        
        */
        
//        int port = 8998;
//        int antalSpillere = 2;
//        int antalTerninger = 2;

        serverLogik = new ServerFunk(port);
        spilLogik = new Spil(antalSpillere, antalTerninger);

        serverLogik.modtagForbindelse(antalSpillere);
        
        Thread t = new Thread(new Runnable() {
                public void run() {
                    System.out.println(".kickefternolere() er kaldt");
                    serverLogik.handleLateConnections();
                    System.out.println(".kickefternolere() er færdig");
                }
        });
        t.start();
        
        
        serverLogik.initierSpil();
      
        while (true) {
            switch (spilLogik.getSpil_status()) {
                case "start":
                    spilLogik.printTerninger(0); // Print terninger
                    serverLogik.initierRunde(Spil.liste_af_raflebaeger, spilLogik.antal_terninger_ialt()); //skriv til spillere runden begynder
                    spilLogik.start_rounde(); // Start runden
                    printstats(); // Viser alle kombi
                    break;
                case "spil":
                    System.out.println("Følgende kommandoer er tilladt:Guess(#,#), MyDices(#), AllDices! eller Liar!");
                    String komando = serverLogik.runde(spilLogik.getHvis_tur());
                    læsCommandov3(komando);
                    break;
                case "runde_slut":
                    serverLogik.rundeSlut();
                    spilLogik.printTerninger(0); // Print terninger
                    serverLogik.initierRunde(Spil.liste_af_raflebaeger, spilLogik.antal_terninger_ialt()); //skriv til spillere runden begynder
                    spilLogik.start_rounde(); // Start en ny runde
                    printstats(); // Viser alle kombi
                    break;
                case "spil_slut":
                    serverLogik.spilSlut(spilLogik.getTaber());
                    System.out.println("Spillet er færdigt!");
                    //t.stop();
                    return; // Afslut spil                
                default:
                    System.out.println("Fejl, ugyldig tilstand");
                    return;
            }
        }
    }

    /**
     * Behandler en komando fra spiller til spillogikken
     * @param streng 
     */
    private static void læsCommandov3(String streng) {
        int turFørSkift = spilLogik.getHvis_tur();
        if (streng == null){
            //error
            serverLogik.udsmidspillereFEJL();
            System.exit(1337);
            throw new Error("Illgal action from player\nA player probably left the game ");
        }     
        
        //hvis spilleren sender et gæt "Guess(antal,værdi)"
        if(streng.startsWith("Guess(") && streng.endsWith(")")){
            streng = streng.substring(6);
            int antal = 0;
            int værdi = 0;
            
            //udled antal som int
            try {
                String antal_string = streng.substring(0, streng.indexOf(","));

                for (int i = 0; i < antal_string.length(); i++) {
                    if (antal_string.charAt(i) > 57 || antal_string.charAt(i) < 48) {
                        antal = -1;
                        i = antal_string.length();
                    } else {
                        antal += ((int) antal_string.charAt(i) - 48) * (int) Math.pow(10, antal_string.length() - 1 - i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                antal = 0;
            }
            
            //udled værdi som int
            try {
                String værdi_string = streng.substring(streng.indexOf(",") + 1, streng.indexOf(")"));
                for (int i = 0; i < værdi_string.length(); i++) {
                    if (værdi_string.charAt(i) > 57 || værdi_string.charAt(i) < 48) {
                        værdi = -1;
                        i = værdi_string.length();
                    } else {
                        værdi += ((int) værdi_string.charAt(i) - 48) * (int) Math.pow(10, værdi_string.length() - 1 - i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                værdi = 0;
            }

            spilLogik.gæt(værdi,antal); //afsend gættet til spillogikken
            
            //Serveren sender beskeder ud om tilstanden af spillet
            if (spilLogik.getHvis_tur() != turFørSkift){
                serverLogik.spillerGættede(turFørSkift, antal, værdi); //spillerens gæt medførte et turskift da det blev godtaget
            } else {
                serverLogik.spillerGættedeFejl(turFørSkift, antal, værdi); //spillerens gær medførte ikke et turskifte da det ikke var gyldigt
            }
            //end if - gæt
        }else if(streng.matches("Liar!")){ //spilleren indsænder løgner: "Lair!"
            spilLogik.løgner(); //spillogikken informeres
            
            //Serverlogikken sender beskeder ud om tilstanden.
            try {
                //hvis det ikke er første spiller er det gyldigt
                if(spilLogik.forrige_gæt != null){ 
                    if (spilLogik.getTaber() != turFørSkift ){      //spillogikken giver turen til taberen derfor tjekkes der hvem der tabte
                        serverLogik.spillerKaldteSnyd(turFørSkift, true, spilLogik.forrige_gæt, stringStats());
                    } else { 
                        serverLogik.spillerKaldteSnyd(turFørSkift, false, spilLogik.forrige_gæt, stringStats());
                    }
                } else { //hvis man kalder snyd på sig selv er det ugyldigt
                    serverLogik.spillerKaldteSnydUgyldigt(turFørSkift);
                }
            }catch (Exception e) {
                e.printStackTrace();
                System.err.println("Streng er: " + streng);
            }
        }else{
            System.out.println("Ugyldig kommando!: "+streng);
            
            //Serveren skriver til spilleren at komandoen var ugyldig dette bør ikke ske med en korrekt klient
            serverLogik.spillerUgyldigKomando(turFørSkift);
        }

    }
    /**
     * Printer de terninger og kombinationer der eksistere i spillet
     */
    private static void printstats() {
        for (int i = 1; i < 7; i++) {
            System.out.println("   Terninger    Komb");
            System.out.println(i + "'er: " + spilLogik.getAntalØjne(i) + " \t " + spilLogik.getKombinationer(i));
        }
    }
    /**
     * Retunerer en streng med de terninger som er i spillet.
     * Strengen er delt ved et ";", hvor der bør indsættes et linjeskift.
     * @return 
     */
    private static String stringStats() {
        String streng = new String();
        streng += "Terninger\t1'ere  2'ere  3'ere  4'ere  5'ere  6'ere;\t  ";
        for (int i = 1; i < 7; i++) {
            streng += "" + spilLogik.getAntalØjne(i) + "         ";
        }//end for
        return streng;
    }
    
    /**
     * Læser en int fra tastaturet og retunere den.
     * @return 
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
