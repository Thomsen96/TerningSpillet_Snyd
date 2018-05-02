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
 * @author chris
 */
public class Spil {

    /* Forklaring af AntalØjne og Kombinationer arrays:
        AntalØjne[]: Antallet af faktiske terninger
        På plads 0 ligger ingenting, på plads 1 ligger antallet af terninger der viser 1,
        på plads 2 ligger antallet af terninger der viser 2 osv. op til plads 6.
    
        Kombinationer[]: Samme som AntalØjne[], men antallet af terninger der viser 1'ere
        er lagt til alle pladserne fra 2 til 6.
    
        Så et eksempel:
        Player 1 har terningerne [1,2,3,3,4,5]
        Player 2 har terningerne [1,1,2,2,2,6]
    
        Så ville arrys se således ud:
                          0 1 2 3 4 5 6
           AntalØjne[] = {0,3,4,2,1,1,1}
       Kombinationer[] = {0,0,7,5,4,4,4}
    
       Yderemere kan "trappereglen" forekomme som ændre lidt på tingene.
       Trappereglen: Hvis dine terninger ligger i stigende rækkefølge fra 1 til "antal af dine terninger": f.eks.
       hvis du har 5 terninger og de er fra 1-5 så bliver alle dine terningerne lavet om til 1'ere.
       Derudover lægges 1 1'er mere til. 
    
       Her kommer et eksempel med 10 terninger i alt, hvor trappereglen gælder for Player 1:
       Player 1 har terningerne [1,2,3,4,5]
       Player 2 har terningerne [2,2,3,5,6]
    
        Så ville arrys se således ud:
                          0 1 2 3 4 5 6
           AntalØjne[] = {0,6,2,1,1,1,1}
       Kombinationer[] = {0,0,8,7,7,7,7}    
     */
    private int[] AntalØjne = new int[7];
    private int[] Kombinationer = new int[7];
    private int hvis_tur = 1;
    public static ArrayList<Raflebaeger> liste_af_raflebaeger;
    public Tur forrige_gæt;
    private Tur nuværende_gæt;
    private int antal_spillere;
    private int antal_terninger;
    private int runde_nr = 1;
    private int taber;
    private String spil_status;

    /**
     * Når der starter en ny runde skal der slås med terningerne og mulige kombinationer skal udregnes
     *
     * @param liste_af_raflebaeger
     */
    public Spil(int antal_spillere, int antal_terninger) {
        this.antal_spillere = antal_spillere;
        this.antal_terninger = antal_terninger;
        taber = -1; //taber nulværdi

        liste_af_raflebaeger = new ArrayList<Raflebaeger>(); // opret liste-array af "Raflebaeger" objekter

        for (int i = 0; i < antal_spillere; i++) {
            liste_af_raflebaeger.add(new Raflebaeger(antal_terninger));
            liste_af_raflebaeger.get(i).ryst(); // Ryst raflebageret
            liste_af_raflebaeger.get(i).Check_for_trapperegel();
        }

        // Til test af trappereglen
        /*
        for (int i = 0; i < antal_spillere-1; i++) {
            liste_af_raflebaeger.add(new Raflebaeger(antal_terninger, false));
            liste_af_raflebaeger.get(i).ryst(); // Ryst raflebageret
        }
            liste_af_raflebaeger.add(new Raflebaeger(antal_terninger, true));
            liste_af_raflebaeger.get(liste_af_raflebaeger.size()-1).Check_for_trapperegel();
            //liste_af_raflebaeger.get(i).ryst(); // Ryst raflebageret
         */
        spil_status = "start";

    }

    /**
     *
     * @return spil_status strengen
     */
    public String getSpil_status() {
        return spil_status;
    }

    /**
     * Få antallets af terninger der viser "værdi"-antal øjne
     *
     * @param værdi
     * @return
     */
    public int getAntalØjne(int værdi) {
        if (værdi > 0 && værdi < 7) {
            return AntalØjne[værdi];
        } else {
            return 0;
        }

    }

    /**
     * Få antallet af terninger der viser "værdi"-antal øjne OG terninger der viser "1" øje.
     *
     * @param værdi
     * @return
     */
    public int getKombinationer(int værdi) {
        if (værdi > 0 && værdi < 7) {
            return Kombinationer[værdi];
        } else {
            return 0;
        }

    }

