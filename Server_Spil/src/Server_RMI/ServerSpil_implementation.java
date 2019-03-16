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
import java.util.Date;
import java.util.List;
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
    //static String PATH = "/home/thieden/NetBeansProjects/TerningSpillet_Snyd/TerningSpillet_Snyd/Server_Spil/server_jar/TerningSpillet_Snyd_server.jar";
    static String PATH = "~/server_jar/snyd_server/TerningSpillet_Snyd_server.jar";

    private ArrayList<SpilData> SpilDataArray;

    public ServerSpil_implementation() throws RemoteException {
        super(1099);
        SpilDataArray = new ArrayList<SpilData>();
    }

    @Override
    public int createGame(int spillere, int terninger, String brugernavn) throws RemoteException {
        System.out.println("init : createGame(" + spillere + "," + terninger + "," + brugernavn + ") was called");

        // Check if port exceedes the max amount of ports
        if (port + port_min >= port_max) {
            port = 0; // If so, reset it to 0
        }

        for (SpilData SpilData : SpilDataArray) {
            if (SpilData.getID() == port + port_min) {
                // if the process is dead, the game has ended and we can use that port
                if (SpilData.getProcess().isAlive() != true) {
                    SpilData.getProcess().destroy();
                    SpilDataArray.remove(SpilData);
                    break;
                } else {
                    port++;

                }
            }
        }

        // Out of ports
        if (port + port_min > port_max) {
            System.out.println("Stop : createGame(" + spillere + "," + terninger + "," + brugernavn + ")"
                    + " call, stopped because \"Out of ports\"");
            throw new IllegalStateException("Out of ports to give " + (port + port_min));
            //return -1;
        }

        int port_lokal = port + port_min;

        String arg = spillere + " " + terninger + " " + port_lokal;

        System.out.println("Server was opened with the following arg : \"" + arg + "\"");
        SpilData sd = null;
        try {
//            Process p = Runtime.getRuntime().exec("java -cp " + PATH + " Server.ServerLogik " + arg);
            List<String> commands = new ArrayList<String>();
            commands.add("bash");
            commands.add("-c");

            Date d = new Date();
            commands.add("java -cp " + PATH + " Server.ServerLogik " + arg);
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            sd = new SpilData(port_lokal, spillere, terninger, brugernavn, p);

            SpilDataArray.add(sd);
        } catch (IOException ex) {
            Logger.getLogger(ServerSpil_implementation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Stop : createGame(" + spillere + "," + terninger + "," + brugernavn + ")"
                    + " call, stopped because \"Something went wrong creating the game at port\"");
            throw new IllegalStateException("Something went wrong creating the game at port : " + (port + port_min));

        }

        //    We use the SpilDataArray to find the index of the server instance
        //  we are looking for.
        if (sd == null) {
            return -1;
        }
        System.out.println("Stop : createGame(" + spillere + "," + terninger + "," + brugernavn + ")"
                + " call sucessfully made a game on port : " + port_lokal);
        return port_lokal;
    }

    @Override
    public ArrayList<SpilData> getGames() throws RemoteException {
        System.out.println("init : getGames()");
        cleanGames();
        if (!SpilDataArray.isEmpty()) {
            System.out.println("stop : getGame() returned a GameDataArray with size: " + SpilDataArray.size());
            return SpilDataArray;
        } else {
            System.out.println("stop : getGame(), it didnt find any games");
            throw new IllegalAccessError("There is no running games");
        }
    }

    @Override
    public SpilData getGame(int ID) throws RemoteException {
        System.out.println("init : getGame()");
        cleanGames();
        for (SpilData SpilData : SpilDataArray) {
            if (SpilData.getID() == ID) {
                System.out.println("stop : getGame(), it found game : " + SpilData);
                return SpilData;
            }
        }
        System.out.println("stop : getGame(), it didnt find a game with ID: " + ID);
        throw new IllegalArgumentException("could not find game with ID : " + ID);
    }

    @Override
    public int closegames() throws RemoteException {
//        kør følgende kommando "kill $(ps aux | grep 'java -cp /home/chris/Desktop/test' | awk '{print $2}')"
        //Process p = Runtime.getRuntime().exec("../killall.sh");
        //return SpilDataArray;
        System.out.println("init : closegames()");
        int i = 0;
        if (!SpilDataArray.isEmpty()) {
            try {

                for (SpilData SpilData : SpilDataArray) {
                    i++;
                    SpilData.getProcess().destroy();
                }
                SpilDataArray.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("stop : closegames(), it didnt close any games");
            throw new IllegalAccessError("There is no running games to close");
        }
        System.out.println("stop : closegames(), it closed " + i + " games");

        return i;
    }

    @Override
    public int closegame(int port, String brugernavn) throws RemoteException {
        System.out.println("init : closegame()");
        int i = 0;
        for (SpilData SpilData : SpilDataArray) {
            try {
                if (SpilData.getID() == port && SpilData.getBrugernavn().equals(brugernavn)) {
                    SpilData.getProcess().destroy();
                    SpilDataArray.remove(i);
                    System.out.println("stop : closegame(), removed : game " + port
                            + " for : " + brugernavn);

                    return i;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("stop : closegame(), could not close: " + SpilData
                        + "because of exception");

            }
            i++;
        }
        System.out.println("stop : closegame(), could not remove: game " + port
                + " for : " + brugernavn + " because no ID matched");
        throw new IllegalArgumentException("could not find gayme with ID : " + port
                + " and username : " + brugernavn);
    }

    public int cleanGames() {
        int i = 0;
        System.out.println("init : cleanGames()");

        for (int j = SpilDataArray.size() - 1; j >= 0; j--) {
            try {
                if (!SpilDataArray.get(j).getProcess().isAlive()) {
                    System.out.println("remove " + j + "" + SpilDataArray.get(j));

                    SpilDataArray.get(j).getProcess().destroy();
                    SpilDataArray.remove(j);

                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        System.out.println("stop : cleanGames(), cleaned " + i + " games");
        return i;
    }

    //Htop på linux og filter på "java -cp"
}
