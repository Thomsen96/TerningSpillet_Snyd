/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author chris
 */
public class Tur {

    private boolean GyldigKombi;
    public int Score;
    public int spiller;
    public int antal;
    public int værdi;

    /**
     * Kontruktør til Tur. Opretter et Tur objekt der får sat en "GyldigKombi" udfra om gættet var "korrekt" udfra om der var nok terninger af den værdi.
     *
     * @param værdi Antal øjne
     * @param antal Antal terninger
     * @param kombi Antallet af terninger der viser "værdi"
     * @param spiller Hvilken spiller der gav gættet
     */
    public Tur(int værdi, int antal, int kombi, int spiller) {
        this.GyldigKombi = false;
        this.spiller = spiller;
        this.antal = antal;
        this.værdi = værdi;

        if (kombi >= antal) {
            GyldigKombi = true;
        }

        Score = (antal * 10) + værdi;
        System.out.println("Spiller " + spiller + " siger " + antal + " " + værdi + "'er");
    }

    /**
     * Tjek om gættet er gyldigt
     *
     * @return om gættet er gyldigt eller ej.
     */
    public boolean isGyldigKombi() {
        return GyldigKombi;
    }

}
