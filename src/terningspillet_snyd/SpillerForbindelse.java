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

/**
 *
 * @author john
 */
public class SpillerForbindelse {
    private final Socket spillerSocket;
    public final PrintWriter spillerSend;
    public final BufferedReader spillerRecive;
    private String spillerNavn;

    
    /**
     * @param socket
     * @throws java.io.IOException
     */
    public SpillerForbindelse(Socket socket) throws IOException{
        spillerSocket = socket;
        spillerSend = new PrintWriter(socket.getOutputStream());
        spillerRecive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public void setSpillerNavn(String navn){
        spillerNavn = navn;
    }
    
    public String getSpillerNavn() {
        return spillerNavn;
    }
    
    public void send(String besked){
        spillerSend.println(besked);
        spillerSend.flush();
    }
    
    public String modtag() throws IOException{
        return spillerRecive.readLine();
    }
}
