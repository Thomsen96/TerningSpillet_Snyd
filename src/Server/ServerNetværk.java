/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import terningspillet_snyd.Raflebaeger;

/**Klassen holder styr på at kører serveren og en liste af klienter.
 * Den har funktioner til at sende til klienterne og modtage fra dem ud fra deres nr i listen.
 *
 * @author john
 */
public class ServerNetværk {
    private ServerSocket server_socket;
    private final ArrayList<SpillerForbindelse> spillere;

    /**
     * Opretter objektet og en server socket på den angivne port.
     * @param port 
     */
    public ServerNetværk(int port){
        try {
            server_socket = new ServerSocket(port);
            System.out.println("Server initiated on port: "+ port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        spillere = new ArrayList<>();
        
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
     * Sender string til alle spillere
     * @param besked 
     */
    public void sendTilAlle(String besked){
        for (SpillerForbindelse spiller : spillere){
            spiller.send(besked);
        }
        System.out.println("Message: \""+ besked +"\" Sent to all players.");
    }
    /**
     * Sender string til spillerNr.
     * @param besked
     * @param spillerNr 
     */
    public void sendTilSpiller(String besked, int spillerNr){
        /*
        spillerSend.get(spiller).println(besked);
        spillerSend.get(spiller).flush();
        */
        
        spillere.get(spillerNr).send(besked);
        System.out.println("Message: \""+ besked +"\" Sent to player "+spillerNr+".");
    }
    /**
     * Modtager besked fra spillerNr
     * @param spillerNr
     * @return 
     */
    public String modtagFraSpiller(int spillerNr){
        try{
            //String besked = spillerRecive.get(spiller).readLine();
            String besked = spillere.get(spillerNr).modtag();
            System.out.println("Message: \""+ besked +"\" Recived from player "+spillerNr+".");
            return besked;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return "error";
    }
    /**
     * Sender raflebære til spiller nr.
     * Det sendes som string og konverteres hos spilleren
     * @param baeger
     * @param spillerNr 
     */
    public void sendRaflebaere(Raflebaeger baeger, int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        String streng = "ctr:tern"+baeger.toString();
        spiller.send(streng);
        System.out.println("Message: \""+ streng +"\" Sent to player "+spillerNr+".");
    }
    
    /**
     * Sender antal terninger til bestemt spiller
     * @param antalTerninger
     * @param spillerNr 
     */
    public void sendAntalTernigerTilSpiller(int antalTerninger, int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        String streng = "ctr:antaltern "+antalTerninger;
        spiller.send(streng);
        System.out.println("Message: \""+streng+"\" Sent to player "+spillerNr+".");
    }
    
    /**
     * Sender antal terninger til alle
     * @param antalTerninger 
     */
    public void sendAntalTernigerTilAlle(int antalTerninger){
        String streng = "ctr:antaltern "+antalTerninger;
        for(SpillerForbindelse spiller: spillere){
        spiller.send(streng);
        }
        
        System.out.println("Message: \""+streng+"\" Sent to all players.");
    }
    
    /**
     * Lukker forbindelsen til en spiller og fjerner dem fra listen
     * Der sendes en "cte:kick" før spilleren fjernes
     * @param spillerNr 
     */
    public void kickSpiller(int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        sendTilSpiller("ctr:kick", spillerNr);
        try {
            spiller.lukForbindelse();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        spillere.remove(spillerNr);
        
        
        
    }
    
    public void kickAlle(){
        try {
            server_socket.close();
        } catch (IOException ex) {
            System.out.println("Server.ServerNetværk.kickAlle()"+"  ALLE er blevet kicket");
        }
    }

    InetAddress getIP() {
        return spillere.get(spillere.size()-1).getip();
    }
}
