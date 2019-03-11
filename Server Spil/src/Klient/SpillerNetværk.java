/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author john
 */
public class SpillerNetværk {
    private Socket forbindelse;
    private PrintWriter udBuffer;
    private BufferedReader indBuffer;
    
    public SpillerNetværk(int port, String navn){
        try{
            
            forbindelse = new Socket("localhost", port);

            udBuffer  = new PrintWriter(forbindelse.getOutputStream());
            indBuffer = new BufferedReader(new InputStreamReader(forbindelse.getInputStream()));
            
            send(navn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SpillerNetværk(int port, String navn, String IP) throws IOException {

            if("127.0.0.1".equals(IP)){ // Hvis localhost
               forbindelse = new Socket("localhost", port); 
            }else{ // Hvis anden IP end localhost
               InetAddress Ip_addresse = InetAddress.getByName(IP); // Et InetAddress objekt som indeholder IP addressen klienten har angivet
               forbindelse = new Socket(Ip_addresse, port);
            }
            

            udBuffer  = new PrintWriter(forbindelse.getOutputStream());
            indBuffer = new BufferedReader(new InputStreamReader(forbindelse.getInputStream()));
            
            send(navn);       
    }
    
    public String modtag() throws IOException{
        String m = new String();
        try {
            
            //this bit waits for new lines until it is empty, which only happens when the conection closes.
            /* 
            String s = indBuffer.readLine();
            String m = new String();
            while (s != null) {      // readLine() giver null når datastrømmen lukkes
                m = m + (s + ";");
                System.out.println("modt: "+s);
                
                s = indBuffer.readLine();
                
            }
            //return s;
            */
            m = indBuffer.readLine();
            return m;
            
        } catch (Exception e) {
            e.printStackTrace();
            lukforbindelse();
        }
        return "error";
    }
    
    public void send(String besked){
        udBuffer.println(besked);
        udBuffer.flush();
    }
    
    /**
     * Modtager raflebæger fra server
     * Afhængig af formatet af Raflebaeger.toString();
     * @return 
     */
    public Klient_raflebaeger modtagTerninger(String beskeden){
        try{
            /*
            String besked = indBuffer.readLine().replace("[", " ");
            besked = besked.replace(",", "");   //dont need two spaces between ints
            besked = besked.replace("]", "");   //dont need to end on space
            */
            beskeden = beskeden.replace("[", " ").replace(",", "").replace("]", "");
            Klient_raflebaeger baeger;
            baeger = new Klient_raflebaeger(beskeden);
            return baeger;
        } catch (Exception e){ // IOException ioe
            e.printStackTrace();
            //ioe.printStackTrace();
        }
        return null;
    }
    
    public int modtagAntalTerninger(){
        try {
            Scanner beskedScanner = new Scanner(indBuffer.readLine());
            while(beskedScanner.hasNextInt() != true){
                beskedScanner.next();
            }
            
            return beskedScanner.nextInt();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return -1;
    }
    
    public boolean getisConnected() {
        return !forbindelse.isClosed();
    }

    void lukforbindelse() throws IOException {
        forbindelse.close();
    }

    public boolean isForbindelseNull() {
        if (forbindelse == null){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Metode der returnere IP-adressen på serveren
     * @return serverens IP
     */
    public String getSocketIP(){
        String hostAddress = forbindelse.getInetAddress().getHostAddress();
        
        if(hostAddress.equals("127.0.0.1")){ // Hvis det denne klient der har hostet serveren
            try {
                return InetAddress.getLocalHost().getHostAddress(); // Returner Ip-addressen på Host
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
                return "fejl";
            }
        }
        return hostAddress; // Returner Ip-addressen på Serveren
    }
    
}
