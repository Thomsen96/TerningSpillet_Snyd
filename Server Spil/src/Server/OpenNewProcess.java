/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class OpenNewProcess {
    public static void main(String[] args) {
        int port = 9000;
        for (int i = 0; i < 2; i++) {
            System.out.println("Server åbnet med port "+port);


            String arg = "2 1 "+port;
        
            try {
                System.out.println("Startet");
                // java -cp /home/chris/Desktop/test_Snyd/TerningSpillet_Snyd_server.jar Server.ServerLogik 2 2 9001 > logfile_2_2_9001.log &
                Process p = Runtime.getRuntime().exec("java -cp /home/chris/Desktop/test_Snyd/TerningSpillet_Snyd_server2.jar Server.ServerLogik " + arg);

                //Process p = Runtime.getRuntime().exec("java -cp /home/chris/Desktop/test_Snyd/TerningSpillet_Snyd_server2.jar Server.ServerLogik " + arg);
                //Process p = Runtime.getRuntime().exec("java -cp /home/s164833/TerningSpillet_Snyd_server.jar Server.ServerLogik " + arg);
                System.out.println("Sluttet");
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(OpenNewProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
            port++;
        }
        
        System.out.println("Lukker");
    }
}