    /**
     * Spillerne kan angive et gæt med en værdi og hvor mange af dem han tror der er totalt mellem alle spillerne/ i alles raflebaerger.
     *
     * @param værdi
     * @param antal
     */
    void gæt(int værdi, int antal) {

        // Hvis spillet er slut kan der ikke angives gæt
        if (test_spilstatus()) {
            return;
        }

        // Ser om værdi er gyldig og om antal er større end 0
        if (værdi > 1 && værdi < 7 && antal > 0) {
            if (forrige_gæt == null) { // Hvis det er første tur i en ny runde
                forrige_gæt = new Tur(værdi, antal, this.getKombinationer(værdi), hvis_tur); // Lav et gæt
                Skift_tur();
            } else { // Hvis det er 2.+ tur.
                nuværende_gæt = new Tur(værdi, antal, this.getKombinationer(værdi), hvis_tur); // Lav et gæt

                if (nuværende_gæt.Score > forrige_gæt.Score) { // Gyldigt gæt der er højere end forrige_gæt
                    forrige_gæt = nuværende_gæt;
                    Skift_tur();
                } else {
                    System.out.println("\t Din Score er ikke bedre end det bedste \"forrige_gæt\"");
                    nuværende_gæt = null;
                }
            }
        } else { // Hvis enten værdi eller antal er ugyldig
            System.out.print("Ugyldigt gæt! ");
            System.out.println("Antal: " + antal + " Værdi: " + værdi);
        }

    }

    /**
     * Spilleren der har turen kan angive at den forrige spiller lyver:
     */
    void løgner() {

        // Hvis spillet er slut kan der ikke angives en løgner
        if (test_spilstatus()) {
            return;
        }

        if (forrige_gæt == null) {
            System.out.println("Du kan ikke sige, at du selv lyver!");
        } else {
            //Hvis_turErDet();
            if (forrige_gæt.isGyldigKombi()) {
                System.out.println("Der var ihvertfald " + (int) forrige_gæt.Score / 10 + " "
                        + (int) forrige_gæt.Score % 10 + "'er "
                        + "Så det var sandt! Spiller " + hvis_tur + " har tabt!");

                taber = hvis_tur;
            } else {
                System.out.println("Der var ikke " + (int) forrige_gæt.Score / 10 + " "
                        + (int) forrige_gæt.Score % 10 + "'er " + "Så det var falsk! Spiller "
                        + forrige_gæt.spiller + " har tabt!");

                taber = forrige_gæt.spiller;
            }
            hvis_tur = taber;
            runde_slut();
        }

    }

    /**
     * Printer en bestemt spillers eller alle spillers terninger
     */
    void printTerninger(int spiller) {

        // Hvis spillet er slut kan der ikke udprintes terninger
        if (test_spilstatus()) {
            return;
        }

        if (spiller - 1 > liste_af_raflebaeger.size() - 1 || spiller < 0) {
            System.out.println("Spiller " + spiller + " findes ikke!");
        } else if (spiller > 0) {
            String text = liste_af_raflebaeger.get(spiller - 1).toString();
            System.out.println("Spiller " + (spiller) + " " + text);
        } else {
            // Udskriv alle spillere og deres terninger!
            for (int i = 0; i < antal_spillere; i++) {
                String text = liste_af_raflebaeger.get(i).toString();
                System.out.println("Spiller " + (i + 1) + " " + text);
            }
        }

    }

