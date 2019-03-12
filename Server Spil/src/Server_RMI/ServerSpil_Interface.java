/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public interface ServerSpil_Interface extends java.rmi.Remote {
    
    int createGame(int spillere, int terninger, String brugernavn) throws java.rmi.RemoteException;
    ArrayList<SpilData> getGames()    throws java.rmi.RemoteException;
    SpilData getGame(int ID) throws java.rmi.RemoteException;
    ArrayList<SpilData> closegames() throws java.rmi.RemoteException;
    ArrayList<SpilData> closegame(int port) throws java.rmi.RemoteException;
}
