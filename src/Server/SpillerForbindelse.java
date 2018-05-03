package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**Klassen indeholder en socket en printwriter og en buffere reader.
 * Den bruges til at lave en nem grænseflade at komminukerer med en socket på.
 * Den indeholder også et navn, som gør det muligt at navngive objektet kende dem fra hinanden.
 *
 * @author john
 */
public class SpillerForbindelse {
    private final Socket spillerSocket;
    private final PrintWriter spillerSend;
    private final BufferedReader spillerRecive;
    private String spillerNavn;

    
    /**
     * Opretter objektet omkring en socket
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
    /**
     * Sender en string over socket'en
     * @param besked 
     */
    public void send(String besked){
        spillerSend.println(besked);
        spillerSend.flush();
    }
    /**
     * Modtager en streng fra socket'en
     * @return
     * @throws IOException 
     */
    public String modtag() throws IOException{
        return spillerRecive.readLine();
    }
    /**
     * Lukker socket'en
     * @throws IOException 
     */
    public void lukForbindelse() throws IOException{
        spillerSocket.close();
    }
}
