/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

import java.rmi.Naming;

/**
 *
 * @author root
 */
public class ServerSpil {
    
    	public static void main(String[] arg) throws Exception
	{
		// Enten: Kør programmet 'rmiregistry' fra mappen med .class-filerne, eller:
		java.rmi.registry.LocateRegistry.createRegistry(1099); // start i server-JVM

		ServerSpil_Interface ServerSpil_instance = new ServerSpil_implementation();
		Naming.rebind("rmi://localhost:1099/ServerSpil_Snyd_RMI", ServerSpil_instance);
		System.out.println("ServerSpil Snyd tjeneste sat op");
	}
    
}
