/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    
    public String modtag(){
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
    public Klient.Klient_raflebaeger modtagTerninger(String beskeden){
        try{
            String besked = indBuffer.readLine().replace("[", " ");
            besked = besked.replace(",", "");   //dont need two spaces between ints
            besked = besked.replace("]", "");   //dont need to end on space
            //ctr:tern[5, 4, 3]
            Klient.Klient_raflebaeger baeger;
            baeger = new Klient.Klient_raflebaeger(besked);
            return baeger;
        } catch (IOException ioe){
            ioe.printStackTrace();
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
    
    boolean getisConnected() {
        return forbindelse.isConnected();
    }
}
