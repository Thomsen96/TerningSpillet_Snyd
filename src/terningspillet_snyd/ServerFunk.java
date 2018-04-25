/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import java.util.ArrayList;

/**
 *
 * @author john
 */
public class ServerFunk {
    private ServerNetværk netværk;
    private ArrayList<String> navne;
    private int antalSpillere;
    
    public ServerFunk(int port){
        netværk = new ServerNetværk(port);
        navne = new ArrayList<>();
        
    }
    
    /**
     * Tilføjer antallet af spillere til spillet
     * 
     * @param antalSpillere 
     */
    public void modtagForbindelse(int antalSpillere){
        this.antalSpillere = antalSpillere;
        for (int i = 0; i < antalSpillere; i++){
            netværk.modtagForbindelse();
            navne.add(netværk.modtagFraSpiller(i));
            netværk.sendTilSpiller("ctr:accepteret", i);
            System.out.println("Player "+ navne.size() +" added to list, with name: " + navne.get(i));
            netværk.sendTilAlle("msg:Spilleren "+ navne.get(i)+" har tilsluttet sig spillet som spiller " + navne.size());
        }
        netværk.sendTilAlle("ctr:initier spil");
    }
    
    public void initierSpil(){
        netværk.sendTilAlle("msg:Alle spillere er nu tilsluttet og spillet vil gå igang.");
        netværk.sendTilAlle("msg:Spillerlisten er som følger:");
        for (int i = 0; i < navne.size(); i++){
            netværk.sendTilAlle("msg:   Spiller " + (i+1) + " er "+ navne.get(i));
        }
        netværk.sendTilAlle("ctr:initier runde");
    }
    
    public void initierRunde(ArrayList<Raflebaeger> raflebærgre){
    
    }
    
}
