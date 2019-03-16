/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

/**
 *
 * @author root
 */
public class SpilData implements java.io.Serializable {

    private static final long serialVersionUID = 12345;

    private final int ID;
    private final int Spillere;
    private final int terninger;
    private final String brugernavn;
    private transient Process process;

    public SpilData(int ID, int Spillere, int terninger, String brugernavn, Process process) {
        this.ID = ID;
        this.Spillere = Spillere;
        this.terninger = terninger;
        this.brugernavn = brugernavn;
        this.process = process;

    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public int getID() {
        return ID;
    }

    public int getSpillere() {
        return Spillere;
    }

    public int getTerninger() {
        return terninger;
    }

    public Process getProcess() {
        return process;
    }

    public String toString() {
        return "Port/id: \"" + ID + "\", bruger: \"" + brugernavn + "\", antal spiller: \""
                + Spillere + "\", terninger: \"" + terninger + "\"";
    }

}
