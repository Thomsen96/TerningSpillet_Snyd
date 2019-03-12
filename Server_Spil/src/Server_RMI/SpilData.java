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
class SpilData {
    
    private final int ID;
    private final int Spillere;
    private final int terninger;
    private final String brugernavn;

    public SpilData(int ID, int Spillere, int terninger, String brugernavn) {
        this.ID = ID;
        this.Spillere = Spillere;
        this.terninger = terninger;
        this.brugernavn = brugernavn;
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
}
