package Klient;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Klientens program
 */
public class Klient {
    
    private static JTabbedPane faneblade;
    private static KlientFunk klient;
    private static Velkomstskærm Velkomstskærm;
    private static Spil_skærm spil_skærm;
    private static JFrame vindue;
    
    public static void main(String[] args) {
        klient = null;
        
        faneblade = new JTabbedPane();
        
        Velkomstskærm = new Velkomstskærm();
        spil_skærm = new Spil_skærm();
        
        faneblade.add("Start", Velkomstskærm);
        
        vindue = new JFrame("Snyd");
        vindue.add( faneblade );
        vindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // reagér på luk
        vindue.pack();                  // lad vinduet selv bestemme sin størrelse
        vindue.setVisible(true);
        //Denne kode kommer fra Jakob Falk
//        faneblade.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
//                protected int calculateTapAreaHight(int t, int h, int m){
//                    return 0;
//                }
//            }
//        );
        
        // Vent på at spillet startes
        while(Velkomstskærm.start == 0 && vindue.isShowing()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                
            }
        }
        
        // Hvis vinduet bliver lukket
        if (!vindue.isShowing()){
            System.exit(0);
        }
        
        
        String navn = Velkomstskærm.getnavn();
        int port = Velkomstskærm.getport();
        String IP = Velkomstskærm.getIP();
        
        String slut_besked = "Fejl!"; // Besked der skal vises i popup-boken
        
        try {
            klient = new KlientFunk(port,navn,IP);
            faneblade.add("Spillet", spil_skærm);     

            spil_skærm.setlogik(klient); // Giver Spil_skærmen et KlientFunk objekt
            faneblade.remove(Velkomstskærm); // Fjerner Velkomstskærmen så spillet vises

            while(klient.Forbundet() && vindue.isShowing()){ // Hvis der er en forbindelse og vinduet bliver vist
                String msg = klient.modtagKommando(); // Modtag kommandoer og hvis det er en "message" ("msg:...") gem den i msg      

                if(!msg.matches("\null") && !msg.startsWith("ctr:")){ // Hvis beskeden er en msg
                    spil_skærm.tilføjText_til_tekstboks(msg);
                    if(msg.endsWith("omgang!") || msg.endsWith("kicket!")){ // Hvis spillet er slut og en taber er udpejet
                        slut_besked = msg;
                    }

                }else if(msg.matches("ctr:start runde")){ // Hvis runden skal startes
                    spil_skærm.tegnTerninger(klient.baerger.antalTerninger()); // Send klientens terninger til GUI
                    spil_skærm.sætAntalTerningerIAlt(klient.antal_terninger_ialt); // Send terninger i alt til GUI
                    System.out.println("Sender antal terninger til GUI: "+klient.baerger.antalTerninger());
                }
                

                if("Tur".equals(klient.getState())){ // Hvis klienten får af vide at det er dens tur
                    spil_skærm.visknapper(); // Enable "Gæt" og "Løgner!" knaperne i GUI
                }
            }

           String taber = "testperson";
           if (!vindue.isShowing()){ // Hvis vinduet er lukket ved "velkomstskærm", luk programmet
                System.exit(0);
           }
        } catch (Exception e) {
            slut_besked = "Forbindelsen til serveren kunne ikke oprettes! \nKlienten lukkes ned!";
            System.err.println("Error creating the socket");
        }      
        
       javax.swing.JOptionPane.showMessageDialog(vindue, ""+slut_besked); // Vis besked vindue
       vindue.setVisible(false); // Fjern vinduet
       System.exit(0); // Luk programmet
        
        
    }   
}
