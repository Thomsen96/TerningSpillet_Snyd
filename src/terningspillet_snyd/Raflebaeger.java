package terningspillet_snyd;
import java.util.ArrayList;
import java.util.Collections;

/** Fil oprindelig skrevet af Jacob Nordfalk
 * En klasse der beskriver et Raflebærger med Terninger */
public class Raflebaeger {

    /**
     * listen af terninger, der er i raflebægeret
     */
    public ArrayList<Terning> terninger;

    /**
     * Holder styr på om raflebaegeret er tomt
     */
    public boolean tom = false;

    /**
     * Holder styr på om trappe reglen er opfyldt for raflebaegeret
     */
    public boolean trappe_regel = true;

    /**
     * Konstruktør der laver et Raflebaeger med 'antalTerninger' terninger kan give en spiller en trappe
     *
     * @param antalTerninger
     * @param trappe
     */
    public Raflebaeger(int antalTerninger, boolean trappe) {
        terninger = new ArrayList<Terning>();
        if (!trappe) {
            for (int i = 0; i < antalTerninger; i++) {
                Terning t;
                t = new Terning();
                terninger.add(t);
            }
        } else { // Lav en trappe 
            for (int i = 0; i < antalTerninger; i++) {
                Terning t;
                t = new Terning(i + 1);
                terninger.add(t);
            }
        }

    }

    /**
     * Konstruktør der laver et Raflebaeger med 'antalTerninger' terninger
     *
     * @param antalTerninger
     */
    public Raflebaeger(int antalTerninger) {
        terninger = new ArrayList<Terning>();

        for (int i = 0; i < antalTerninger; i++) {
            Terning t;
            t = new Terning();
            terninger.add(t);
        }
    }

    /**
     * Lægger en terning i bægeret
     *
     * @param t Et terninge objekt
     */
    public void tilføjTerning(Terning t) {
        terninger.add(t);
    }

    /**
     * fjerner en terning fra bægeret
     */
    public void fjernTerning() {
        try {
            terninger.remove(terninger.size() - 1);
            if (antalTerninger() == 0) {
                tom = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Kunne ikke fjerne terningen!");
        }
    }

    /**
     * ryster bægeret, så alle terningerne bliver 'kastet' og får en ny værdi
     */
    public void ryst() {
        for (Terning t : terninger) {
            t.kast();
        }
        Sorter();
        trappe_regel = true;
        Check_for_trapperegel();
    }

    /**
     * finder antallet af terninger, der viser en bestemt værdi
     */
    public int antalDerViser(int værdi) {
        int resultat = 0;
        for (Terning t : terninger) {
            int terningensVærdi = t.getVærdi();

            // Hvis terningen er 'værdi'
            if (terningensVærdi == værdi) {
                resultat = resultat + 1;
            }
        }
        return resultat;
    }

    /**
     * beskriver bægerets indhold som en streng
     */
    public String toString() {// (listens toString() kalder toString() på hver terning)
        return terninger.toString();
    }

    /**
     * Returner antallet af terninger fra "terninger" listen.
     */
    public int antalTerninger() {
        return terninger.size();
    }

    /**
     * Sortere listen "terninger" med terninge objektor i stigende rækkefølge
     */
    public void Sorter() {
        TerningComparator sammenligner = new TerningComparator();
        Collections.sort(terninger, sammenligner);
    }

    /**
     * Kontrollere om trappereglen er opfyldt for et Raflebaeger
     */
    public void Check_for_trapperegel() {
        if (terninger.size() == 0) {
            trappe_regel = false;
            return;
        }

        for (int i = 0; i < terninger.size(); i++) {
            //System.out.println("Terninger:"+terninger.get(i).getVærdi()+" i er: "+i+" terninger.size() er: "+terninger.size());

            if ((i + 1) != terninger.get(i).getVærdi()) {
                trappe_regel = false;
                return;
            }
        }
        if (trappe_regel) {
            //System.out.println("Trappereglen er opfyldt!");
        }
    }
}
