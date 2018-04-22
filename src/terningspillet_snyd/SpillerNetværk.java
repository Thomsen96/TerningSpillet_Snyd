/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public Raflebaeger modtagTerninger(){
        try{
            String besked = indBuffer.readLine().replace("[", " ");
            besked = besked.replace(",", "");   //dont need two spaces between ints
            besked = besked.replace("]", "");   //dont need to end on space
            
            //replace to make space between ints, else scanner.hasNextInt wont work
            
            Scanner beskedScanner = new Scanner(besked);
            Raflebaeger baeger = new Raflebaeger(0, false);
            
            System.out.println("modtag terning called; message recived:" +  besked );
            
            // if the first number is message, first int read is integer
            while(beskedScanner.hasNextInt()){
                Terning t = new Terning(beskedScanner.nextInt());
                baeger.tilføjTerning(t);
                //System.out.println("Ny terning tilføjet");
            }
            
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
}
