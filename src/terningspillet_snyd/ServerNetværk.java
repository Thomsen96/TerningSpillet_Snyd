/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author john
 */
public class ServerNetværk {
    private ServerSocket server_socket;
    
    private final ArrayList<SpillerForbindelse> spillere;
//    public final ArrayList<SpillerForbindelse> spillere;        //for testing
    /*
    private ArrayList<Socket> spillerSocket;
    private ArrayList<PrintWriter> spillerSend;
    private ArrayList<BufferedReader> spillerRecive;
    private ArrayList<String> spillerNavn;
    */
    
    
    
    public ServerNetværk(int port){
        try {
        server_socket = new ServerSocket(port);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        spillerSocket = new ArrayList<>();
        spillerSend = new ArrayList<>();
        spillerRecive = new ArrayList<>();
        spillerNavn = new ArrayList<>();
        */
        
        spillere = new ArrayList<>();
        System.out.println("Server initiated");
    }
    /**
     * Modtager forbindelse udefra og tilføjer denne til spiller listen.
     * Denne funktion venter på forbindelse fra klient, uden timeout.
     */
    public void modtagForbindelse() {
        try{
            Socket nySpiller = server_socket.accept();
            SpillerForbindelse nySpillerForbindelse = new SpillerForbindelse(nySpiller);
            spillere.add(nySpillerForbindelse);
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }
    
    /**
     * sender string til alle spillere
     * @param besked 
     */
    public void sendTilAlle(String besked){
        for (SpillerForbindelse spiller : spillere){
            spiller.send(besked);
        }
        System.out.println("Message: \""+ besked +" \" Sent to all players.");
    }
    /**
     * sender string til spillerNr.
     * @param besked
     * @param spillerNr 
     */
    public void sendTilSpiller(String besked, int spillerNr){
        /*
        spillerSend.get(spiller).println(besked);
        spillerSend.get(spiller).flush();
        */
        
        spillere.get(spillerNr).send(besked);
        System.out.println("Message: \""+ besked +" \" Sent to player "+spillerNr+".");
    }
    /**
     * modtager besked fra spiller
     * @param spillerNr
     * @return 
     */
    public String modtagFraSpiller(int spillerNr){
        try{
            //String besked = spillerRecive.get(spiller).readLine();
            String besked = spillere.get(spillerNr).modtag();
            System.out.println("Message: \""+ besked +" \" Recived from player "+spillerNr+".");
            return besked;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return "error";
    }
    /**
     * Sender raflebære til spiller nr.
     * @param baeger
     * @param spillerNr 
     */
    public void sendRaflebaere(Raflebaeger baeger, int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        spiller.send(baeger.toString());
        System.out.println("Message: \""+ baeger.toString() +" \" Sent to player "+spillerNr+".");
    }
    
    /**
     * Sender antal terninger til bestemt spiller
     * @param antalTerninger
     * @param spillerNr 
     */
    public void sendAntalTernigerTilSpiller(int antalTerninger, int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        spiller.send("Antal terninger: "+antalTerninger);
        System.out.println("Message: \""+"Antal terninger: "+antalTerninger +" \" Sent to player "+spillerNr+".");
    }
    
    /**
     * Sender antal terninger til alle
     * @param antalTerninger 
     */
    public void sendAntalTernigerTilAlle(int antalTerninger){
        for(SpillerForbindelse spiller: spillere){
        spiller.send("Antal terninger: "+antalTerninger);
        }
        
        System.out.println("Message: \""+"Antal terninger: "+antalTerninger +" \" Sent to all players.");
    }
    
    
    public void kickSpiller(int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        try {
            spiller.lukForbindelse();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        spillere.remove(spillerNr);
        
        
    }
}