    /**
     * Starter en runde. Dette indebærer: -Reset AntalØjne og Kombinationer arrrys -Udskriver hvis tur det er -Sætter AntalØjne og Kombinationer arrrys udfra terningerne i runden -Sætter spil_status til "spil" -Fjerner forrige rundes gæt
     */
    void start_rounde() {

        // Hvis spillet er slut kan der ikke startes en ny runde, der skal startes et nyt SPIL!
        if (test_spilstatus()) {
            return;
        }

        //Fjerner alle tidligere terningeoptællinger
        for (int i = 0; i <= 6; i++) {
            AntalØjne[i] = 0;
            Kombinationer[i] = 0;
        }

        Hvis_turErDet(); // Udskriv hvis tur det er

        for (Raflebaeger raflebaeger : liste_af_raflebaeger) { // Gennemløb alle raflebærgerne
            if (raflebaeger.trappe_regel) { // Hvis trappereglen er opfyldt
                int trappen = raflebaeger.antalTerninger() + 1;
                AntalØjne[1] += trappen; // Tilføjer trappen til 1'erne i AntalØjne[]
                for (int i = 2; i < 7; i++) {
                    Kombinationer[i] += trappen; // Tilføjer trappen til alle tal i kombinationer[i]
                }
            } else {
                AntalØjne[1] += raflebaeger.antalDerViser(1); // Tilføj antal 1'ere i Antaløjne[1]
                for (int i = 2; i <= 6; i++) {
                    AntalØjne[i] += raflebaeger.antalDerViser(i); // Tilføj antal terninger der viser i til AntalØjne[i]

                    // Tilføj antal terninger der viser i + antal terninger der viser 1 til Kombinationer[i]
                    Kombinationer[i] += raflebaeger.antalDerViser(i) + raflebaeger.antalDerViser(1);
                }
            }
        }

        taber = -1; //Reset taber hvis spillet ikke er slut.
        runde_nr++; //Tæller antal runder op (Variablen bruges ikke)
        spil_status = "spil"; // Sæt spillet til "spil" tilstanden

        //Fjerner forrige rundes gæt
        forrige_gæt = null;
        nuværende_gæt = null;
    }

    /**
     * Angiver at en runde er slut. Dette indebærer: - Fjern terninger fra vinderne af runden og ryst alles bærgere - Sæt spil_status til "runde_slut" - Tjekker om spillet er slut, angiver en taber og sætter spil_status til "spil_slut"
     */
    private void runde_slut() {

        // Fjerner terninger fra dem der vandt runden og ryster alles bærgere
        for (int i = 0; i < antal_spillere; i++) {
            if (i != taber - 1) {
                if (liste_af_raflebaeger.get(i).terninger.size() != 0) { // Har terninger
                    liste_af_raflebaeger.get(i).fjernTerning(); // Fjern en terning
                } else { // Har ikke terninger

                }
            }
            liste_af_raflebaeger.get(i).ryst(); // Ryst raflebageret
        }

        spil_status = "runde_slut";

        int tomme = 0;
        for (int i = 0; i < antal_spillere; i++) { // Gennemgår hvor mange af spillernes bærgere er tomme
            if (liste_af_raflebaeger.get(i).tom) {
                tomme++;
            }
        }
        if (tomme >= antal_spillere - 1) { // Hvis der kun er en tilbage med et tomt bærger
            spil_status = "spil_slut";
            System.out.println("Spiller " + taber + " har tabt!");
        }
    }

    /**
     * Udskriver hvis tur det er
     */
    private void Hvis_turErDet() {
        System.out.println("Det er nu spiller " + hvis_tur + "'s tur ");
    }

    /**
     * Tester om spillet er slut Hvis det er slut, udskriv at spillet skal genstartes og hvem der tabte
     *
     * @return om spillet er slut
     */
    public boolean test_spilstatus() {
        if (spil_status == "spil_slut") {
            System.out.println("Genstart spillet! Spiller " + taber + " har tabt!");
            return true;
        }
        return false;
    }

    /**
     * @return antallet af terninger
     */
    int antal_terninger_ialt() {
        int antal_terninger_ialt = 0;
        for (int i = 0; i < antal_spillere; i++) {
            antal_terninger_ialt += liste_af_raflebaeger.get(i).antalTerninger();
        }
        return antal_terninger_ialt;
    }

    /**
     * Skifter turen til en spiller, hvis bærger ikke er tomt
     */
    private void Skift_tur() {
        do {
            hvis_tur++;

            if (hvis_tur > antal_spillere) {
                hvis_tur = 1;
            }
        } while (liste_af_raflebaeger.get(hvis_tur - 1).tom);

        Hvis_turErDet();
    }

    /**
     * @return Hvis tur det er
     */
    public int getHvis_tur() {
        return hvis_tur;
    }

    /**
     * @return Hvem der har tabt
     */
    public int getTaber() {
        return taber;
    }
}
