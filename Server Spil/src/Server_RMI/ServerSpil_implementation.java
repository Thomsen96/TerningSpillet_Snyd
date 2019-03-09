/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class ServerSpil_implementation extends UnicastRemoteObject implements ServerSpil_Interface {
    static int port_min = 9000;
    static int port_max = 9100;
    int port = 0;
    private ArrayList<SpilData> SpilDataArray;
    private ArrayList<Server_instance> Server_instanceArray;
    

    public ServerSpil_implementation() throws RemoteException {
        super(1099);
        SpilDataArray = new ArrayList<SpilData>();
        Server_instanceArray = new ArrayList<Server_instance>();
    }

    @Override
    public int createGame(int spillere, int terninger) throws RemoteException {
        
        // Check if port exceedes the max amount of ports
        if(port+port_min >= port_max){
            port = 0; // If so, reset it to 0
        }
       
        for (SpilData SpilData : SpilDataArray) {
            if(SpilData.getID() == port+port_min ){
                port++;
            }
        }
        
        // Out of ports
        if(port+port_min > port_max){
            throw new IllegalStateException("Out of ports to give "+(port+port_min));
            //return -1;
        }
       
        /*
        try {
            (new Socket("127.0.0.1", (port+port_min))).close();
            return -1;
        } catch (Exception e) {
        }
        */
        
        
        try {
            SpilData SD = new SpilData(port+port_min, spillere, terninger);
            SpilDataArray.add(SD);
            Server_instance S = new Server_instance(spillere, terninger, port+port_min);
            
            // Start a new thread that runs a game and will clean up after it self
            // on the array lists
            Thread t = new Thread(new Runnable() {
                public void run() {
                    int port_lokal = port+port_min;
                    try {
                        S.startSpil();
                    } catch (Exception e) {
                        System.out.println("Der skete en fejl i spillet med port: "+port_lokal);
                        e.printStackTrace();
                        System.out.println("Fjerner spillet fra listen");
                    }
                    
                    /*    We use the SpilDataArray to find the index of the server instance
                     *  we are looking for.
                     */
                    int i = 0;
                    for (SpilData SpilData : SpilDataArray) {

                        if(SpilData.getID() == port_lokal ){
                            break;
                        } 
                        i++;
                    }
                    Server_instanceArray.remove(i);
                    SpilDataArray.remove(i);
                }
            });
            t.start();
            Server_instanceArray.add(S);
            
            //Return the port of the new Server instance
            return SD.getID();
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
