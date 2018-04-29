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
    
    /**
     * Serveren oprettes på port, og er herefter klar til at modtage forbindelser
     * @param port 
     */
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
    
    /**
     * Skriver til spillerene at spillet starter
     * Herefter gåes til "initier runde"
     */
    public void initierSpil(){
        netværk.sendTilAlle("msg:Alle spillere er nu tilsluttet og spillet vil gå igang.");
        netværk.sendTilAlle("msg:Spillerlisten er som følger:");
        for (int i = 0; i < navne.size(); i++){
            netværk.sendTilAlle("msg:   Spiller " + (i+1) + " er "+ navne.get(i));
        }
        netværk.sendTilAlle("ctr:initier runde");
    }
    
    /**
     * Sender det totale antal terninger og hvert raflebære til deres respektive ejere.
     * Herefter gåes til "start runde"
     * @param raflebærgre
     * @param antalTerninger 
     */
    public void initierRunde(ArrayList<Raflebaeger> raflebærgre,int antalTerninger){
        netværk.sendAntalTernigerTilAlle(antalTerninger);
        for (int i = 0; i < navne.size(); i++){
            netværk.sendRaflebaere(raflebærgre.get(i),i);
        }
        netværk.sendTilAlle("ctr:start runde");
    }
    
    /**
     * Fortæller spilleren der har tur det er hans tur og retunerer den kommando han sender.
     * @param spillerNr
     * @return kommando
     */
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
     * @param tidligereGæt 
     * @param visTerninger 
     */
    public void spillerKaldteSnyd(int spillerNr, boolean sandt, Tur tidligereGæt, String visTerninger){
        spillerNr--;
        int tidligereSpiller = tidligereGæt.spiller;
        tidligereSpiller--;
        //Spilleren kaldte snyd på spilleren før ham(tur.spiller), bolean fortæller om det var korrekt eller ej.
        String tern1 = visTerninger.substring(0, visTerninger.indexOf(";"));
        String tern2 = visTerninger.substring(visTerninger.indexOf(";"), visTerninger.length()-1);
        
        if (sandt){
            netværk.sendTilAlle("msg:"+navne.get(spillerNr)+" har kaldt snyd på "+
                    navne.get(tidligereSpiller)+"'s gæt." );
            netværk.sendTilAlle("msg:Terningerne var som følger:");
            netværk.sendTilAlle("msg:"+tern1);
            netværk.sendTilAlle("msg:"+tern2);
            netværk.sendTilAlle("msg:Der var ikke "+tidligereGæt.antal+" "+tidligereGæt.værdi+"'ere.");
            netværk.sendTilAlle("msg:"+navne.get(tidligereSpiller)+" har tabt runden!");
        } else {
            netværk.sendTilAlle("msg:"+navne.get(spillerNr)+" har kaldt snyd på "+
                    navne.get(tidligereSpiller)+"'s gæt." );
            netværk.sendTilAlle("msg:Terningerne var som følger:");
            netværk.sendTilAlle("msg:"+tern1);
            netværk.sendTilAlle("msg:"+tern2);
            netværk.sendTilAlle("msg:Der var "+tidligereGæt.antal+" "+tidligereGæt.værdi+"'ere.");
            netværk.sendTilAlle("msg:"+navne.get(spillerNr)+" har tabt runden!");
        }
        netværk.sendTilAlle("ctr:runde slut");
    }
    
    /**
     * Spilleren har sendt en ugyldig kommando
     * @param spillerNr 
     */
    public void spillerUgyldigKomando(int spillerNr){
        spillerNr--;
        netværk.sendTilSpiller("msg:Din sendte kommando er ugyldig", spillerNr);
        netværk.sendTilSpiller("ctr:ugyldigt", spillerNr);
    }
    /**
     * Spillet er slut, taberen udråbes og alle spillere kickes.
     * @param taber 
     */
    public void spilSlut(int taber){
        taber--;
        netværk.sendTilAlle("msg:Spillet er slut.");
        netværk.sendTilAlle("msg:"+navne.get(taber)+" har tabt og skal give en omgang");
        for (int i = 0; i < navne.size()-1; i++){
            netværk.kickSpiller(0); //Hver gennemløb bliver en ny spiller den første i listen,
            navne.remove(0);        //da de fjernes fra samme liste. Derfor smiddes spiller 0 ud
        }
        
    }
}
