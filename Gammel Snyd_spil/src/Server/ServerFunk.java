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
    private boolean spil_slut;
    
    /**
     * Serveren oprettes på port, og er herefter klar til at modtage forbindelser
     * @param port 
     */
    public ServerFunk(int port){
        netværk = new ServerNetværk(port);
        navne = new ArrayList<>();
        spil_slut = false;
        
    }
    
    /**
     * Venter på at det angivne antal spillere forbinder sig.
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
     * Skriver til spillerene hvem der deltager og at spillet starter.
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
        netværk.sendTilAlle("msg:Det er nu "+ navne.get(spillerNr));
        netværk.sendTilSpiller("ctr:tur", spillerNr);
        return netværk.modtagFraSpiller(spillerNr);
    }
    
    /**
     * SpillerNr gættede på antal og værdi og gættet blev godkendt
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
     * SpillerNr gættete på antal og værdi men gættet blev ikke godtaget
     * @param spillerNr
     * @param antal
     * @param værdi 
     */
    public void spillerGættedeFejl(int spillerNr, int antal, int værdi){
        spillerNr--;
        netværk.sendTilSpiller("ctr:gaet ikke accepteret", spillerNr);
        netværk.sendTilSpiller("msg:Dit gæt blev ikke accepteret.", spillerNr);
    }
    
    /**
     * SpillerNr kaldte snyd på spilleren før ham, boolean viser om det var sandt at det var løgn.
     * Alle informeres om hvem der har tabt, og hvis spillet fortsætter 
     * @param spillerNr
     * @param sandt 
     * @param tidligereGæt 
     * @param visTerninger 
     */
    public void spillerKaldteSnyd(int spillerNr, boolean sandt, Tur tidligereGæt, String visTerninger){
         spillerNr--;
        int tidligereSpiller = tidligereGæt.spiller;
        tidligereSpiller--;
        
        String tern1 = visTerninger.substring(0, visTerninger.indexOf(";"));
        String tern2 = visTerninger.substring(visTerninger.indexOf(";")+1, visTerninger.length()-1);
        
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
        netværk.sendTilAlle("msg: - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        netværk.sendTilAlle("msg: ");
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
        netværk.sendTilAlle("msg:"+navne.get(taber)+" har tabt og skal give en omgang!");
        try {
            Thread.sleep(1000); // Så alle klienterne kan nå og modtage sidste msg og vise det i popup boksen.
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        for (int i = navne.size()-1; i >= 0; i--){
            netværk.kickSpiller(i); //Hver gennemløb bliver en ny spiller den første i listen,
            navne.remove(i);        //da de fjernes fra samme liste. Derfor smiddes spiller 0 ud
        }
        
        netværk.kickAlle();
        
        spil_slut = true;
        
    }
    
    /**
     * Køres når runden er slut.
     * Bringer spillerne til "initier runde"
     */
    void rundeSlut() {
        netværk.sendTilAlle("ctr:initier runde");
    }
    
    /**
     * Køres hvis en spiller kalder snyd på sig selv.
     * 
     * @param spillerNr 
     */
    void spillerKaldteSnydUgyldigt(int spillerNr) {
        spillerNr--;
        netværk.sendTilAlle("msg:"+navne.get(spillerNr)+" kaldte snyd på sig selv, det måes man ikke!");
    }
    
    /**
     * Håndtere kliententer der prøver at tilslutte sig efter, at et spil er startet.
     * 
     */
    void handleLateConnections(){       
        int tal = 0;
        while (!spil_slut) { // Hvis spillet er slut
            try {
                netværk.afvisForsinkedeForbindelser();
                String streng = netværk.getIP().toString();
                netværk.spillere_udsmides_kick();
                tal++;
                System.out.println("En spiller med IP:"+streng+" prøvede at tilslutte sig spillet, efter spillet var igangsat. \"Spiller udsmidnings Thread\" kørt: "+tal+" gange");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
