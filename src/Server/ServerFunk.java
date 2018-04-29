/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.ArrayList;
import terningspillet_snyd.Raflebaeger;

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
    
    public void initierRunde(ArrayList<Raflebaeger> raflebærgre,int antalTerninger){
        netværk.sendAntalTernigerTilAlle(antalTerninger);
        for (int i = 0; i < navne.size(); i++){
            netværk.sendRaflebaere(raflebærgre.get(i),i);
        }
        netværk.sendTilAlle("ctr:start runde");
    }
    
    public String runde(int spillerNr){
        spillerNr--;
        netværk.sendTilSpiller("ctr:tur", spillerNr);
        netværk.sendTilAlle("Det er nu "+ navne.get(spillerNr));
        return netværk.modtagFraSpiller(spillerNr);
    }
    
    /**
     * SpillerNr gættede og gættet blev godtaget
     * @param spillerNr
     * @param antal
     * @param værdi 
     */
    public void spillerGættede(int spillerNr, int antal, int værdi){
        spillerNr--;
        netværk.sendTilSpiller("ctr:gaet accepteret", spillerNr);
        netværk.sendTilAlle("msg:"+ navne.get(spillerNr)+" gættede på at der er "+ antal+" "+værdi+"'ere.");
    }
    
    /**
     * SpillerNr gættete og gættet blev ikke godtaget
     * @param spillerNr
     * @param antal
     * @param værdi 
     */
    public void spillerGættedeFejl(int spillerNr, int antal, int værdi){
        spillerNr--;
        netværk.sendTilSpiller("ctr:gaet ikke accepteret", spillerNr);
        netværk.sendTilSpiller("msg:Dit gæt blev ikke accepteret, da det enten ikke var højere end det forrige gæt eller indeholdt ulovlige værdier", spillerNr);
    }
    
    /**
     * SpillerNr kaldte snyd på spilleren før ham, boolean viser om det var korrekt.
     * @param spillerNr
     * @param sandt 
     */
    public void spillerKaldteSnyd(int spillerNr, boolean sandt){
        
    }
}
