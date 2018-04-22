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
    
    public void modtagForbindelse() {
        try{
            Socket nySpiller = server_socket.accept();
            SpillerForbindelse nySpillerForbindelse = new SpillerForbindelse(nySpiller);
            /*
            spillerSocket.add(nySpiller);
            spillerSend.add(new PrintWriter(nySpiller.getOutputStream())) ;
            spillerRecive.add(new BufferedReader(new InputStreamReader(nySpiller.getInputStream())));
            spillerNavn.add(modtagFraSpiller(spillerSocket.size() -1));
            */
            
            spillere.add(nySpillerForbindelse);
            nySpillerForbindelse.setSpillerNavn(nySpillerForbindelse.modtag());
            System.out.println("Player "+ spillere.size() +" added to list, with name: " + nySpillerForbindelse.getSpillerNavn());
            
        } catch (Exception e) {
           e.printStackTrace(); 
        }
    }
    
    
    public void sendTilAlle(String besked){
        for (SpillerForbindelse spiller : spillere){
            spiller.send(besked);
        }
        System.out.println("Message: \""+ besked +" \" Sent to all players.");
    }
    
    public void sendTilSpiller(String besked, int spillerNr){
        /*
        spillerSend.get(spiller).println(besked);
        spillerSend.get(spiller).flush();
        */
        
        spillere.get(spillerNr).send(besked);
        System.out.println("Message: \""+ besked +" \" Sent to player "+spillerNr+".");
    }
    
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
    
    public void sendRaflebaere(Raflebaeger baeger, int spillerNr){
        SpillerForbindelse spiller = spillere.get(spillerNr);
        spiller.send(baeger.toString());
    }
}
