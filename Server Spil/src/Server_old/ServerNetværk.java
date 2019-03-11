/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    public int getAntalSpillere() {
        return spillere.size();
    }
    
    private final ArrayList<SpillerForbindelse> spillere_udsmides;

    /**
     * Opretter objektet og en server socket på den angivne port.
     * @param port 
     */
    public ServerNetværk(int port){
        System.out.println(""+isPortInUse("127.0.0.1", port));
        try {
            server_socket = new ServerSocket(port);
            System.out.println("Server initiated on port: "+ port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        spillere = new ArrayList<>();
        spillere_udsmides = new ArrayList<>();
    }
    
    /**
     * Modtager forbindelse udefra og tilføjer denne til spiller listen.
     * Denne funktion venter på forbindelse fra klient, uden timeout.
     */
    public void modtagForbindelse() {
        try{
            Socket nySpiller = server_socket.accept();
            System.out.println(""+nySpiller.getLocalPort()+" eller "+nySpiller.getLocalSocketAddress()+" thread: "+Thread.currentThread().getName());
            SpillerForbindelse nySpillerForbindelse = new SpillerForbindelse(nySpiller);
            spillere.add(nySpillerForbindelse);
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }
    /**
     * Modtager forbindelse udefra og smider dem ud når spillet allerede er begyndt
     */
    public void afvisForsinkedeForbindelser() {
        try{
            Socket nySpiller = server_socket.accept();
            SpillerForbindelse nySpillerForbindelse = new SpillerForbindelse(nySpiller);
            spillere_udsmides.add(nySpillerForbindelse);
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
            try {
                spiller.send(besked);
            } catch (Exception e) {
                e.printStackTrace(); 
            }
            
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
        try {
            spillere.get(spillerNr).send(besked);
            System.out.println("Message: \""+ besked +"\" Sent to player "+spillerNr+".");
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
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
        
        try {
            spillere.remove(spillerNr);   
        } catch (Exception e) {
        }
        
    }
    
     /**
     * Lukker forbindelsen til spillere på udsmidningslisten
     */
    public void spillere_udsmides_kick(){
        if(spillere_udsmides.size() > 0){
            spillere_udsmides.get(0).send("ctr:kick");
            try {
                spillere_udsmides.get(0).lukForbindelse();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            spillere_udsmides.remove(0); 
        }
    }
    
     /**
     * Smid alle af server socket'en
     */   
    public void kickAlle(){
        try {
            server_socket.close();
        } catch (IOException ex) {
            
        }
    }

     /**
     * Returnere Host/IP på folk der prøver at tilslutte et spil efter at det er gået igang
     */
    String getIP() {
        try {
            return spillere.get(spillere.size()-1).getip().toString();
        } catch (Exception e) {
            return null;
        }
        
    }
    
     /**
     * Tester om en Port er i brug. Dette gør, at folk der prøver køre to instancer af programmet og laver
     * spil der kører på den samme port vil få en fejl
     */    
    private boolean isPortInUse(String host, int port){
        boolean result = false;
        
        try {
            (new Socket(host, port)).close();
            result = true;
        } catch (SocketException e) {
            
        } catch (UnknownHostException e) {
            
        }catch (IOException e) {
            
        }
        
        return result;
    }
}
