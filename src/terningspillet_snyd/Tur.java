/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

/**
 *
 * @author chris
 */
public class Tur {

    private boolean GyldigKombi;
    public int Score;
    public int spiller;

    public Tur(int værdi, int antal, int kombi, int spiller) {
        this.GyldigKombi = false;
        this.spiller = spiller;
        
        if (kombi >= antal) {
            GyldigKombi = true;
        }
        
        Score = (antal*10)+værdi;  
        //System.out.println("Antal: "+antal+" Værdi: "+værdi+" Er det en gyldig kombi? "+GyldigKombi+" Score: "+Score);
        System.out.println("Spiller "+spiller+" siger "+antal+" "+værdi+"'er");
    }
    
    public boolean isGyldigKombi() {
        return GyldigKombi;
    }
    
}
