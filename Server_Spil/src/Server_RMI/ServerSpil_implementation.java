/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ServerSpil_implementation extends UnicastRemoteObject implements ServerSpil_Interface {

    static int port_min = 9000;
    static int port_max = 9100;
    int port = 0;
    static String PATH = "/home/thieden/NetBeansProjects/TerningSpillet_Snyd/TerningSpillet_Snyd/Server_Spil/server_jar/TerningSpillet_Snyd_server.jar";

    private ArrayList<SpilData> SpilDataArray;

    public ServerSpil_implementation() throws RemoteException {
        super(1099);
        SpilDataArray = new ArrayList<SpilData>();
    }

    @Override
    public int createGame(int spillere, int terninger, String brugernavn) throws RemoteException {

        // Check if port exceedes the max amount of ports
        if (port + port_min >= port_max) {
            port = 0; // If so, reset it to 0
        }

        for (SpilData SpilData : SpilDataArray) {
            if (SpilData.getID() == port + port_min) {
                port++;
            }
        }

        // Out of ports
        if (port + port_min > port_max) {
            throw new IllegalStateException("Out of ports to give " + (port + port_min));
            //return -1;
        }

        int port_lokal = port + port_min;

        String arg = spillere + " " + terninger + " " + port_lokal;

        System.out.println("Server åbnet følgende argument " + arg);

        System.out.println("Startet");
        SpilData sd = null;
        try {
            Process p = Runtime.getRuntime().exec("java -cp " + PATH + " Server.ServerLogik " + arg);
            sd = new SpilData(port_lokal, spillere, terninger, brugernavn);
            SpilDataArray.add(sd);
        } catch (IOException ex) {
            Logger.getLogger(ServerSpil_implementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sluttet");

        //    We use the SpilDataArray to find the index of the server instance
        //  we are looking for.
        if (sd == null) {
            return -1;
        }
        /*
        int i = 0;
        for (SpilData SpilData : SpilDataArray) {

            if (SpilData.getID() == port_lokal) {
                break;
            }
            i++;
        }
        SpilDataArray.remove(i);
         */
        return port_lokal;
    }

    @Override
    public ArrayList<SpilData> getGames() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SpilData getGame(int ID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<SpilData> closegames() throws RemoteException {
        //kør følgende kommando "kill $(ps aux | grep 'java -cp /home/chris/Desktop/test' | awk '{print $2}')"
        //Process p = Runtime.getRuntime().exec("../killall.sh");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<SpilData> closegame(int port, String brugernavn) throws RemoteException {
        //port skal indsættes i kommandoen
        //kør følgende scriptkiddy Process p = Runtime.getRuntime().exec("../killspecific.sh "+port);
        //kør følgende kommando "kill $(ps aux | grep 'Server.ServerLogik [0-9]* [0-9] port' | awk '{print $2}')"
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Htop på linux og filter på "java -cp"
}
