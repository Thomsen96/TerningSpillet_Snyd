/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient_RMI;

import Server_RMI.*;
import java.rmi.Naming;

/**
 *
 * @author root
 */
public class KlientTest_closeGame {

    public static void main(String[] arg) throws Exception {
        //ServerSpil_Interface ServerSpil_inst = (ServerSpil_Interface) Naming.lookup("rmi://localhost:1099/ServerSpil_Snyd_RMI");
        ServerSpil_Interface ServerSpil_inst = (ServerSpil_Interface) Naming.lookup("rmi://130.225.170.205:1099/ServerSpil_Snyd_RMI");
        for (int i = 0; i < 1; i++) {
            try {
                System.out.println(i + ". forsøger at dræbe spil på port 9000: " + ServerSpil_inst.closegame(9002, "casam"));
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
