/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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

    boolean getisConnected() {
        return forbindelse.isConnected();
    }
}
