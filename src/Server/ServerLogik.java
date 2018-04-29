/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author john
 */
public class ServerLogik {
    public static Spil spilLogik;
    public static ServerFunk serverLogik;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 8998;
        int antalSpillere = 2;
        int antalTerninger = 6;
        
        serverLogik = new ServerFunk(port);
        spilLogik = new Spil(antalSpillere, antalTerninger);
        
        serverLogik.modtagForbindelse(antalSpillere);
        serverLogik.initierSpil();
        
        
        while(!spilLogik.getSpil_status().equals("spil slut")){
           switch(spilLogik.getSpil_status()) {
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
                    spilLogik.printTerninger(0); // Print terninger
                    serverLogik.initierRunde(Spil.liste_af_raflebaeger, spilLogik.antal_terninger_ialt()); //skriv til spillere runden begynder
                    spilLogik.start_rounde(); // Start en ny runde
                    printstats(); // Viser alle kombi
                    break; 
                case "spil_slut":
                    serverLogik.spilSlut(spilLogik.getTaber());
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
    private static void læsCommandov3(String streng) {
        int turFørSkift = spilLogik.getHvis_tur();
        
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
            
            //Serveren sender beskeder ud om tilstanden af spillet
            if (spilLogik.getHvis_tur() != turFørSkift){
                serverLogik.spillerGættede(turFørSkift, antal, værdi);
            } else {
                serverLogik.spillerGættedeFejl(turFørSkift, antal, værdi);
            }
            //end if - gæt
        }else if(streng.matches("Liar!")){
            spilLogik.løgner();
            
            //Serverlogikken sender beskeder ud om tilstanden.
            if (spilLogik.getTaber() != turFørSkift){
                serverLogik.spillerKaldteSnyd(turFørSkift, true, spilLogik.forrige_gæt, stringStats());
            } else {
                serverLogik.spillerKaldteSnyd(turFørSkift, false, spilLogik.forrige_gæt, stringStats());
            }
        }else{
            System.out.println("Ugyldig kommando!: "+streng);
            
            //server funk
            serverLogik.spillerUgyldigKomando(turFørSkift);
        }
    }
    
    private static void printstats() {
        for (int i = 1; i < 7; i++) {
            System.out.println("   Terninger    Komb");
            System.out.println(i+"'er: "+spilLogik.getAntalØjne(i)+" \t "+spilLogik.getKombinationer(i));
        }      
    }
    
    private static String stringStats() {
        String streng = new String();
        streng += "Terninger  1  2  3  4  5  6;";
        for (int i = 1; i < 7; i++) {
            streng += ""+spilLogik.getAntalØjne(i)+"  ";
        }//end for
        return streng;
    }
}